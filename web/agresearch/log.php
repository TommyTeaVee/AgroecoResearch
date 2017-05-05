<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_SESSION['admin']) && $_SESSION['admin']==true){
	checkMessages($mail_server, $email, $password, $dbh);
	
	if(!isset($_SESSION['max_messages'])){
		$max_messages=$max_log_items_per_page;
	} else {
		$max_messages=$_SESSION['max_messages'];
	}
	
	if(!isset($_GET['from'])){
		$from=0;
	} else {
		$from=$_GET['from'];
	}
?>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<title>Agroeco Research</title>
<script language="javascript" type="text/javascript">
<!--
function showPopup(url,width,height) {
	newwindow=window.open(url,'name','height=' + height +',width=' + width + ',top=50,left=50,screenX=50,screenY=50');
	if (window.focus) {newwindow.focus()}
	return false;
}

// -->
</script>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Log</h2><br>
<p><table class="w3-table w3-border w3-bordered">
  <thead>
	<tr class="w3-green">
	  <th><input class="w3-check" type="checkbox" id="toggle_all" name="toggle_all"></th>
	  <th>Item</th>
	  <th>Date</th>
	  <th>Field</th>
	  <th>Plot</th>
	  <th>Description</th>
	  <th>Actions</th>
	</tr>
  </thead>
<?php 
$query="(SELECT 'Log', log_id AS id, log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, activity_name AS item FROM field, activity, log WHERE field.field_id = log.field_id AND activity.activity_id = log.activity_id AND log.activity_id>0) UNION (SELECT 'Log', log_id AS id, log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, measurement_name AS item FROM field, measurement, log WHERE field.field_id = log.field_id AND measurement.measurement_id = log.measurement_id AND log.measurement_id>0) UNION (SELECT 'Input', input_log_id AS id, input_log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, crop_name AS item FROM field, crop, input_log WHERE field.field_id = input_log.field_id AND crop.crop_id = input_log.crop_id AND input_log.crop_id>0) UNION (SELECT 'Input', input_log_id AS id, input_log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, treatment_name AS item FROM field, treatment, input_log WHERE field.field_id = input_log.field_id AND treatment.treatment_id = input_log.treatment_id AND input_log.treatment_id>0) ORDER BY date, item LIMIT $from, $max_messages";
$result = mysqli_query($dbh,$query);
while($row = mysqli_fetch_array($result,MYSQL_NUM)){
	if($row[0]=="Log"){
		$color=$log_row_color;
	} else {
		$color=$input_log_row_color;
	}
	echo('<tr class="'.$color.'">');
	echo('<td><input class="w3-check" type="checkbox"></td>');
	echo('<td>'.$row[0].'</td>');
	echo('<td>'.$row[2].'</td>');
	echo('<td>'.$row[3].' R'.$row[4].'</td>');
	if($row[5]=="-1"){
		$plot_number="All";
	} else {
		$plot_number=intval($row[5])+1;
	}
	echo('<td>'.$plot_number.'</td>');
	echo('<td>'.$row[6].'</td>');
	if($row[0]=="Input"){
		$link_details="calc_il.php?id=".$row[1]."&task=details";
		$link_edit="calc_il.php?id=".$row[1]."&task=edit";
	} else {
		$link_details="calc_l.php?id=".$row[1]."&task=details";
		$link_edit="calc_l.php?id=".$row[1]."&task=edit";
	}
	echo('<td><a href="'.$link_details.'" onclick="return showPopup(\''.$link_details.'\',600,400)">Details</a> || <a href="'.$link_edit.'" onclick="return showPopup(\''.$link_edit.'\',800,700)">Edit</a></td>');
	echo('</tr>');
}
?>
</table></p><br>
<button class="w3-button w3-green w3-round w3-border w3-border-green" style="width:20%; height:40px; max-width:300px;" id="delete_selected" name="delete_selected">Delete selected</button> <button class="w3-button w3-green w3-round w3-border w3-border-green" id="filters" name="filters" style="width:20%; height:40px; max-width:300px;">Filters</button> <button class="w3-button w3-green w3-round w3-border w3-border-green" id="menu" name="menu" style="width:20%; height:40px; max-width:300px;">Menu</button><br><br>
</body>
</html>
<?php
} else {
        header("Location: index.php");
}
?>