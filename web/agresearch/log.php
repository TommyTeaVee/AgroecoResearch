<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_GET['delete_log']) && isset($_GET['delete_input_log']) && isset($_SESSION['admin']) && $_SESSION['admin']==true){
	$delete_log=$_GET['delete_log'];
	$delete_input_log=$_GET['delete_input_log'];
	if($delete_log!=""){
		$query="DELETE FROM log WHERE log_id IN (".$delete_log.")";
		$result = mysqli_query($dbh,$query);
	}
	if($delete_input_log!=""){
		$query="DELETE FROM input_log WHERE input_log_id IN (".$delete_input_log.")";
		$result = mysqli_query($dbh,$query);
	}
} 

if(isset($_SESSION['admin']) && $_SESSION['admin']==true){
	checkMessages($mail_server, $email, $password, $dbh);
	
	if(!isset($_SESSION['max_messages']) || !is_numeric($_SESSION['max_messages'])){
		$max_messages=$max_log_items_per_page;
	} else {
		if($_SESSION['max_messages']<=0){
			$max_messages=$max_log_items_per_page;
		} else {
			$max_messages=$_SESSION['max_messages'];
		}
	}
	
	if(!isset($_SESSION['log_field_filter'])){
		$_SESSION['log_field_filter']=" ";
	} else if($_SESSION[log_field_filter]==""){
		$_SESSION['log_field_filter']=" ";
	}
	if(!isset($_SESSION['input_log_field_filter'])){
		$_SESSION['input_log_field_filter']=" ";
	} else if($_SESSION[input_log_field_filter]==""){
		$_SESSION['input_log_field_filter']=" ";
	}
	if(!isset($_SESSION['log_date_filter'])){
		$_SESSION['log_date_filter']=" ";
	}
	if(!isset($_SESSION['input_log_date_filter'])){
		$_SESSION['input_log_date_filter']=" ";
	}
	
	if(!isset($_GET['from']) || $_SESSION['reset']){
		$from=0;
		$_SESSION['reset']=false;
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

function goToMenu(){
	document.location = "menu.php";
}

function affectAllCheckboxes(){
	var masterCheckbox = document.getElementById("toggle_all");
	var state = masterCheckbox.checked;
	var log_ids = document.getElementsByName("delete_log[]");
	var input_log_ids = document.getElementsByName("delete_input_log[]");
	for(var i=0;i<log_ids.length;i++){
		log_ids[i].checked=state;
	}
	for(var i=0;i<input_log_ids.length;i++){
		input_log_ids[i].checked=state;
	}
}

function confirmDelete(){
	var delete_log =  document.getElementsByName("delete_log[]");
	var delete_input_log =  document.getElementsByName("delete_input_log[]");
	var log_values = [];
	var input_log_values=[];
	for(var i=0;i<delete_log.length;i++){
		if(delete_log[i].checked){
			log_values.push(delete_log[i].value);
		}
	}
	for(var i=0;i<delete_input_log.length;i++){
		if(delete_input_log[i].checked){
			input_log_values.push(delete_input_log[i].value);
		}
	}
	if(log_values.length>0 || input_log_values.length>0){
		if(window.confirm("Delete selcted items?")){
			document.location = "log.php?delete_log=" + log_values.join(',') + "&delete_input_log=" + input_log_values.join(',');
		}
	} else {
		alert("No items selected");
	}
}

// -->
</script>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Log</h2><br>
<p><table class="w3-table w3-border w3-bordered w3-striped w3-hoverable">
  <thead>
	<tr class="w3-green">
	  <th><input class="w3-check" type="checkbox" id="toggle_all" name="toggle_all" onclick="affectAllCheckboxes()"></th>
	  <th>Item</th>
	  <th>Date</th>
	  <th>Field</th>
	  <th>Plot</th>
	  <th>Description</th>
	  <th>Actions</th>
	</tr>
  </thead>
<?php 
if(isset($_SESSION['log_activity_filter'])){
	$query="SELECT 'Log', log_id AS id, log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, activity_name AS item FROM field, activity, log WHERE field.field_id = log.field_id".$_SESSION['log_field_filter'].$_SESSION['log_date_filter']."AND activity.activity_id = log.activity_id AND log.activity_id=".$_SESSION['log_activity_filter']." ORDER BY date DESC, fname, frn, plot, item LIMIT $from, $max_messages";
} else if(isset($_SESSION['log_measurement_filter'])){
	$query="SELECT 'Log', log_id AS id, log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, measurement_name AS item FROM field, measurement, log WHERE field.field_id = log.field_id".$_SESSION['log_field_filter'].$_SESSION['log_date_filter']."AND measurement.measurement_id = log.measurement_id AND log.measurement_id=".$_SESSION['log_measurement_filter']." ORDER BY date DESC, fname, frn, plot, item LIMIT $from, $max_messages";
} else if(isset($_SESSION['input_log_crop_filter'])){
	$query="SELECT 'Input', input_log_id AS id, input_log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, crop_name AS item FROM field, crop, input_log WHERE field.field_id = input_log.field_id".$_SESSION['input_log_field_filter'].$_SESSION['input_log_date_filter']."AND crop.crop_id = input_log.crop_id AND input_log.crop_id=".$_SESSION['input_log_crop_filter']." ORDER BY date DESC, fname, frn, plot, item LIMIT $from, $max_messages";
} else if(isset($_SESSION['input_log_treatment_filter'])){
	$query="SELECT 'Input', input_log_id AS id, input_log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, treatment_name AS item FROM field, treatment, input_log WHERE field.field_id = input_log.field_id".$_SESSION['input_log_field_filter'].$_SESSION['input_log_date_filter']."AND treatment.treatment_id = input_log.treatment_id AND input_log.treatment_id=".$_SESSION['input_log_treatment_filter']." ORDER BY date DESC, fname, frn, plot, item LIMIT $from, $maxmessages";
} else {
	$query="(SELECT 'Log', log_id AS id, log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, activity_name AS item FROM field, activity, log WHERE field.field_id = log.field_id".$_SESSION['log_field_filter'].$_SESSION['log_date_filter']."AND activity.activity_id = log.activity_id AND log.activity_id>0) UNION (SELECT 'Log', log_id AS id, log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, measurement_name AS item FROM field, measurement, log WHERE field.field_id = log.field_id".$_SESSION['log_field_filter'].$_SESSION['log_date_filter']."AND measurement.measurement_id = log.measurement_id AND log.measurement_id>0) UNION (SELECT 'Input', input_log_id AS id, input_log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, crop_name AS item FROM field, crop, input_log WHERE field.field_id = input_log.field_id".$_SESSION['input_log_field_filter'].$_SESSION['input_log_date_filter']."AND crop.crop_id = input_log.crop_id AND input_log.crop_id>0) UNION (SELECT 'Input', input_log_id AS id, input_log_date AS date, field_name AS fname, field_replication_number AS frn, plot_number AS plot, treatment_name AS item FROM field, treatment, input_log WHERE field.field_id = input_log.field_id".$_SESSION['input_log_field_filter'].$_SESSION['input_log_date_filter']."AND treatment.treatment_id = input_log.treatment_id AND input_log.treatment_id>0) ORDER BY date DESC, fname, frn, plot, item LIMIT $from, $max_messages";
}
$result = mysqli_query($dbh,$query);
$n=0;
while($row = mysqli_fetch_array($result,MYSQL_NUM)){
	echo('<tr>');
	if($row[0]=='Log'){
		echo('<td><input class="w3-check" type="checkbox" name="delete_log[]" value="'.$row[1].'"></td>');
	} else if($row[0]=='Input'){
		echo('<td><input class="w3-check" type="checkbox" name="delete_input_log[]" value="'.$row[1].'"></td>');
	}
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
	echo('<td><a href="'.$link_details.'" onclick="return showPopup(\''.$link_details.'\',800,700)">Details</a> || <a href="'.$link_edit.'" onclick="return showPopup(\''.$link_edit.'\',800,700)">Edit</a></td>');
	echo('</tr>');
	$n++;
}
?>
</table>
<?php
if($n==0){ echo("No results"); }
?>
</p><div class="w3-row-padding"><div class="w3-half" align="center">
<?php
if($from>0){
	$prev=$from-$max_messages;
	echo('<a href="log.php?from='.$prev.'">Previous</a>');
} else {
	echo('&nbsp;');
}
?>
</div><div class="w3-half" align="center">
<?php 
if(getTotalItems($dbh,$_SESSION['log_field_filter'],$_SESSION['input_log_field_filter'],$_SESSION['log_date_filter'],$_SESSION['input_log_date_filter'],$_SESSION['log_activity_filter'],$_SESSION['log_measurement_filter'],$_SESSION['input_log_crop_filter'],$_SESSION['input_log_treatment_filter'])>($from+$max_messages)){
	$next=$from+$max_messages;
	echo('<a href="log.php?from='.$next.'">Next</a>');
}
?></div></div>
<br>
<button class="w3-button w3-green w3-round w3-border w3-border-green" style="width:20%; height:40px; max-width:300px;" type="button" id="delete_selected" name="delete_selected" onclick="confirmDelete()">Delete selected</button> <button class="w3-button w3-green w3-round w3-border w3-border-green" id="filters" name="filters" style="width:20%; height:40px; max-width:300px;" onclick="return showPopup('filters.php',800,700)">Filters</button> <button class="w3-button w3-green w3-round w3-border w3-border-green" id="menu" name="menu" type="button" style="width:20%; height:40px; max-width:300px;" onclick="goToMenu()">Menu</button><br><br>
</body>
</html>
<?php
} else {
        header("Location: index.php");
}
?>