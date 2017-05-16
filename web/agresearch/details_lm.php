<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_SESSION['admin']) && $_SESSION['admin']==true && isset($_GET['id'])){
	
	$id=$_GET['id'];
	$query="SELECT log_id, log_date, field_name, field_replication_number, plot_number, measurement_name, log_value_units, measurement_type, log_value_number, log_value_text, log_comments FROM log, field, measurement WHERE log_id=$id AND field.field_id = log.field_id AND measurement.measurement_id = log.measurement_id";
	$result = mysqli_query($dbh,$query);
	$row = mysqli_fetch_array($result,MYSQL_NUM);
	
	if($row[4]=="-1"){
		$plot_number="All";
	} else {
		$plot_number=intval($row[4])+1;
	}
	
?>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<title>Agroeco Research</title>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Item details</h2>
<p><div class="w3-text-green">
<b>Field:</b> <?php echo($row[2]." replication ".$row[3]); ?><br>
<b>Plot:</b> <?php echo($plot_number); ?><br>
<b>Measurement:</b> <?php echo($row[5]); ?><br>
<b>Date:</b> <?php echo($row[1]); ?> <br>
<?php
if($row[7]==0){
?>
<b>Value:</b> <?php echo($row[9]); ?><br>
<?php
} else {
?>
<b>Value (<?php echo($row[6]); ?>):</b> <?php echo($row[8]); ?><br>
<?php
}
?>
<b>Comments:</b> <?php echo($row[10]); ?><br>
</div>
</p>
<button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" onclick="javascript:window.close();">Close</button><br><br>
<?php
} 
?>