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
	$age=intval($_POST['age']);
	$origin=$_POST['origin'];
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
	
	$query="UPDATE input_log SET input_log_date='$date', input_age=$age, input_origin='$origin', input_quantity=$quantity, input_cost=$cost, input_comments='$comments'".$update_picture." WHERE input_log_id=$id";
	$result = mysqli_query($dbh,$query);
	echo "<script type='text/javascript'>";
	echo "window.opener.location.reload(false);";
	echo "window.close();";
	echo "</script>";
	
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true && isset($_GET['id'])){
	
	$id=$_GET['id'];
	$field=$_GET['field'];
	
	$plots=getAllPlots($dbh,$field);
	$plot_list=explode(",",$plots);
	
	$query="SELECT crop_name FROM crop WHERE crop_id=$id";
	$result = mysqli_query($dbh,$query);
	$row = mysqli_fetch_array($result,MYSQL_NUM);
	$crop_name = $row[0];

	$yy=date('Y');
	$mm=date('m');
	$dd=date('d');
	
?>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<title>Agroeco Research</title>
<script>
       <!--
	   
	   var selected_plots=[];
	   
       function isNumberKey(evt)
       {
          var charCode = (evt.which) ? evt.which : evt.keyCode;
          if (charCode != 46 && charCode > 31 
            && (charCode < 48 || charCode > 57))
             return false;

          return true;
       }
	   
	   function addPlot(){
		var e = document.getElementById("a_plots");
		var plot_id = e.options[e.selectedIndex].value;
		var plot_name = e.options[e.selectedIndex].text;
		//TODO
	   }
       //-->
</script>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Add crop input</h2>
<form method="post" enctype="multipart/form-data" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<input name="id" type="hidden" id="id" value="<? echo($id); ?>">
<input name="field" type="hidden" id="field" value="<? echo($field); ?>">
<input name="plots" type="hidden" id="plots" value="">
<p><div class="w3-text-green">
<b>Registered by:</b> <?php echo(getUserNameFromId($dbh,$_SESSION['user_id'])); ?><br>
<b>Field:</b> <?php echo(getFieldNameFromId($dbh,$field)); ?><br>
<b>Plots:</b> <span id="inlcluded_plots"></span><br>
<span id="available_plots">
<select class="w3-select w3-text-green" name="a_plots" id="a_plots" onchange="addPlot();">
  <option value="" selected disabled>Available plots:</option>
<?php
for($i=0;$i<sizeof($plot_list);$i++){
	echo('<option value="'.$i.'">'.$plot_list[$i].'</option>');
}
?>
</select>
</span>
<br><br>
<b>Crop:</b> <?php echo($crop_name); ?><br><br>
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
<br><b>Age (days):</b> <input class="w3-input w3-border-green w3-text-green" name="age" type="text" value="" onkeypress="return isNumberKey(event)">
<b>Origin:</b> <input class="w3-input w3-border-green w3-text-green" name="origin" type="text" maxlength="200" value="">
<b>Quantity (units):</b> <input class="w3-input w3-border-green w3-text-green" name="quantity" type="text" value="" onkeypress="return isNumberKey(event)">
<b>Cost (local currency):</b> <input class="w3-input w3-border-green w3-text-green" name="cost" type="text" value="" onkeypress="return isNumberKey(event)">
<br>
<b>Image:</b> <input class="w3-input w3-border-green w3-text-green" name="input_picture" type="file" id="input_picture" accept=".jpg,.png">
<b>Comments:</b> <input class="w3-input w3-border-green w3-text-green" name="comments" type="text" value="">
</div>
</p>
<button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" id="add" name="add">Add</button> <button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" onclick="javascript:window.close();">Close</button><br><br></form>
<?php
} 
?>