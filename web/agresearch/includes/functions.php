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

function getCrops($dbh,$int){
	$ret=array();
	if($int==1){
		$query="SELECT crop_id, CONCAT(crop_name,' (',crop_variety_name,')') AS name FROM crop WHERE crop_used_for_intercropping=1 ORDER BY name";
	} else if ($int==0) {
		$query="SELECT crop_id, CONCAT(crop_name,' (',crop_variety_name,')') AS name FROM crop WHERE crop_used_for_intercropping=0 ORDER BY name";
	} else {
		$query="SELECT crop_id, CONCAT(crop_name,' (',crop_variety_name,')') AS name FROM crop ORDER BY name";
	}
	$result = mysqli_query($dbh,$query);
	$i=0;
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$ret[$i]=$row[0].",".$row[1];
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
?>