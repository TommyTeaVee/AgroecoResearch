<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "./../includes/init_database.php";
include_once "./../includes/variables.php";
include_once "./../includes/functions.php";
$dbh = initDB();

session_start();

if($_SERVER["REQUEST_METHOD"] == "POST") {
	if(isset($_POST['edit_crop'])){
		$crop_id=$_POST['id'];
		$crop_name=normalize($_POST['crop_name']);
		$crop_variety_name=normalize($_POST['crop_variety_name']);
		$query="UPDATE crop SET crop_name='$crop_name', crop_variety_name='$crop_variety_name' WHERE crop_id=$crop_id";
		$result = mysqli_query($dbh,$query);
		header("Location: crops.php");
	} else if(isset($_POST['cancel'])){
		header("Location: crops.php");
	}
} else if($_SERVER["REQUEST_METHOD"] == "GET" && isset($_SESSION['admin']) && $_SESSION['admin']==true) {
	$crop_id=$_GET['id'];
	$query="SELECT crop_name, crop_variety_name FROM crop WHERE crop_id=$crop_id";
	$result = mysqli_query($dbh,$query);
	while($row = mysqli_fetch_array($result,MYSQL_NUM)){
		$crop_name=$row[0];
		$crop_variety_name=$row[1];
	}
?>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./../css/w3.css">
<title>Agroeco Research</title>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Edit crop</h2>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<input name="id" type="hidden" id="id" value="<? echo($crop_id); ?>">
<p>      
<label class="w3-text-green">Crop name:</label>
<input class="w3-input w3-border-green w3-text-green" name="crop_name" type="text" maxlength="20" value="<?php echo($crop_name); ?>"></p>
<p>      
<label class="w3-text-green">Crop variety:</label>
<input class="w3-input w3-border-green w3-text-green" name="crop_variety_name" type="text" maxlength="40" value="<?php echo($crop_variety_name); ?>"></p>
<button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="edit_crop" name="edit_crop">Edit crop</button> <button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="cancel" name="cancel">Cancel</button></form><br>
<br><br></div>
</body>
</html>
<?php
}
?>