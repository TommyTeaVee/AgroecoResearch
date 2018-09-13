<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "./../includes/init_database.php";
include_once "./../includes/variables.php";
include_once "./../includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_GET['selected'])){
	$selected=$_GET['selected'];
	$selected_n=explode("*",$selected);
	
	$all_fields=array();
	$all_dates=array();
	$all_measurements=array();
	
	$n_sections=sizeof($selected_n);
	
	if($n_sections>0){
	
		for($i=0;$i<$n_sections;$i++){
			$selected_n_parts=explode(",",$selected_n[$i]);
			array_push($all_dates,$selected_n_parts[0]);
			array_push($all_fields,$selected_n_parts[1]);
			array_push($all_measurements,$selected_n_parts[2]);
		}
	
		//generate report
		$filename="download_".date("Y-m-d").".csv";
	
		$files = glob('generated/*'); 
		foreach($files as $file){ 
			if(is_file($file))
				unlink($file);
		}
		$df	= fopen("generated/".$filename, 'w');
	
		for($a=0;$a<$n_sections;$a++){
			
			$log_date_filter=" AND log.log_date = '".$all_dates[$a]."' ";
			$date_title=$all_dates[$a];
			
			$fields=getChildFields($dbh,$all_fields[$a]);
			
			$measurement=$all_measurements[$a];
			
			//title
			$fname=getFieldNameFromId($dbh,implode(",",$fields));
			$title=array("Location: ".$fname);
			fputcsv($df, $title);
			$title=array("Period: ".$date_title);
			fputcsv($df, $title);
			$mname=getMeasurementNameFromIdWithUnits($dbh,$measurement);
			$title=array("Parameter: ".$mname);
			fputcsv($df, $title);
			$title=array(" ");
			fputcsv($df, $title);
			
			$is_multisample=isMultisample($dbh,$measurement);
			
			if($is_multisample){
					
			} else {
				
				//header
				$plot_name_row=array();
				$date_row=array();
				$data_block=array();
				
				//results
				$result_block=array();
				$mean_row=array("Mean");
				$std_dev_row=array("Standard deviation");
				$std_dev_values=array();
	
				$found_plots_dates=array();
				$data_row=array();
				
				for($i=0;$i<sizeof($fields);$i++){
					$query="SELECT DISTINCT plots, log_value_number, log_date FROM log WHERE measurement_id=$measurement AND field_id=".$fields[$i]." ".$log_date_filter." ORDER BY plots, log_date";
					$result = mysqli_query($dbh,$query);
		
					$target_order=array("Control","PSL","SL","PS","PL","S","L","P");
					$db_result_unordered=array();
					$db_result_ordered=array();
					$found_dates=array();
					while($row=mysqli_fetch_array($result,MYSQL_NUM)){
						$label=calculatePlotLabelsWithoutCrop($dbh,$fields[$i],$row[0]);
						array_push($db_result_unordered,array($label,$row[1],$row[2]));
						if(!in_array($row[2],$found_dates)){
							array_push($found_dates,$row[2]);
						}
					}
		
					for($j=0;$j<sizeof($target_order);$j++){
						$found=false;
						for($k=0;$k<sizeof($db_result_unordered);$k++){
							if($db_result_unordered[$k][0]==$target_order[$j]){
								array_push($db_result_ordered,$db_result_unordered[$k]);
								$found=true;
							}
						}
						if(!$found){
							for($k=0;$k<sizeof($found_dates);$k++){
								array_push($db_result_ordered,array($target_order[$j]," ",$found_dates[$k]));
							}
						}
					}
		
					array_push($data_row,array());
		
					for($j=0;$j<sizeof($db_result_ordered);$j++){
			
						$row=$db_result_ordered[$j];
						$label=$row[0];
			
						if($multiple_dates && $date_in_label){
							$label=$label." ".$row[2];
						}
						$sample=$row[1];
						if(!in_array($label,$found_plots_dates)){
							array_push($found_plots_dates,$label);
						}
						$index=array_search($label,$found_plots_dates);
						$x=sizeof($data_row[$i]);
						if($index==$x){
							array_push($data_row[$i],$sample);
						} else if($index>$x){
							for($j=0;$j<($index-$x);$j++){
								array_push($data_row[$i],"*");
							}
							array_push($data_row[$i],$sample);
						} else {
							$data_row[$i][$index]=$sample;
						}		
					}
					array_unshift($data_row[$i],getFieldNameFromId($dbh,$fields[$i]));
					array_push($data_block,$data_row[$i]);
				}
		
				for($i=0;$i<sizeof($found_plots_dates);$i++){
					if($i==0){
						array_push($plot_name_row,"Field");
					}
					array_push($plot_name_row,$found_plots_dates[$i]);
				}
			
				fputcsv($df, $plot_name_row);
	
				for($i=0;$i<sizeof($data_block);$i++){
					$row=$data_block[$i];
					fputcsv($df, $row);
				}
	
				for($i=1;$i<sizeof($plot_name_row);$i++){
					$mean_sum=0;
					$std_dev_values=array();
					$divisor=sizeof($data_block);
					for($j=0;$j<sizeof($data_block);$j++){
						if($data_block[$j][$i]==" " || $data_block[$j][$i]==""){
							$divisor--;
						} else {
							$mean_sum+=$data_block[$j][$i];
							array_push($std_dev_values,$data_block[$j][$i]);
						}
					}
					if($divisor>0){
						$mean=$mean_sum/$divisor;
						array_push($mean_row,$mean);
						array_push($std_dev_row,my_standard_deviation($std_dev_values));
					} else {
						array_push($mean_row,0);
						array_push($std_dev_row,0);
					}
		
				}
				array_push($result_block,$mean_row);
				array_push($result_block,$std_dev_row);
				
			}
			
			fputcsv($df,array(" "));
	
			for($i=0;$i<sizeof($result_block);$i++){
				$row=$result_block[$i];
				fputcsv($df,$row);
			}
			
			fputcsv($df,array(" "));
			fputcsv($df,array(" "));
		
		}
		
		fclose($df);
		header("Location: get_report.php?name=$filename");
		
	}
}
?>