<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

$id=$_GET['id'];
$numbers=$_GET['numbers'];
$values=str_replace(";","#",$_GET['values']);
$values=str_replace("_"," ",$values);

$numbers_list=explode(",",$numbers);
$values_list=explode(",",$values);
$samples_string="";
for($i=0;$i<sizeof($numbers_list);$i++){
	if($samples_string==""){
		$samples_string=$numbers_list[$i]."*".$values_list[$i];
	} else {
		$samples_string.="*".$numbers_list[$i]."*".$values_list[$i];
	}
}

$query="UPDATE log SET log_value_text='$samples_string' WHERE log_id=$id";
$result = mysqli_query($dbh,$query);
?>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<title>Agroeco Research</title>
</head>
<body onload="javascript:window.close();">
</body>
</html>