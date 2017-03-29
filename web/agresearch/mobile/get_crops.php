<?php
include_once "./../includes/init_database.php";
include_once "./../includes/functions.php";
$dbh = initDB();

$ret="-1";

if($_SERVER["REQUEST_METHOD"] == "GET" && isset($_GET['user_id'])){
	$user_id=$_GET['user_id'];
	$u=getUserRole($dbh,$user_id);
	if($u>0){
		$query="SELECT crop_id, crop_name, crop_variety_name, crop_used_for_intercropping FROM crop ORDER BY crop_id";
		$result = mysqli_query($dbh,$query);
		while($row = mysqli_fetch_array($result,MYSQL_NUM)){
			if($ret=="-1"){
				$ret=$row[0].",\"".$row[1]."\",\"".$row[2]."\",".$row[3];
			} else {
				$ret.="\n".$row[0].",\"".$row[1]."\",\"".$row[2]."\",".$row[3];
			}			
		}
	}
	echo($ret);
}

?>