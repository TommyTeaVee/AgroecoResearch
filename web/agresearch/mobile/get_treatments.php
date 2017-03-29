<?php
include_once "./../includes/init_database.php";
include_once "./../includes/functions.php";
$dbh = initDB();

$ret="-1";

if($_SERVER["REQUEST_METHOD"] == "GET" && isset($_GET['user_id'])){
	$user_id=$_GET['user_id'];
	$u=getUserRole($dbh,$user_id);
	if($u>0){
		$query="SELECT treatment_id, treatment_name, treatment_category, primary_crop_id, intercropping_crop_id FROM treatment ORDER BY treatment_id";
		$result = mysqli_query($dbh,$query);
		while($row = mysqli_fetch_array($result,MYSQL_NUM)){
			if($ret=="-1"){
				$ret=$row[0].",\"".$row[1]."\",\"".$row[2]."\",".$row[3].",".$row[4];
			} else {
				$ret.="\n".$row[0].",\"".$row[1]."\",\"".$row[2]."\",".$row[3].",".$row[4];
			}			
		}
	}
	echo($ret);
}

?>