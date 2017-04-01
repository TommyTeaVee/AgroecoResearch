<?php
include_once "./../includes/init_database.php";
include_once "./../includes/functions.php";
$dbh = initDB();

if($_SERVER["REQUEST_METHOD"] == "GET" && isset($_GET['user_id'])){
	$user_id=$_GET['user_id'];
	$u=getUserRole($dbh,$user_id);
	if($u>0){
		$df = fopen("php://output", 'w');
		$query="SELECT activity_id, activity_name, activity_category, activity_periodicity FROM activity ORDER BY activity_id";
		$result = mysqli_query($dbh,$query);
		while($row = mysqli_fetch_array($result,MYSQL_NUM)){
			fputcsv($df, $row);			
		}
		/*
		fwrite($df,"*\n");
		
		$query="SELECT activity_x_crop_or_treatment_id, activity_id, crop_id, treatment_id FROM activity_x_crop_or_treatment ORDER BY activity_x_crop_or_treatment_id";
		$result = mysqli_query($dbh,$query);
		while($row = mysqli_fetch_array($result,MYSQL_NUM)){
			fputcsv($df, $row);	
		}
		*/
		fclose($df);
	}
}

?>