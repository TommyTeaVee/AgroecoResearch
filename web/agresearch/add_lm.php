<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_POST['add'])){
	
	$id=$_POST['id'];
	$type=$_POST['type'];
	$dd=$_POST['dd'];
	$mm=$_POST['mm'];
	$yyyy=$_POST['yyyy'];
	$date=$yyyy."-".$mm."-".$dd;
	$comments=$_POST['comments'];
	if(isset($_FILES['log_picture']['name'])){
		$image_file=$_FILES['log_picture']['name'];
		$upload = "images/".$image_file;
		if(is_uploaded_file($_FILES['log_picture']['tmp_name'])) {
			move_uploaded_file($_FILES['log_picture']['tmp_name'],$upload);
			$update_picture=", log_picture='$upload'";
		} else {
			$update_picture="";
		}
	} else {
		$update_picture="";
	}
	
	if($type=="0"){
		$value=$_POST['value'];
		$query="UPDATE log SET log_date='$date', log_value_text='$value', log_comments='$comments'".$update_picture." WHERE log_id=$id";
	} else {
		$value=floatval($_POST['value']);
		$units=$_POST['units'];
		$query="UPDATE log SET log_date='$date', log_value_number=$value, log_value_units='$units', log_comments='$comments'".$update_picture." WHERE log_id=$id";
	}
	
	$result = mysqli_query($dbh,$query);
	echo "<script type='text/javascript'>";
	echo "window.opener.location.reload(false);";
	echo "window.close();";
	echo "</script>";
	
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true && isset($_GET['id'])){
	
	$id=$_GET['id'];
	$field=$_GET['field'];
	
	$values=$_SESSION['values'];
	
	$query="SELECT measurement_name, measurement_category, measurement_type, measurement_has_sample_number, measurement_categories, measurement_units FROM measurement WHERE measurement_id=$id";
	$result = mysqli_query($dbh,$query);
	$row = mysqli_fetch_array($result,MYSQL_NUM);
	$measurement_name = $row[0];
	$measurement_category = $row[1];
	$measurement_type = $row[2];
	$measurement_has_samples = $row[3];
	$measurement_categories = $row[4];
	$measurement_units = $row[5];

	$yy=date('Y');
	$mm=date('m');
	$dd=date('d');
	
?>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<title>Agroeco Research</title>
<script language="Javascript">
       <!--
       function isNumberKey(evt)
       {
          var charCode = (evt.which) ? evt.which : evt.keyCode;
          if (charCode != 46 && charCode > 31 
            && (charCode < 48 || charCode > 57))
             return false;

          return true;
       }
	   
	   function addPlot(){
		var e = document.getElementById("a_plots");
		var plot_id = e.options[e.selectedIndex].value;
		if(plot_id!=""){
			var plot_label = e.options[e.selectedIndex].text;
			var p = document.getElementById("plots");
			var pl = document.getElementById("plot_labels");
			if(p.value==""){
				p.value=plot_id;
				pl.value=plot_label;
			} else {
				p.value+=","+plot_id;
				pl.value+=", "+plot_label;
			}
			var ip = document.getElementById("included_plots");
			ip.innerHTML=formatPlotsHTML(pl.value,p.value);
		
			e.remove(e.selectedIndex);
			e.options[0].text="Plot added";
			e.disabled=true;
		}
	   }
	   
	   function formatPlotsHTML(plots,ids){
		   var plot_labels_list = plots.split(",");
		   var plot_ids_list = ids.split(",");
		   var stringHTML="";
		   for(var i=0;i<plot_ids_list.length;i++){
			   if(stringHTML==""){
				   stringHTML='<a href="javascript:removePlot(\''+ plot_labels_list[i].trim() +'\','+ plot_ids_list[i] +')">'+ plot_labels_list[i].trim() +'</a>';
			   } else {
				   stringHTML+=', <a href="javascript:removePlot(\''+ plot_labels_list[i].trim() +'\','+ plot_ids_list[i] +')">'+ plot_labels_list[i].trim() +'</a>';
			   }
		   }
		   return stringHTML;
	   }
	   
	   function removePlot(plot,id){
		    
		    var e = document.getElementById("a_plots");
			var o = document.createElement("option");
			o.text = plot;
			o.value = id;
			e.add(o);
			e.options[0].text="Add a plot:";
			e.disabled=false;
			
			var p = document.getElementById("plots");
			var pl = document.getElementById("plot_labels");
			var plot_list = p.value.split(",");
			var plot_labels_list = pl.value.split(",");
			var string_ids="";
			var string_labels="";
			for (var i=0; i<plot_list.length; i++){
				if(plot_list[i]==id){
					
				} else {
					if(string_ids==""){
						string_ids=plot_list[i];
						string_labels=plot_labels_list[i];
					} else {
						string_ids+=","+plot_list[i];
						string_labels+=", "+plot_labels_list[i];
					}
				}
			}
			p.value=string_ids;
			pl.value=string_labels;
			var ip = document.getElementById("included_plots");
			ip.innerHTML=formatPlotsHTML(pl.value,p.value);
	   }
       //-->
</script>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Add measurement</h2>
<form method="post" enctype="multipart/form-data" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<input name="id" type="hidden" id="id" value="<? echo($id); ?>">
<input name="type" type="hidden" id="type" value="<? echo($measurement_type); ?>">
<input name="has_samples" type="hidden" id="has_samples" value="<? echo($measurement_has_samples); ?>">
<input name="field" type="hidden" id="field" value="<? echo($field); ?>">
<input name="plots" type="hidden" id="plots" value="">
<input name="plot_labels" type="hidden" id="plot_labels" value="">
<p><div class="w3-text-green">
<b>Registered by:</b> <?php echo(getUserNameFromId($dbh,$row[15])); ?><br>
<b>Field:</b> <?php echo(getFieldNameFromId($dbh,$field)); ?><br>
<b>Plot (click on plot name to remove):</b> <span id="included_plots"></span><br>
<?php
$plots=getRemainingPlots($dbh,$field,array(),($id*-1),"lm");
if($plots!=""){
	$plot_list=explode(",",$plots);
	$plot_labels=calculatePlotLabels($dbh,$field,$plots);
	$plot_labels_list=explode(",",$plot_labels);
?>
<span id="available_plots">
<select class="w3-select w3-text-green" name="a_plots" id="a_plots" onchange="addPlot();">
  <option value="" selected>Add a plot:</option>
<?php

for($i=0;$i<sizeof($plot_list);$i++){
	echo('<option value="'.$plot_list[$i].'">'.$plot_labels_list[$i].'</option>');
}
?>
</select>
</span><br>
<?php }
?><br>
<b>Measurement:</b> <?php echo($measurement_name." (".$measurement_category.")"); ?><br><br>
<b>Date:</b>
<div class="w3-row-padding">
  <div class="w3-third">
    <select class="w3-select w3-text-green" name="dd">
		<option value="" disabled>Day</option>
		<?php
		for($i=1;$i<=31;$i++){
			if($i<10){
				$n="0".$i;
			} else {
				$n=$i;
			}
			if($n==$dd){
				$selected=" selected";
			} else {
				$selected="";
			}
			echo('<option value="'.$n.'"'.$selected.'>'.$n.'</option>');
		}
		?>
	</select>
  </div>
  <div class="w3-third">
    <select class="w3-select w3-text-green" name="mm">
		<option value="" disabled>Month</option>
		<?php
		for($i=1;$i<=12;$i++){
			if($i<10){
				$n="0".$i;
			} else {
				$n=$i;
			}
			if($n==$mm){
				$selected=" selected";
			} else {
				$selected="";
			}
			echo('<option value="'.$n.'"'.$selected.'>'.$n.'</option>');
		}
		?>
	</select>
  </div>
  <div class="w3-third">
    <input class="w3-input w3-border-teal w3-text-green" type="text" name="yyyy" value="<?php echo($yy); ?>" onkeypress="return isNumberKey(event)">
  </div>
</div>
<?php 
if($measurement_has_samples==0){
	if($measurement_type==0){ ?>
<b>Value:</b>
<select class="w3-select w3-text-green" name="value">
<?php
		$categories=explode(",",$measurement_categories);
		for($i=0;$i<sizeof($categories);$i++){
			echo('<option value="'.$categories[$i].'">'.$categories[$i].'</option>');
		}
?>
</select>
<?php	
	} else {
?>
<b>Units:</b> <input class="w3-input w3-border-green w3-text-green" name="units" type="text" value="<?php echo($measurement_units); ?>">
<b>Value:</b> <input class="w3-input w3-border-green w3-text-green" name="value" type="text" value="" onkeypress="return isNumberKey(event)">
<?php	
	}
} else {
	$values=parseSampleValues($values);
	if($measurement_type==0){
		?>
<b><br>Values (sample:value)</b> <?php echo($values); ?><br><a href="edit_samples.php?id=-1&m_id=<?php echo($id); ?>">Add samples</a><br><br>
<?php	
	} else if($measurement_type==1) {
	?>
<b><br>Values (sample:value in <?php echo($measurement_units); ?>)</b> <?php echo($values); ?><br><a href="edit_samples.php?id=-1&m_id=<?php echo($id); ?>">Add samples</a><br><br>
<?php
	} else {
		$values=parseHealthReportValues($dbh,$values);
	?>
<b><br>Health report (sample #:problems)</b><br> <?php echo($values); ?><br><a href="edit_health_report.php?id=-1&m_id=<?php echo($id); ?>">Add health report</a><br><br>
<?php
	}
}
?>
<b>Image:</b> <input class="w3-input w3-border-green w3-text-green" name="input_picture" type="file" id="input_picture" accept=".jpg,.png">
<b>Comments:</b> <input class="w3-input w3-border-green w3-text-green" name="comments" type="text" value="">
</div>
</p>
<button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" id="add" name="add">Add</button> <button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" onclick="javascript:window.close();">Close</button><br><br></form>
<?php
} 
?>