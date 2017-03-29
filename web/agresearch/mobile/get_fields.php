<?php
include_once "./../includes/init_database.php";
include_once "./../includes/functions.php";
$dbh = initDB();

$ret="-1";

if($_SERVER["REQUEST_METHOD"] == "GET" && isset($_GET['user_id'])){
	$user_id=$_GET['user_id'];
	$u=getUserRole($dbh,$user_id);
	if($u>0){
		$query="SELECT field_id, parent_field_id, user_id, field_date_created, field_name, field_replication_number, field_lat, field_lng, field_configuration FROM field ORDER BY field_id";
		$result = mysqli_query($dbh,$query);
		while($row = mysqli_fetch_array($result,MYSQL_NUM)){
			if($ret=="-1"){
				$ret=$row[0].",".$row[1].",".$row[2].",\"".$row[3]."\",\"".$row[4]."\",".$row[5].",\"".$row[6]."\",\"".$row[7]."\",\"".$row[8]."\"";
			} else {
				$ret.="\n".$row[0].",".$row[1].",".$row[2].",\"".$row[3]."\",\"".$row[4]."\",".$row[5].",\"".$row[6]."\",\"".$row[7]."\",\"".$row[8]."\"";
			}			
		}
	}

	echo($ret);
}

?>