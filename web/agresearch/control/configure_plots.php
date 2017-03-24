<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "./../includes/init_database.php";
include_once "./../includes/variables.php";
include_once "./../includes/functions.php";
$dbh = initDB();

session_start();

if($_SERVER["REQUEST_METHOD"] == "POST") {
	$field_id=$_POST['field_id'];
	$config=$_POST['config'];
	if(isset($_POST['update'])){
		$query="UPDATE field SET field_configuration='$config' WHERE field_id=$field_id";
		$result = mysqli_query($dbh,$query);
		header("Location: fields.php");
	} else if(isset($_POST['update_copy'])){
		$parent_field_id=getParentField($field_id,$dbh);
		$query="UPDATE field SET field_configuration='$config' WHERE parent_field_id=$parent_field_id";
		$result = mysqli_query($dbh,$query);
		header("Location: fields.php");
	} else if(isset($_POST['cancel'])){
		header("Location: fields.php");
	}
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true) {
	$field_id=$_GET['id'];
	$fname=$_GET['fname'];
	$config=getFieldConfiguration($field_id,$dbh);
	$config_parts=explode(";",$config);
	$general=parseConfig($config_parts[0]);
	$grid=parseConfig($config_parts[1]);
	$rows=$grid[0];
	$columns=$grid[1];
	$cropsNI=getCrops($dbh,0);
	$cropsI=getCrops($dbh,1);
?>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./../css/w3.css">
<title>Agroeco Research</title>
<script type="text/javascript">
	<?php
		$js_crops_ni="";
		for($i=0;$i<sizeof($cropsNI);$i++){
			$parts=explode(",",$cropsNI[$i]);
			$id=$parts[0];
			$name=$parts[1];
			if($js_crops_ni==""){
				$js_crops_ni='"'.$id.','.$name.'"';
			} else {
				$js_crops_ni.=',"'.$id.','.$name.'"';
			}
		}
		$js_crops_i="";
		for($i=0;$i<sizeof($cropsI);$i++){
			$parts=explode(",",$cropsI[$i]);
			$id=$parts[0];
			$name=$parts[1];
			if($js_crops_i==""){
				$js_crops_i='"'.$id.','.$name.'"';
			} else {
				$js_crops_i.=',"'.$id.','.$name.'"';
			}
		}
	?>
	var cropsNI=[<?php echo($js_crops_ni); ?>];
	var cropsI=[<?php echo($js_crops_i); ?>];
</script>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Configure plots: <?php echo($fname); ?></h2><br>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" id="config">
<input name="id" id="id" type="hidden" value="<?php  echo($field_id); ?>">
<input name="config" id="config" type="hidden" value="<?php  echo($config); ?>">
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
		echo('<td id="cell'.($p-2).'" class="'.$color.'" style="height:100px; padding:3px; border: 3px solid white;"><div align="center"><strong><a class="w3-text-black" href="javascript:displayPlotConfiguration('.($p-2).',\''.implode(",",$plot).'\')">'.$content.'</a></strong></div></td>');
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
</div><div class="w3-half w3-text-green"><div id="plotNumber"></div><br><div id="plotCrop"></div><br>
<div id="plotIntercropping"></div><br>
<div id="plotIntercroppingCrop"></div><br>
<div id="plotSoilManagement"></div><br>
<div id="plotPestControl"></div><br>
</div>
</div>
<script type="text/javascript">
	function displayPlotConfiguration(plot,plotConfig){
		var configParts=plotConfig.split(",");
		document.getElementById("plotNumber").innerHTML='<span class="w3-green">&nbsp;Plot number '+ (plot+1) +':&nbsp;</span>';
		var i;
		var options="";
		var selected="";
		var id;
		var name;
		for(i=0;i<cropsNI.length;i++){
			selected="";
			parts=cropsNI[i].split(",");
			id=parts[0];
			name=parts[1];
			if(id==configParts[0]){
				selected=" selected";
			}
			options+='<option value="'+id+'"'+selected+'>'+name+'</option>';
		}
		document.getElementById("plotCrop").innerHTML='<select class="w3-select w3-text-green" name="primary_crop" id="primary_crop">'+ options +'</select>';
		if(configParts[1]==0){
			document.getElementById("plotIntercropping").innerHTML='<input class="w3-check" type="checkbox" value="1" name="intercropping" id="intercropping"><label class="w3-validate w3-text-green">Intercropping</label>';
			document.getElementById("plotIntercroppingCrop").innerHTML='';
		} else {
			document.getElementById("plotIntercropping").innerHTML='<input class="w3-check" type="checkbox" value="1" name="intercropping" id="intercropping" checked><label class="w3-validate w3-text-green">Intercropping</label>';
			options="";
			for(i=0;i<cropsI.length;i++){
				selected="";
				parts=cropsI[i].split(",");
				id=parts[0];
				name=parts[1];
				if(id==configParts[0]){
					selected=" selected";
				}
				options+='<option value="'+id+'"'+selected+'>'+name+'</option>';
			}
			document.getElementById("plotIntercroppingCrop").innerHTML='<select class="w3-select w3-text-green" name="intercropping_crop" id="intercropping_crop">'+ options +'</select>';
		}
		if(configParts[2]==0){
			document.getElementById("plotSoilManagement").innerHTML='<input class="w3-check" type="checkbox" value="1" name="soil_management" id="soil_management"><label class="w3-validate w3-text-green">Soil management</label>';
		} else {
			document.getElementById("plotSoilManagement").innerHTML='<input class="w3-check" type="checkbox" value="1" name="soil_management" id="soil_management" checked><label class="w3-validate w3-text-green">Soil management</label>';
		}
		if(configParts[3]==0){
			document.getElementById("plotPestControl").innerHTML='<input class="w3-check" type="checkbox" value="1" name="pest_control" id="pest_control"><label class="w3-validate w3-text-green">Pest control</label>';
		} else {
			document.getElementById("plotPestControl").innerHTML='<input class="w3-check" type="checkbox" value="1" name="pest_control" id="pest_control" checked><label class="w3-validate w3-text-green">Pest control</label>';
		}
	}
</script>
</p><button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="update" name="update">Update configuration</button> <button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="update_copy" name="update_copy">Update configuration and copy to replications</button> <button class="w3-button w3-padding-large w3-green w3-round w3-border w3-border-green" id="cancel" name="cancel">Cancel</button><br><br>
</div></form>
</body>
</html>
<?php
}
?>