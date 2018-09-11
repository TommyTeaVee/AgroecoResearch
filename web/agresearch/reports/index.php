<?php
header("Cache-Control: no-cache, must-revalidate");
session_start();

if($_SERVER["REQUEST_METHOD"] == "POST") {
	if(isset($_POST['menu'])){
		header("Location: ./../menu.php");
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
<script language="javascript" type="text/javascript">
<!--
function showPopup(url,width,height) {
	newwindow=window.open(url,'name','height=' + height +',width=' + width + ',top=50,left=50,screenX=50,screenY=50');
	if (window.focus) {newwindow.focus()}
	return false;
}
// -->
</script>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Reports</h2>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<div align="center"><h4 class="w3-green">Reports are generated as CSV files. To open a report in Excel, please use 'comma' as separator and 'double quotes' as text delimiter.</h4></div><br>
<p>
<div align="center"><button class="w3-button w3-green w3-round w3-border w3-border-green" style="width:40%; height:40px; max-width:500px;" id="monthly" name="monthly" onclick="return showPopup('monthly.php',800,700)">Monthly report</button><br><br><button class="w3-button w3-green w3-round w3-border w3-border-green" style="width:40%; height:40px; max-width:500px;" id="timeline" name="timeline" onclick="return showPopup('timeline.php',800,700)">Timeline</button><br><br><button class="w3-button w3-green w3-round w3-border w3-border-green" style="width:40%; height:40px; max-width:500px;" id="singlesample" name="singlesample" onclick="return showPopup('singlesample.php',800,700)">Single sample measurements</button><br><br><button class="w3-button w3-green w3-round w3-border w3-border-green" style="width:40%; height:40px; max-width:500px;" id="plantsize" name="plantsize" onclick="return showPopup('plantsize.php',800,700)">Maize plant size measurements</button><br><br><button class="w3-button w3-green w3-round w3-border w3-border-green" style="width:40%; height:40px; max-width:500px;" id="multisample" name="multisample" onclick="return showPopup('multisample.php',800,700)">Multiple sample measurements</button><br><br><button class="w3-button w3-green w3-round w3-border w3-border-green" style="width:40%; height:40px; max-width:500px;" id="menu" name="menu">Menu</button></div><br>
</form><br>
</div>
</body>
</html>
<?php
} else {
        header("Location: ./../index.php");
}
?>