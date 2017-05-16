<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_POST['edit'])){
	
	$id=$_POST['id'];
	$type=$_POST['type'];
	$dd=$_POST['dd'];
	$mm=$_POST['mm'];
	$yyyy=$_POST['yyyy'];
	$date=$yyyy."-".$mm."-".$dd;
	$comments=$_POST['comments'];
	
	if($type=="0"){
		$value=$_POST['value'];
		$query="UPDATE log SET log_date='$date', log_value_text='$value', log_comments='$comments' WHERE log_id=$id";
	} else {
		$value=floatval($_POST['value']);
		$units=$_POST['units'];
		$query="UPDATE log SET log_date='$date', log_value_number=$value, log_value_units='$units', log_comments='$comments' WHERE log_id=$id";
	}
	
	$result = mysqli_query($dbh,$query);
	echo "<script type='text/javascript'>";
	echo "window.opener.location.reload(false);";
	echo "window.close();";
	echo "</script>";
	
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true && isset($_GET['id'])){
	
	$id=$_GET['id'];
	$query="SELECT log_id, log_date, field_name, field_replication_number, plot_number, measurement_name, measurement_type, log_value_units, measurement_categories, log_value_number, log_value_text, log_comments FROM log, field, measurement WHERE log_id=$id AND field.field_id = log.field_id AND measurement.measurement_id = log.measurement_id";
	$result = mysqli_query($dbh,$query);
	$row = mysqli_fetch_array($result,MYSQL_NUM);
	
	if($row[4]=="-1"){
		$plot_number="All";
	} else {
		$plot_number=intval($row[4])+1;
	}
	
	$date=$row[1];
	$date_parts=explode("-",$date);
	$yy=$date_parts[0];
	$mm=$date_parts[1];
	$dd=$date_parts[2];
	
?>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<title>Agroeco Research</title>
<script language=Javascript>
       <!--
       function isNumberKey(evt)
       {
          var charCode = (evt.which) ? evt.which : evt.keyCode;
          if (charCode != 46 && charCode > 31 
            && (charCode < 48 || charCode > 57))
             return false;

          return true;
       }
       //-->
</script>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Edit item</h2>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<input name="id" type="hidden" id="id" value="<? echo($id); ?>">
<p><div class="w3-text-green">
<b>Field:</b> <?php echo($row[2]." replication ".$row[3]); ?><br>
<b>Plot:</b> <?php echo($plot_number); ?><br>
<b>Measurement:</b> <?php echo($row[5]); ?><br><br>
<b>Date:</b>
<div class="w3-row-padding">
  <div class="w3-third">
    <select class="w3-select w3-text-green" name="dd">
		<option value="" disabled>Day</option>
		<?php
		for($i=1;$i<=31;$i++){
			if($i<10){
				$n="0".$i;
			} else {
				$n=$i;
			}
			if($n==$dd){
				$selected=" selected";
			} else {
				$selected="";
			}
			echo('<option value="'.$n.'"'.$selected.'>'.$n.'</option>');
		}
		?>
	</select>
  </div>
  <div class="w3-third">
    <select class="w3-select w3-text-green" name="mm">
		<option value="" disabled>Month</option>
		<?php
		for($i=1;$i<=12;$i++){
			if($i<10){
				$n="0".$i;
			} else {
				$n=$i;
			}
			if($n==$mm){
				$selected=" selected";
			} else {
				$selected="";
			}
			echo('<option value="'.$n.'"'.$selected.'>'.$n.'</option>');
		}
		?>
	</select>
  </div>
  <div class="w3-third">
    <input class="w3-input w3-border-teal w3-text-green" type="text" name="yyyy" value="<?php echo($yy); ?>" onkeypress="return isNumberKey(event)">
  </div>
</div>
<?php if($row[6]==0){ ?>
<b>Value:</b>
<select class="w3-select w3-text-green" name="value">
<?php
	$categories=explode(",",$row[8]);
	for($i=0;$i<sizeof($categories);$i++){
		if($categories[$i]==$row[10]){
			$selected=" selected";
		} else {
			$selected="";
		}
		echo('<option value="'.$categories[$i].'"'.$selected.'>'.$categories[$i].'</option>');
	}
?>
</select>
<?php	
} else {
?>
<b>Value:</b> <input class="w3-input w3-border-green w3-text-green" name="value" type="text" value="<?php echo($row[9]); ?>" onkeypress="return isNumberKey(event)">
<b>Units:</b> <input class="w3-input w3-border-green w3-text-green" name="units" type="text" value="<?php echo($row[7]); ?>">
<?php	
}
?>
<b>Comments:</b> <input class="w3-input w3-border-green w3-text-green" name="comments" type="text" value="<?php echo($row[11]); ?>"><br>
</div>
</p>
<button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" id="edit" name="edit">Edit</button> <button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" onclick="javascript:window.close();">Close</button><br><br></form>
<?php
} 
?>