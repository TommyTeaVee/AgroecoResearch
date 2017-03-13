<?php
header("Cache-Control: no-cache, must-revalidate");
session_start();

if($_SERVER["REQUEST_METHOD"] == "POST") {
	if(isset($_POST['users'])){
		header("Location: users.php");
	}
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true){
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
<h2 class="w3-green">Menu</h2><br>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<p>
<button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="users" name="users">Users</button> 
<button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="crops" name="crops">Crops</button> 
<button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="treatments" name="login">Treatments</button>
<br><br>
</form><br><br>
</div>
</body>
</html>
<?php
}
?>