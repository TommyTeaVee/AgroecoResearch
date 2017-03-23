<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "./../includes/init_database.php";
include_once "./../includes/variables.php";
include_once "./../includes/functions.php";
$dbh = initDB();

session_start();

if(isset($_SESSION['admin']) && $_SESSION['admin']==true) {
	$field_id=$_GET['id'];
	$fname=$_GET['fname'];
	$config=getFieldConfiguration($field_id,$dbh);
	$config_parts=explode(";",$config);
	$general=parseConfig($config_parts[0]);
	$grid=parseConfig($config_parts[1]);
	$rows=$grid[0];
	$columns=$grid[1];
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
<h2 class="w3-green">Configure plots: <?php echo($fname); ?></h2><br>
<p><div class="w3-row-padding"><div class="w3-half w3-text-green">
<table>
<?php
$p=2;
$n=0;
$c=0;
$crops=array();
$last_crop=-1;
$used_colors=array();
for($i=1;$i<=$rows;$i++){
	echo("<tr>");
	for($j=1;$j<=$columns;$j++){
		$plot=parseConfig($config_parts[$p]);
		if($plot[2]==0 && $plot[3]==0){
			$color=$cell_color[0];
		} else if($plot[2]==1 && $plot[3]==0){
			$color=$cell_color[1];
		} else if($plot[2]==0 && $plot[3]==1){
			$color=$cell_color[2];
		} else if($plot[2]==1 && $plot[3]==1){
			$color=$cell_color[3];
		}
		if(!in_array($color,$used_colors)){
			$used_colors[$c]=$color;
			$c++;
		}
		if($last_crop==-1 || $last_crop!=$plot[0]){
			$crops[$n]=$plot[0];
			$last_crop=$plot[0];
			$n++;
		} 
		if($general[0]==1){
			$content="C";
		} else {
			$crop_number=array_search($plot[0],$crops)+1;
			$content="C".$crop_number;
		}
		if($plot[1]!=0){
			$content.="+L";
		}
		echo('<td id="cell'.($p-2).'" class="'.$color.'" style="height:100px; padding:3px; border: 3px solid white;"><div align="center"><strong>'.$content.'</strong></div></td>');
		$p++;
	}
	echo("</tr>");
}
?>
</table><br>
<table>
<?php
for($i=0;$i<sizeof($used_colors);$i++){
	echo("<tr>");
	echo('<td class="'.$used_colors[$i].'" style="width:10%; padding:3px; border: 3px solid white;">&nbsp;</td>');
	if($used_colors[$i]=="w3-khaki"){
		$legend="No treatment";
	} else if($used_colors[$i]=="w3-yellow") {
		$legend="Soil management";
	} else if($used_colors[$i]=="w3-lime") {
		$legend="Pest control";
	} else if($used_colors[$i]=="w3-light-green") {
		$legend="Soil management AND Pest control";
	}
	echo('<td class="w3-text-black" style="padding:3px; border: 3px solid white;">'.$legend.'</td>');
	echo("</tr>");
}
?>
</table><br>
</div><div class="w3-half w3-text-green"><?php echo($config_parts[1]); ?>
</div>
</div>
</p>
</div>
</body>
</html>
<?php
}
?>