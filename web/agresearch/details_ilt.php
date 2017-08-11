<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_SESSION['admin']) && $_SESSION['admin']==true && isset($_GET['id'])){
	
	$id=$_GET['id'];
	$query="SELECT input_log_id, input_log_date, field_name, field_replication_number, plot_number, treatment_name, input_treatment_material, input_quantity, input_treatment_preparation_method, input_cost, input_comments, input_picture FROM input_log, field, treatment WHERE input_log_id=$id AND field.field_id = input_log.field_id AND treatment.treatment_id = input_log.treatment_id";
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
<b>Treatment:</b> <?php echo($row[5]); ?><br>
<b>Date:</b> <?php echo($row[1]); ?> <br>
<b>Material used:</b> <?php echo($row[6]); ?> <br>
<b>Preparation method:</b> <?php echo($row[8]); ?><br>
<b>Quantity:</b> <?php echo($row[7]); ?><br>
<b>Cost (local currency):</b> <?php echo($row[9]); ?><br>
<?php
if($row[11]!=""){
	$filename=$row[11];
	list($width, $height)=getimagesize($filename);
	$w=$width*(150/$height);
	$h=150;
?>
<img src="<?php echo($filename); ?>" width="<?php echo($w); ?>" height="<?php echo($h); ?>"><br>
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