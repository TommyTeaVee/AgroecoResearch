<?php

function normalize($data) {
	$data = trim($data);
	$data = addslashes($data);
	$data = htmlspecialchars($data);
	return $data;
}

function validateUser($dbh, $user_alias, $user_password) {
	$ret=-1;
	$query="SELECT user_id, user_role FROM user WHERE user_alias='$user_alias' AND user_password='$user_password'";
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret=$row[0].",".$row[1];
	}
	return $ret;
}

function getUserRole($dbh,$user_id){
	$ret=-1;
	$query="SELECT user_role FROM user WHERE user_id=$user_id";
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret=$row[0];
	}
	return $ret;
}

function getUserNameFromId($dbh,$user_id){
	$ret="";
	$query="SELECT user_name FROM user WHERE user_id=$user_id";
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret=$row[0];
	}
	return $ret;
}

function getCrops($dbh,$int){
	$ret=array();
	if($int==1){
		$query="SELECT crop_id, CONCAT(crop_name,' (',crop_variety_name,')') AS name, crop_symbol FROM crop WHERE crop_used_for_intercropping=1 ORDER BY name";
	} else if ($int==0) {
		$query="SELECT crop_id, CONCAT(crop_name,' (',crop_variety_name,')') AS name, crop_symbol FROM crop WHERE crop_used_for_intercropping=0 ORDER BY name";
	} else {
		$query="SELECT crop_id, CONCAT(crop_name,' (',crop_variety_name,')') AS name, crop_symbol FROM crop ORDER BY name";
	}
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret[$i]=$row[0].",".$row[1].",".$row[2];
		$i++;
	}
	return $ret;
}

function getTreatments($dbh){
	$ret=array();
	$query="(SELECT treatment_id AS id, treatment_name AS treatment, ' ' AS crop1, ' ' AS crop2 FROM treatment WHERE primary_crop_id = NULL AND intercropping_crop_id = NULL) UNION (SELECT treatment_id AS id, treatment_name AS treatment, (SELECT CONCAT(crop_name,' (',crop_variety_name,')') FROM crop WHERE crop_id = treatment.primary_crop_id) AS crop1, (SELECT CONCAT(crop_name,' (',crop_variety_name,')') FROM crop WHERE crop_id = treatment.intercropping_crop_id) AS crop2 FROM treatment) ORDER BY treatment";
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$t="";
		if(!is_null($row[2]) && !is_null($row[3])){
			$t=" (".$row[2]." with ".$row[3].")";
		}
		$ret[$i]=$row[0].",".$row[1].$t;
		$i++;
	}
	return $ret;
}

function getMeasurementCategories($dbh){
	$ret=array();
	$query="SELECT DISTINCT measurement_category FROM measurement ORDER BY measurement_category";
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret[$i]=$row[0];
		$i++;
	}
	return $ret;
}

function getMeasurementSubcategories($dbh){
	$ret=array();
	$query="SELECT DISTINCT measurement_subcategory FROM measurement ORDER BY measurement_subcategory";
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret[$i]=$row[0];
		$i++;
	}
	return $ret;
}

function getActivityCategories($dbh){
	$ret=array();
	$query="SELECT DISTINCT activity_category FROM activity ORDER BY activity_category";
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret[$i]=$row[0];
		$i++;
	}
	return $ret;
}

function fieldHasConfiguration($field_id,$dbh){
	$ret=false;
	$query="SELECT field_configuration FROM field WHERE field_id=$field_id";
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		if($row[0]!=""){
			$ret=true;
		}
	}
	return $ret;
}

function getFieldConfiguration($field_id,$dbh){
	$ret="";
	$query="SELECT field_configuration FROM field WHERE field_id=$field_id";
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret=$row[0];
	}
	return $ret;
}

function getParentField($field_id,$dbh){
	$ret="";
	$query="SELECT parent_field_id FROM field WHERE field_id=$field_id";
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret=$row[0];
	}
	return $ret;
}

function parseConfig($element){
	$inner=substr($element,3,(strlen($element)-4));
	$parts=explode(",",$inner);
	return $parts;
}

function recalculateConfig($config){
	$elements=explode(";",$config);
	$included_crops=array();
	$n=0;
	$intercropping=0;
	$soil_management=0;
	$pest_control=0;
	for($i=2;$i<(sizeof($elements)-1);$i++){
		$parts=parseConfig($elements[$i]);
		if(!in_array($parts[0],$included_crops)){
			$included_crops[$n]=$parts[0];
			$n++;
		}
		if($parts[1]!=0){
			$intercropping=1;
		}
		if($parts[2]!=0){
			$soil_management=1;
		}
		if($parts[3]!=0){
			$pest_control=1;
		}
	}
	$elements[0]='F=('.$n.','.$intercropping.','.$soil_management.','.$pest_control.')';
	$ret=implode(";",$elements);
	return $ret;
}

function calculatePlotLabels($dbh,$field_id,$plotsCSV){
	$ret="";
	
	$query="SELECT field_configuration FROM field WHERE field_id=$field_id";
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$field_configuration=$row[0];
		$elements=explode(";",$field_configuration);
		$plots=explode(",",$plotsCSV);
		if(sizeof($plots)==(sizeof($elements)-3)){
			$ret="All";
		} else {
			for($i=0;$i<sizeof($plots);$i++){
				$plot=$elements[$plots[$i]+2];
				$plot_parts=parseConfig($plot);
				$plot_string=getCropSymbolFromId($dbh,$plot_parts[0]);
				$plot_treatments="";
				if($plot_parts[3]!=0){
					$plot_treatments="P";
				}
				if($plot_parts[2]!=0){
					$plot_treatments.="S";
				}
				if($plot_parts[1]!=0){
					$plot_treatments.="L";
				}
				if($plot_treatments!=""){
					$plot_string=$plot_string."-".$plot_treatments;
				}
			
				if($ret==""){
					$ret=$plot_string;
				} else {
					$ret.=", ".$plot_string;
				}
			}
		}
	}
	
	return $ret;
}

function getCropSymbolFromId($dbh,$crop_id){
	$ret="";
	$query="SELECT crop_symbol FROM crop WHERE crop_id=$crop_id";
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret=$row[0];
	}
	return $ret;
}

function getTotalItems($dbh,$log_field_filter,$input_log_field_filter,$log_date_filter,$input_log_date_filter,$activity_filter,$measurement_filter,$crop_filter,$treatment_filter){
	
	$n=0;
	$m=0;
	$only_log=false;
	$only_input=false;
	
	$where="";
	if($log_field_filter!=" "){
		$log_field_filter=substr($log_field_filter,5);
		$where=" WHERE ".$log_field_filter;
	}
	if($log_date_filter!=" "){
		$log_date_filter=substr($log_date_filter,5);
		if($where==""){
			$where=" WHERE ".$log_date_filter;
		} else {
			$where.=" AND ".$log_date_filter;
		}
	}
	if($activity_filter>0){
		$only_log=true;
		$activity_filter="activity_id=".$activity_filter;
		if($where==""){
			$where=" WHERE ".$activity_filter;
		} else {
			$where.=" AND ".$activity_filter;
		}
	} else if($measurement_filter>0){
		$only_log=true;
		$measurement_filter="measurement_id=".$measurement_filter;
		if($where==""){
			$where=" WHERE ".$measurement_filter;
		} else {
			$where.=" AND ".$measurement_filter;
		}
	}
	$query="SELECT COUNT(log_id) FROM log".$where;
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$n=$row[0];
	} else {
		$n=0;
	}
	
	$where="";
	if($input_log_field_filter!=" "){
		$input_log_field_filter=substr($input_log_field_filter,5);
		$where=" WHERE ".$input_log_field_filter;
	}
	if($input_log_date_filter!=" "){
		$input_log_date_filter=substr($input_log_date_filter,5);
		if($where==""){
			$where=" WHERE ".$input_log_date_filter;
		} else {
			$where.=" AND ".$input_log_date_filter;
		}
	}
	if($crop_filter>0){
		$only_input=true;
		$crop_filter="crop_id=".$crop_filter;
		if($where==""){
			$where=" WHERE ".$crop_filter;
		} else {
			$where.=" AND ".$crop_filter;
		}
	} else if($treatment_filter>0){
		$only_input=true;
		$treatment_filter="treatment_id=".$treatment_filter;
		if($where==""){
			$where=" WHERE ".$treatment_filter;
		} else {
			$where.=" AND ".$treatment_filter;
		}
	}
	$query="SELECT COUNT(input_log_id) FROM input_log".$where;
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$m=$row[0];
	}
	
	if($only_log){
		$ret=$n;
	} else if($only_input){
		$ret=$m;
	} else {
		$ret=$m+$n;
	}
	
	return $ret;
}

function getFields($dbh){
	$ret=array();
	$query="SELECT field_id, field_name, field_replication_number FROM field ORDER BY field_name, field_replication_number";
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret[$i]=$row;
		$i++;
	}
	return $ret;
}

function getActivities($dbh){
	$ret=array();
	$query="SELECT activity_id, activity_name FROM activity ORDER BY activity_name";
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret[$i]=$row;
		$i++;
	}
	return $ret;
}

function getMeasurements($dbh){
	$ret=array();
	$query="SELECT measurement_id, measurement_name FROM measurement ORDER BY measurement_name";
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret[$i]=$row;
		$i++;
	}
	return $ret;
}

function getTreatmentColors($dbh){
	$ret=array();
	$query="SELECT treatment_color_id, color FROM treatment_color ORDER BY treatment_color_id";
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret[$i]=$row[1];
		$i++;
	}
	return $ret;
}

//mail

function decodeISO88591($string) {               
	$string=str_replace("=?iso-8859-1?q?","",$string);
  	$string=str_replace("=?iso-8859-1?Q?","",$string);
  	$string=str_replace("?=","",$string);

  	$charHex=array("0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F");
       
	for($z=0;$z<sizeof($charHex);$z++) {
		for($i=0;$i<sizeof($charHex);$i++) {
      		$string=str_replace(("=".($charHex[$z].$charHex[$i])),chr(hexdec($charHex[$z].$charHex[$i])),$string);
    	}
  	}
  	return($string);
}

function parse($structure) {
	$type = array("text", "multipart", "message", "application", "audio", "image", "video", "other");
	$encoding = array("7bit", "8bit", "binary", "base64", "quoted-printable", "other");
	$ret = array();
	$parts = $structure->parts;
	for($x=0; $x<sizeof($parts); $x++) {
		$ret[$x]["pid"] = ($x+1);	
		$this_part = $parts[$x];
		if ($this_part->type == "") { $this_part->type = 0; }
		$ret[$x]["type"] = $type[$this_part->type] . "/" . strtolower($this_part->subtype);	
		if ($this_part->encoding == "") { $this_part->encoding = 0; }
		$ret[$x]["encoding"] = $encoding[$this_part->encoding];	
		$ret[$x]["size"] = strtolower($this_part->bytes);	
		if ($this_part->ifdisposition) {
			$ret[$x]["disposition"] = strtolower($this_part->disposition);	
			if (strtolower($this_part->disposition) == "attachment" || strtolower($this_part->disposition) == "inline") {
				$params = $this_part->dparameters;
				if (is_null($params)) {
					$params = $this_part->parameters;
				}
				if (!is_null($params)) {
					foreach ($params as $p) {
						if($p->attribute == "FILENAME" || $p->attribute == "NAME") {
							$ret[$x]["name"] = $p->value;	
							break;			
						}
					}
				}
			}
		} 
	}
	return $ret;
}

function decodeSubject($s) {
	$ret=$s;
	$elements=imap_mime_header_decode($s);
	if (sizeof($elements)>0) {
		if($elements[0]->charset=="utf-8") {
			$ret=utf8_decode($elements[0]->text);
		} else if ($elements[0]->charset="ISO-8859-1") {
			$ret=decodeISO88591($elements[0]->text);
		}
	}
	return $ret;
}

function checkMessages($mail_server, $mail_user, $mail_password, $dbh){
	if ($inbox = imap_open ($mail_server, $mail_user, $mail_password)) {
		$total = imap_num_msg($inbox);
		for($x=1; $x<=$total; $x++) {
			$headers = imap_header($inbox, $x);
			$structure = imap_fetchstructure($inbox, $x);
			$sections = parse($structure);
			if (isset($headers->subject)) {
				$subject = decodeSubject($headers->subject);
			} else {
				$subject = "";
			}
			if ($subject=="pA439urcjLVk6szA" && is_array($sections) && sizeof($sections)>0) {
				for($y=0; $y<sizeof($sections); $y++) {	
					$type = $sections[$y]["type"];
					$encoding = $sections[$y]["encoding"];
					$pid = $sections[$y]["pid"];
					$attachment = imap_fetchbody($inbox,$x,$pid);
					if ($type=="text/plain" || $type=="text/html") {
						if ($encoding == "base64") {
							$text = trim(utf8_decode(imap_base64($attachment)));
						} else {
							$text = trim(utf8_decode(decodeISO88591($attachment)));
						}
						$what_log=explode("<>",$text);
						$ma_log_entry=explode("|",$what_log[0]);
						for($i=0;$i<sizeof($ma_log_entry);$i++){
							$ma_log_entry_part=explode(";",$ma_log_entry[$i]);
							if(sizeof($ma_log_entry_part)==16){
								$field_id=$ma_log_entry_part[0];
								$plots=$ma_log_entry_part[1];
								$user_id=$ma_log_entry_part[2];
								$crop_id=$ma_log_entry_part[3];
								$treatment_id=$ma_log_entry_part[4];
								$measurement_id=$ma_log_entry_part[5];
								$activity_id=$ma_log_entry_part[6];
								$date=$ma_log_entry_part[7];
								$number_value=$ma_log_entry_part[8];
								$units=$ma_log_entry_part[9];
								$text_value=$ma_log_entry_part[10];
								$number_of_laborers=$ma_log_entry_part[11];
								$cost=number_format($ma_log_entry_part[12],2,'.','');
								$comments=$ma_log_entry_part[13];
								$log_id=$ma_log_entry_part[14];
								$sample_number=$ma_log_entry_part[15];
							
								$query="INSERT INTO log (field_id, plots, user_id, crop_id, sample_number, treatment_id, measurement_id, activity_id, log_date, log_value_number, log_value_units, log_value_text, log_number_of_laborers, log_cost, log_comments) VALUES ($field_id, '$plots', $user_id, $crop_id, $sample_number, $treatment_id, $measurement_id, $activity_id, '$date', $number_value, '$units', '$text_value', $number_of_laborers, $cost, '$comments')";
								//echo($query);
								$result = mysqli_query($dbh,$query);
							}
						}
						
						$i_log_entry=explode("|",$what_log[1]);
						for($i=0;$i<sizeof($i_log_entry);$i++){
							$i_log_entry_part=explode(";",$i_log_entry[$i]);
							if(sizeof($i_log_entry_part)==14){
								$log_id=$i_log_entry_part[0];
								$field_id=$i_log_entry_part[1];
								$plots=$i_log_entry_part[2];
								$user_id=$i_log_entry_part[3];
								$crop_id=$i_log_entry_part[4];
								$treatment_id=$i_log_entry_part[5];
								$date=$i_log_entry_part[6];
								$age=$i_log_entry_part[7];
								$origin=$i_log_entry_part[8];
								if($origin=="null") { $origin=""; }
								$quantity=$i_log_entry_part[9];
								$cost=number_format($i_log_entry_part[10],2,'.','');
								$material=$i_log_entry_part[11];
								if($material=="null") { $material=""; }
								$method=$i_log_entry_part[12];
								if($method=="null") { $method=""; }
								$comments=$i_log_entry_part[13];
								if($comments=="null") { $comments=""; }
							
								$query="INSERT INTO input_log (input_log_date, field_id, plots, user_id, crop_id, treatment_id, input_age, input_origin, input_quantity, input_cost, input_treatment_material, input_treatment_preparation_method, input_comments) VALUES ('$date', $field_id, '$plots', $user_id, $crop_id, $treatment_id, $age, '$origin', $quantity, $cost, '$material', '$method', '$comments')";
								//echo($query);
								$result = mysqli_query($dbh,$query);
							}
						}
						
					}
				}
				$sections=NULL;
			}
			imap_delete($inbox,$x);
		}
		imap_close($inbox, CL_EXPUNGE);
	}
}

function markNotificationAsSent($dbh,$id){
	$query="UPDATE notification SET notification_sent=1 WHERE notification_id=$id";
	$result = mysqli_query($dbh,$query);
}

function parseSampleValues($sample_values){
	$ret="";
	$elements=explode("*",$sample_values);
	for($i=0;$i<sizeof($elements);$i+=2){
		if($ret==""){
			$ret=$elements[$i].":".$elements[$i+1];
		} else {
			$ret.=", ".$elements[$i].":".$elements[$i+1];
		}
	}
	return $ret;
}
?>