<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_POST['apply'])){
	if(isset($_POST['toggle_field'])){
		$_SESSION['log_field_filter']=" AND log.field_id=".$_POST['field']." ";
		$_SESSION['input_log_field_filter']=" AND input_log.field_id=".$_POST['field']." ";
		$_SESSION['field']=$_POST['field'];
	} else {
		$_SESSION['log_field_filter']=" ";
		$_SESSION['input_log_field_filter']=" ";
		$_SESSION['field']=-1;
	}
	if(isset($_POST['toggle_dates'])){
		$dd1=$_POST['dd1'];
		$mm1=$_POST['mm1'];
		$yy1=$_POST['yy1'];
		$dd2=$_POST['dd2'];
		$mm2=$_POST['mm2'];
		$yy2=$_POST['yy2'];
		if(checkdate($mm1,$dd1,$yy1) && checkdate($mm2,$dd2,$yy2)){
			$date1 = strtotime($yy1."-".$mm1."-".$dd1);
			$date2 = strtotime($yy2."-".$mm2."-".$dd2);
			if($date1>$date2){
				$_SESSION['log_date_filter']=" AND (log.log_date BETWEEN '".$yy2."-".$mm2."-".$dd2."' AND '".$yy1."-".$mm1."-".$dd1."') ";
				$_SESSION['input_log_date_filter']=" AND (input_log.input_log_date BETWEEN '".$yy2."-".$mm2."-".$dd2."' AND '".$yy1."-".$mm1."-".$dd1."') ";
				$_SESSION['date1']=$yy2."-".$mm2."-".$dd2;
				$_SESSION['date2']=$yy1."-".$mm1."-".$dd1;
			} else if($date1<$date2) {
				$_SESSION['log_date_filter']=" AND (log.log_date BETWEEN '".$yy1."-".$mm1."-".$dd1."' AND '".$yy2."-".$mm2."-".$dd2."') ";
				$_SESSION['input_log_date_filter']=" AND (input_log.input_log_date BETWEEN '".$yy1."-".$mm1."-".$dd1."' AND '".$yy2."-".$mm2."-".$dd2."') ";
				$_SESSION['date1']=$yy1."-".$mm1."-".$dd1;
				$_SESSION['date2']=$yy2."-".$mm2."-".$dd2;
			} else {
				$_SESSION['log_date_filter']=" AND log.log_date = '".$yy1."-".$mm1."-".$dd1."' ";
				$_SESSION['input_log_date_filter']=" AND input_log.input_log_date = '".$yy1."-".$mm1."-".$dd1."' ";
				$_SESSION['date1']=$yy1."-".$mm1."-".$dd1;
				$_SESSION['date2']=$yy1."-".$mm1."-".$dd1;
			}
		} else {
			$_SESSION['log_date_filter']=" ";
			$_SESSION['input_log_date_filter']=" ";
			$_SESSION['date1']="";
			$_SESSION['date2']="";
		}
	} else {
		$_SESSION['log_date_filter']=" ";
		$_SESSION['input_log_date_filter']=" ";
		$_SESSION['date1']="";
		$_SESSION['date2']="";
	}
	if(isset($_POST['toggle_activity'])){
		$_SESSION['log_activity_filter']=$_POST['activity'];
	} else {
		unset($_SESSION['log_activity_filter']);
	}
	if(isset($_POST['toggle_measurement'])){
		$_SESSION['log_measurement_filter']=$_POST['measurement'];
	} else {
		unset($_SESSION['log_measurement_filter']);
	}
	if(isset($_POST['toggle_crop'])){
		$_SESSION['input_log_crop_filter']=$_POST['crop'];
	} else {
		unset($_SESSION['input_log_crop_filter']);
	}
	if(isset($_POST['toggle_treatment'])){
		$_SESSION['input_log_treatment_filter']=$_POST['treatment'];
	} else {
		unset($_SESSION['input_log_treatment_filter']);
	}
	
	$_SESSION['max_messages']=$_POST['max_messages'];
	$_SESSION['reset']=true;
	
	echo "<script type='text/javascript'>";
	echo "window.opener.location.reload(false);";
	echo "window.close();";
	echo "</script>";
	
} else if(isset($_POST['remove'])){
	
	$_SESSION['log_field_filter']=" ";
	$_SESSION['input_log_field_filter']=" ";
	$_SESSION['field']=-1;
	
	$_SESSION['log_date_filter']=" ";
	$_SESSION['input_log_date_filter']=" ";
	$_SESSION['date1']="";
	$_SESSION['date2']="";
	
	unset($_SESSION['log_activity_filter']);
	unset($_SESSION['log_measurement_filter']);
	unset($_SESSION['input_log_crop_filter']);
	unset($_SESSION['input_log_treatment_filter']);
	
	$_SESSION['max_messages']=$max_log_items_per_page;
	$_SESSION['reset']=true;
	
	echo "<script type='text/javascript'>";
	echo "window.opener.location.reload(false);";
	echo "window.close();";
	echo "</script>";
	
	
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true){
	$currentYear=date("Y");
	
	if(!isset($_SESSION['max_messages'])){
		$max_messages=$max_log_items_per_page;
	} else {
		$max_messages=$_SESSION['max_messages'];
	}
	
	if($_SESSION['date1']!="" && $_SESSION['date2']!=""){
		$date1_parts=explode("-",$_SESSION['date1']);
		$yy1=$date1_parts[0];
		$mm1=$date1_parts[1];
		$dd1=$date1_parts[2];
		$date2_parts=explode("-",$_SESSION['date2']);
		$yy2=$date2_parts[0];
		$mm2=$date2_parts[1];
		$dd2=$date2_parts[2];
	} else {
		$yy1="";
		$mm1="";
		$dd1="";
		$yy2="";
		$mm2="";
		$dd2="";
	}
	
?>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<title>Agroeco Research</title>
<script language=Javascript>
       <!--
		function toggleField(){
			var cb = document.getElementById("toggle_field");
			var s = document.getElementById("field");
			s.disabled = !cb.checked;
		}
		
		function toggleDates(){
			var cb = document.getElementById("toggle_dates");
			var dd1 = document.getElementById("dd1");
			var mm1 = document.getElementById("mm1");
			var yy1 = document.getElementById("yy1");
			dd1.disabled = !cb.checked;
			mm1.disabled = !cb.checked;
			yy1.disabled = !cb.checked;
			dd2.disabled = !cb.checked;
			mm2.disabled = !cb.checked;
			yy2.disabled = !cb.checked;
		}
		
		function toggleActivity(){
			var cb = document.getElementById("toggle_activity");
			var s = document.getElementById("activity");
			s.disabled = !cb.checked;
			if(cb.checked){
				document.getElementById("toggle_measurement").checked=false;
				document.getElementById("toggle_crop").checked=false;
				document.getElementById("toggle_treatment").checked=false;
				toggleMeasurement();
				toggleCrop();
				toggleTreatment();
			}
		}
		
		function toggleMeasurement(){
			var cb = document.getElementById("toggle_measurement");
			var s = document.getElementById("measurement");
			s.disabled = !cb.checked;
			if(cb.checked){
				document.getElementById("toggle_activity").checked=false;
				document.getElementById("toggle_crop").checked=false;
				document.getElementById("toggle_treatment").checked=false;
				toggleActivity();
				toggleCrop();
				toggleTreatment();
			}
		}
		
		function toggleCrop(){
			var cb = document.getElementById("toggle_crop");
			var s = document.getElementById("crop");
			s.disabled = !cb.checked;
			if(cb.checked){
				document.getElementById("toggle_activity").checked=false;
				document.getElementById("toggle_measurement").checked=false;
				document.getElementById("toggle_treatment").checked=false;
				toggleActivity();
				toggleMeasurement();
				toggleTreatment();
			}
		}
		
		function toggleTreatment(){
			var cb = document.getElementById("toggle_treatment");
			var s = document.getElementById("treatment");
			s.disabled = !cb.checked;
			if(cb.checked){
				document.getElementById("toggle_activity").checked=false;
				document.getElementById("toggle_measurement").checked=false;
				document.getElementById("toggle_crop").checked=false;
				toggleActivity();
				toggleMeasurement();
				toggleCrop();
			}
		}
		
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
<body class="w3-small">
<div class="w3-container w3-card-4">
<h2 class="w3-green">Filters</h2>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<p>
<input class="w3-check" type="checkbox" id="toggle_field" name="toggle_field" onclick="toggleField()" <?php echo(($_SESSION['field']>0) ? 'checked' : ''); ?>><label class="w3-text-green">Field</label>
<select class="w3-select w3-text-green" name="field" id="field" <?php echo(($_SESSION['field']>0) ? '' : 'disabled'); ?>>
<?php
$fields=getFields($dbh);
for($i=0;$i<sizeof($fields);$i++){
	$field=$fields[$i];
	$selected = ($field[0]==$_SESSION['field']) ? 'selected' : '';
	echo('<option value="'.$field[0].'"'.$selected.'>'.$field[1].' R'.$field[2].'</option>');
}
?>
</select>
</p>
<p>
<input class="w3-check" type="checkbox" id="toggle_dates" name="toggle_dates" onclick="toggleDates()" <?php echo(($_SESSION['date1']!="" && $_SESSION['date2']!="") ? 'checked' : ''); ?>><label class="w3-text-green">Dates</label>
<div class="w3-row-padding">
  <div class="w3-third">
    <select class="w3-select w3-text-green" name="dd1" id="dd1" <?php echo(($_SESSION['date1']!="" && $_SESSION['date2']!="") ? '' : 'disabled'); ?>>
		<option value="" selected disabled>Day</option>
		<?php
		for($i=1;$i<=31;$i++){
			if($i<10){
				$n="0".$i;
			} else {
				$n=$i;
			}
			$selected = ($n==$dd1) ? 'selected' : '';
			echo('<option value="'.$n.'"'.$selected.'>'.$n.'</option>');
		}
		?>
	</select>
  </div>
  <div class="w3-third">
    <select class="w3-select w3-text-green" name="mm1" id="mm1" <?php echo(($_SESSION['date1']!="" && $_SESSION['date2']!="") ? '' : 'disabled'); ?>>
		<option value="" selected disabled>Month</option>
		<?php
		for($i=1;$i<=12;$i++){
			if($i<10){
				$n="0".$i;
			} else {
				$n=$i;
			}
			$selected = ($n==$mm1) ? 'selected' : '';
			echo('<option value="'.$n.'"'.$selected.'>'.$n.'</option>');
		}
		?>
	</select>
  </div>
  <div class="w3-third">
    <input class="w3-input w3-border-teal w3-text-green" type="text" name="yy1" id="yy1" value="<?php echo(($yy1=="") ? $currentYear : $yy1); ?>" onkeypress="return isNumberKey(event)" <?php echo(($_SESSION['date1']!="" && $_SESSION['date2']!="") ? '' : 'disabled'); ?>>
  </div>
</div>
<div class="w3-row-padding">
  <div class="w3-third">
    <select class="w3-select w3-text-green" name="dd2" id="dd2" <?php echo(($_SESSION['date1']!="" && $_SESSION['date2']!="") ? '' : 'disabled'); ?>>
		<option value="" selected disabled>Day</option>
		<?php
		for($i=1;$i<=31;$i++){
			if($i<10){
				$n="0".$i;
			} else {
				$n=$i;
			}
			$selected = ($n==$dd2) ? 'selected' : '';
			echo('<option value="'.$n.'"'.$selected.'>'.$n.'</option>');
		}
		?>
	</select>
  </div>
  <div class="w3-third">
    <select class="w3-select w3-text-green" name="mm2" id="mm2" <?php echo(($_SESSION['date1']!="" && $_SESSION['date2']!="") ? '' : 'disabled'); ?>>
		<option value="" selected disabled>Month</option>
		<?php
		for($i=1;$i<=12;$i++){
			if($i<10){
				$n="0".$i;
			} else {
				$n=$i;
			}
			$selected = ($n==$mm2) ? 'selected' : '';
			echo('<option value="'.$n.'"'.$selected.'>'.$n.'</option>');
		}
		?>
	</select>
  </div>
  <div class="w3-third">
    <input class="w3-input w3-border-teal w3-text-green" type="text" name="yy2" id="yy2" value="<?php echo(($yy2=="") ? $currentYear : $yy2); ?>" onkeypress="return isNumberKey(event)" <?php echo(($_SESSION['date1']!="" && $_SESSION['date2']!="") ? '' : 'disabled'); ?>>
  </div>
</div>
</p>
<p>
<input class="w3-check" type="checkbox" id="toggle_activity" name="toggle_activity" onclick="toggleActivity()" <?php echo(($_SESSION['log_activity_filter']>0) ? 'checked' : ''); ?>><label class="w3-text-green">Activity</label>
<select class="w3-select w3-text-green" name="activity" id="activity" <?php echo(($_SESSION['log_activity_filter']>0) ? '' : 'disabled'); ?>>
<?php
$activities=getActivities($dbh);
for($i=0;$i<sizeof($activities);$i++){
	$activity=$activities[$i];
	$selected = ($activity[0]==$_SESSION['log_activity_filter']) ? 'selected' : '';
	echo('<option value="'.$activity[0].'"'.$selected.'>'.$activity[1].'</option>');
}
?>
</select>
</p>
<p>
<input class="w3-check" type="checkbox" id="toggle_measurement" name="toggle_measurement" onclick="toggleMeasurement()" <?php echo(($_SESSION['log_measurement_filter']>0) ? 'checked' : ''); ?>><label class="w3-text-green">Measurement</label>
<select class="w3-select w3-text-green" name="measurement" id="measurement" <?php echo(($_SESSION['log_measurement_filter']>0) ? '' : 'disabled'); ?>>
<?php
$measurements=getMeasurements($dbh);
for($i=0;$i<sizeof($measurements);$i++){
	$measurement=$measurements[$i];
	$selected = ($measurement[0]==$_SESSION['log_measurement_filter']) ? 'selected' : '';
	echo('<option value="'.$measurement[0].'"'.$selected.'>'.$measurement[1].'</option>');
}
?>
</select>
</p>
<p>
<input class="w3-check" type="checkbox" id="toggle_crop" name="toggle_crop" onclick="toggleCrop()" <?php echo(($_SESSION['input_log_crop_filter']>0) ? 'checked' : ''); ?>><label class="w3-text-green">Crop</label>
<select class="w3-select w3-text-green" name="crop" id="crop" <?php echo(($_SESSION['input_log_crop_filter']>0) ? '' : 'disabled'); ?>>
<?php
$crops=getCrops($dbh,-1);
for($i=0;$i<sizeof($crops);$i++){
	$crop=explode(",",$crops[$i]);
	$selected = ($crop[0]==$_SESSION['input_log_crop_filter']) ? 'selected' : '';
	echo('<option value="'.$crop[0].'"'.$selected.'>'.$crop[1].'</option>');
}
?>
</select>
</p>
<p>
<input class="w3-check" type="checkbox" id="toggle_treatment" name="toggle_treatment" onclick="toggleTreatment()" <?php echo(($_SESSION['input_log_treatment_filter']>0) ? 'checked' : ''); ?>><label class="w3-text-green">Treatment</label>
<select class="w3-select w3-text-green" name="treatment" id="treatment" <?php echo(($_SESSION['input_log_treatment_filter']>0) ? '' : 'disabled'); ?>>
<?php
$treatments=getTreatments($dbh);
for($i=0;$i<sizeof($treatments);$i++){
	$treatment=explode(",",$treatments[$i]);
	$selected = ($treatment[0]==$_SESSION['input_log_treatment_filter']) ? 'selected' : '';
	echo('<option value="'.$treatment[0].'"'.$selected.'>'.$treatment[1].'</option>');
}
?>
</select>
</p>
<p>
<label class="w3-text-green">Items per page</label><input class="w3-input w3-border-teal w3-text-green" type="text" name="max_messages" id="max_messages" value="<?php echo($max_messages); ?>" onkeypress="return isNumberKey(event)">
</p>
<button class="w3-button w3-green w3-round w3-border w3-border-green w3-medium w3-round-large" id="apply" name="apply">Apply</button> <button class="w3-button w3-green w3-round w3-border w3-border-green w3-medium w3-round-large" id="remove" name="remove">Remove all</button> <button class="w3-button w3-green w3-round w3-border w3-border-green w3-medium w3-round-large" onclick="javascript:window.close();">Close</button><br><br>
</form>
</div>
</body>
</html>
<?php
}
?>