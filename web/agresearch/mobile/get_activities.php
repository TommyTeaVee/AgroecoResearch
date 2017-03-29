<?php
include_once "./../includes/init_database.php";
include_once "./../includes/functions.php";
$dbh = initDB();

$ret="-1";

if($_SERVER["REQUEST_METHOD"] == "GET" && isset($_GET['user_id'])){
	$user_id=$_GET['user_id'];
	$u=getUserRole($dbh,$user_id);
	if($u>0){
		$query="SELECT activity_id, activity_name, activity_category, activity_periodicity FROM activity ORDER BY activity_id";
		$result = mysqli_query($dbh,$query);
		while($row = mysqli_fetch_array($result,MYSQL_NUM)){
			if($ret=="-1"){
				$ret=$row[0].",\"".$row[1]."\",\"".$row[2]."\",".$row[3];
			} else {
				$ret.="\n".$row[0].",\"".$row[1]."\",\"".$row[2]."\",".$row[3];
			}			
		}
		
		$ret.="\n*";
		
		$query="SELECT activity_x_crop_or_treatment_id, activity_id, crop_id, treatment_id FROM activity_x_crop_or_treatment ORDER BY activity_x_crop_or_treatment_id";
		$result = mysqli_query($dbh,$query);
		while($row = mysqli_fetch_array($result,MYSQL_NUM)){
			$ret.="\n".$row[0].",".$row[1].",".$row[2].",".$row[3];
		}
	}

	
	echo($ret);
}

?>