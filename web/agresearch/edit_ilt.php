<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_POST['edit'])){
	
	$id=$_POST['id'];
	$dd=$_POST['dd'];
	$mm=$_POST['mm'];
	$yyyy=$_POST['yyyy'];
	$date=$yyyy."-".$mm."-".$dd;
	$material=$_POST['material'];
	$method=$_POST['method'];
	$quantity=floatval($_POST['quantity']);
	$cost=floatval($_POST['cost']);
	$comments=$_POST['comments'];
	if(isset($_FILES['input_picture']['name'])){
		$image_file=$_FILES['input_picture']['name'];
		$upload = "images/".$image_file;
		if(is_uploaded_file($_FILES['input_picture']['tmp_name'])) {
			move_uploaded_file($_FILES['input_picture']['tmp_name'],$upload);
			$update_picture=", input_picture='$upload'";
		} else {
			$update_picture="";
		}
	} else {
		$update_picture="";
	}
	
	$query="UPDATE input_log SET input_log_date='$date', input_treatment_material='$material', input_treatment_preparation_method='$method', input_quantity=$quantity, input_cost='$cost', input_comments='$comments'".$update_picture." WHERE input_log_id=$id";
	$result = mysqli_query($dbh,$query);
	echo "<script type='text/javascript'>";
	echo "window.opener.location.reload(false);";
	echo "window.close();";
	echo "</script>";
	
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true && isset($_GET['id'])){
	
	$id=$_GET['id'];
	$query="SELECT input_log_id, input_log_date, field_name, field_replication_number, plots, treatment_name, input_treatment_material, input_treatment_preparation_method, input_quantity, input_cost, input_comments, input_picture, field.field_id, input_log.user_id FROM input_log, field, treatment WHERE input_log_id=$id AND field.field_id = input_log.field_id AND treatment.treatment_id = input_log.treatment_id";
	$result = mysqli_query($dbh,$query);
	$row = mysqli_fetch_array($result,MYSQL_NUM);
	
	$plot_labels = calculatePlotLabels($dbh,$row[12],$row[4]);
	
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
<form method="post" enctype="multipart/form-data" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<input name="id" type="hidden" id="id" value="<? echo($id); ?>">
<p><div class="w3-text-green">
<b>Registered by:</b> <?php echo(getUserNameFromId($dbh,$row[13])); ?><br>
<b>Field:</b> <?php echo($row[2]." replication ".$row[3]); ?><br>
<b>Plots:</b> <?php echo($plot_labels); ?> <a href="edit_plots.php?task=it&id=<?php echo($id); ?>">Edit</a><br>
<b>Treatment:</b> <?php echo($row[5]. " (".$row[6].")"); ?><br><br>
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
<b>Material used:</b> <input class="w3-input w3-border-green w3-text-green" name="material" type="text" value="<?php echo($row[6]); ?>">
<b>Preparation method:</b> <input class="w3-input w3-border-green w3-text-green" name="method" type="text" value="<?php echo($row[7]); ?>">
<b>Quantity (units):</b> <input class="w3-input w3-border-green w3-text-green" name="quantity" type="text" value="<?php echo($row[8]); ?>" onkeypress="return isNumberKey(event)">
<b>Cost (local currency):</b> <input class="w3-input w3-border-green w3-text-green" name="cost" type="text" value="<?php echo($row[9]); ?>" onkeypress="return isNumberKey(event)">
<?php
if($row[11]!=""){
	$filename=$row[11];
	list($width, $height)=getimagesize($filename);
	$w=$width*(150/$height);
	$h=150;
?>
<br><img src="<?php echo($filename); ?>" width="<?php echo($w); ?>" height="<?php echo($h); ?>"><br>
<b>Replace image:</b> 
<?php	
} else {
?>
<b>Add an image:</b> 
<?php	
}
?>
<input class="w3-input w3-border-green w3-text-green" name="input_picture" type="file" id="input_picture" accept=".jpg,.png">
<b>Comments:</b> <input class="w3-input w3-border-green w3-text-green" name="comments" type="text" value="<?php echo($row[10]); ?>">
</div>
</p>
<button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" id="edit" name="edit">Edit</button> <button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" onclick="javascript:window.close();">Close</button><br><br></form>
<?php
} 
?>