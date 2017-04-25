<?php
include_once "./../includes/init_database.php";
include_once "./../includes/functions.php";
$dbh = initDB();

if($_SERVER["REQUEST_METHOD"] == "GET" && isset($_GET['user_id'])){
	$user_id=$_GET['user_id'];
	$u=getUserRole($dbh,$user_id);
	if($u>0){
		$df = fopen("php://output", 'w');
		$query="SELECT measurement_id, measurement_name, measurement_category, measurement_subcategory, measurement_type, measurement_range_min, measurement_range_max, measurement_units, measurement_categories, measurement_periodicity FROM measurement ORDER BY measurement_subcategory, measurement_name";
		$result = mysqli_query($dbh,$query);
		while($row = mysqli_fetch_array($result,MYSQL_NUM)){
			fputcsv($df, $row);			
		}
		/*
		fwrite($df,"*\n");
		
		$query="SELECT measurement_x_crop_or_treatment_id, measurement_id, crop_id, treatment_id FROM measurement_x_crop_or_treatment ORDER BY measurement_x_crop_or_treatment_id";
		$result = mysqli_query($dbh,$query);
		while($row = mysqli_fetch_array($result,MYSQL_NUM)){
			fputcsv($df, $row);	
		}
		*/
		fclose($df);
	}
}

?>