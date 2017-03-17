<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "./../includes/init_database.php";
include_once "./../includes/variables.php";
include_once "./../includes/functions.php";
$dbh = initDB();

session_start();

if($_SERVER["REQUEST_METHOD"] == "POST") {
	if(isset($_POST['edit_measurement'])){
		$measurement_id=$_POST['measurement_id'];
		$measurement_name=normalize($_POST['measurement_name']);
		$c=$_POST['measurement_category'];
		if($c=="-1"){
			$measurement_category=normalize($_POST['other_measurement_category']);
		} else {
			$measurement_category=normalize($c);
		}
		$measurement_type=$_POST['measurement_type'];
		if($measurement_type==0){
			$measurement_range_min=0;
			$measurement_range_max=0;
			$measurement_units="";
			$measurement_categories=normalize($_POST['measurement_categories']);
		} else if($measurement_type==1) {
			$measurement_range_min=$_POST['measurement_range_min'];
			$measurement_range_max=$_POST['measurement_range_max'];
			$measurement_units=normalize($_POST['measurement_units']);
			$measurement_categories="";
		} else {
			$measurement_type=1;
			$measurement_range_min=0;
			$measurement_range_max=0;
			$measurement_units="";
			$measurement_categories="";
		}
		$measurement_periodicity=$_POST['measurement_periodicity'];
		$query="UPDATE measurement SET measurement_name='$measurement_name', measurement_category='$measurement_category', measurement_type=$measurement_type, measurement_range_min=$measurement_range_min, measurement_range_max=$measurement_range_max, measurement_units='$measurement_units', measurement_categories='$measurement_categories', measurement_periodicity=$measurement_periodicity WHERE measurement_id=$measurement_id";
		$result = mysqli_query($dbh,$query);
		header("Location: measurements.php");
	} else if(isset($_POST['cancel'])){
		header("Location: measurements.php");
	}
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true) {
	$measurement_id=$_GET['id'];
	$query="SELECT measurement_name, measurement_category, measurement_type, measurement_range_min, measurement_range_max, measurement_units, measurement_categories, measurement_periodicity FROM measurement WHERE measurement_id=$measurement_id";
	$result = mysqli_query($dbh,$query);
	if($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$measurement_name=$row[0];
		$measurement_category=$row[1];
		$measurement_type=$row[2];
		$measurement_range_min=$row[3];
		$measurement_range_max=$row[4];
		$measurement_units=$row[5];
		$measurement_categories=$row[6];
		$measurement_periodicity=$row[7];
	}
	$measurement_categories_catalog=getMeasurementCategories($dbh);
?>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./../css/w3.css">
<title>Agroeco Research</title>
<script type="text/javascript">
	var crops=[];
</script>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Edit measurement</h2>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<input name="measurement_id" type="hidden" id="measurement_id" value="<? echo($measurement_id); ?>">
<p>      
<label class="w3-text-green">Measurement name:</label>
<input class="w3-input w3-border-green w3-text-green" name="measurement_name" type="text" maxlength="100" value="<?php echo("$measurement_name"); ?>"></p>
<p><select class="w3-select w3-text-green" name="measurement_category" id="measurement_category">
  <option value="" disabled>Category:</option>
<?php
for($i=0;$i<sizeof($measurement_categories_catalog);$i++){
	if($measurement_category==$measurement_categories_catalog[$i]){
		echo('<option value="'.$measurement_categories_catalog[$i].'" selected>'.$measurement_categories_catalog[$i].'</option>');
	} else {
		echo('<option value="'.$measurement_categories_catalog[$i].'">'.$measurement_categories_catalog[$i].'</option>');
	}
}
?>
<option value="-1">Other</option>
</select>
<div id="otherfield"></div></p>
<script type="text/javascript">
	document.getElementById("measurement_category").onclick = function () {
		if(document.getElementById("measurement_category").value=="-1"){
			document.getElementById("otherfield").innerHTML='<label class="w3-text-green">Enter category:</label><input class="w3-input w3-border-green w3-text-green" name="other_measurement_category" type="text" maxlength="30">';
		} else {
			document.getElementById("otherfield").innerHTML='';
		}
    };
</script>
<p><select class="w3-select w3-text-green" name="measurement_type" id="measurement_type">
  <option value="" disabled>Type:</option>
  <option value="0" <?php echo($measurement_type == 0 ? 'selected' : ''); ?>>Qualitative</option>
  <option value="1" <?php echo($measurement_type == 1 ? 'selected' : ''); ?>>Quantitative</option>
</select>
<div id="quantiquali"></div></p>
<script type="text/javascript">
	document.getElementById("quantiquali").innerHTML='<?php
		if($measurement_type==0) {
			echo('<label class="w3-text-green">Enter categories (separated by commas):</label><input class="w3-input w3-border-green w3-text-green" name="measurement_categories" type="text" value="'.$measurement_categories.'">');
		} else if($measurement_type==1) {
			echo('<div class="w3-row-padding"><div class="w3-third w3-text-green">Min range:<input class="w3-input w3-border-green w3-text-green" name="measurement_range_min" type="text" value="'.$measurement_range_min.'"></div><div class="w3-third w3-text-green">Max range:<input class="w3-input w3-border-green w3-text-green" name="measurement_range_max" type="text" value="'.$measurement_range_max.'"></div><div class="w3-third w3-text-green">Units:<input class="w3-input w3-border-green w3-text-green" name="measurement_units" type="text" maxlenght="30" value="'.$measurement_units.'"></div></div>');
		}
	?>';
	document.getElementById("measurement_type").onclick = function () {
		if(document.getElementById("measurement_type").value=="0"){
			document.getElementById("quantiquali").innerHTML='<label class="w3-text-green">Enter categories (separated by commas):</label><input class="w3-input w3-border-green w3-text-green" name="measurement_categories" type="text">';
		} else if(document.getElementById("measurement_type").value=="1"){
			document.getElementById("quantiquali").innerHTML='<div class="w3-row-padding"><div class="w3-third w3-text-green">Min range:<input class="w3-input w3-border-green w3-text-green" name="measurement_range_min" type="text"></div><div class="w3-third w3-text-green">Max range:<input class="w3-input w3-border-green w3-text-green" name="measurement_range_max" type="text"></div><div class="w3-third w3-text-green">Units:<input class="w3-input w3-border-green w3-text-green" name="measurement_units" type="text" maxlenght="30"></div></div>';
		} else {
			document.getElementById("quantiquali").innerHTML='';
		}
    };
</script> 
<p><label class="w3-text-green">Periodicity (days):</label>
<input class="w3-input w3-border-green w3-text-green" name="measurement_periodicity" type="text" maxlength="10" value="<?php echo($measurement_periodicity); ?>"></p>
<br><button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="edit_measurement" name="edit_measurement">Edit measurement</button> <button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="cancel" name="cancel">Cancel</button></form><br>
<br><br></div>
</body>
</html>
<?php
}
?>