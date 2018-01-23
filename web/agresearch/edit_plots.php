<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

$proceed=false;

if(isset($_POST['add'])){
	$id=$_POST['id'];
	$task=$_POST['task'];
	if($task=="lm"){
		$query="SELECT field_id FROM log WHERE log_id=$id";
		$result = mysqli_query($dbh,$query);
		$row = mysqli_fetch_array($result,MYSQL_NUM);
		$field_id=$row[0];
		$add=$_POST['added_plot'];
		if($add!=""){
			$plot_list=$add;
			$plots=explode(",",$plot_list);
			$query="UPDATE log SET plots='".$plot_list."' WHERE log_id=$id";
			$result = mysqli_query($dbh,$query);
		}
	} else {
		if($task=="la") {
			$query="SELECT plots, field_id FROM log WHERE log_id=$id";
		} else {
			$query="SELECT plots, field_id FROM input_log WHERE input_log_id=$id";
		}
		$result = mysqli_query($dbh,$query);
		$row = mysqli_fetch_array($result,MYSQL_NUM);
		$plot_list = $row[0];
		$field_id=$row[1];
		$plots = explode(",",$plot_list);
	
		$add=$_POST['added_plot'];
		if($add!=""){
			$plot_list.=",".$add;
			$plots = explode(",",$plot_list);
			if($task=="la"){
				$query="UPDATE log SET plots='".$plot_list."' WHERE log_id=$id";
			} else {
				$query="UPDATE input_log SET plots='".$plot_list."' WHERE input_log_id=$id";
			}
			$result = mysqli_query($dbh,$query);
		}
		
	}
	
	$proceed=true;
	
} else if(isset($_POST['back'])){
	$id=$_POST['id'];
	$task=$_POST['task'];
	
	if($task=="lm"){
		header("Location: edit_lm.php?id=$id");
	} else if ($task=="la"){
		header("Location: edit_la.php?id=$id");
	} else if ($task=="it"){
		header("Location: edit_ilt.php?id=$id");
	} else if ($task=="ic"){
		header("Location: edit_ilc.php?id=$id");
	}
	
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true && isset($_GET['id'])){
	$id=$_GET['id'];
	$task=$_GET['task'];
	if($task=="lm" || $task=="la"){
		$query="SELECT plots, field_id FROM log WHERE log_id=$id";
	} else {
		$query="SELECT plots, field_id FROM input_log WHERE input_log_id=$id";
	}
	$result = mysqli_query($dbh,$query);
	$row = mysqli_fetch_array($result,MYSQL_NUM);
	$plot_list = $row[0];
	$field_id=$row[1];
	$plots = explode(",",$plot_list);
	
	if(isset($_GET['remove'])){
		$remove=$_GET['remove'];
		$plot_list="";
		for($i=0;$i<sizeof($plots);$i++){
			if($remove!=$plots[$i]){
				if($plot_list==""){
					$plot_list=$plots[$i];
				} else {
					$plot_list.=",".$plots[$i];
				}
			}
		}
		if($task=="lm" || $task=="la"){
			$query="UPDATE log SET plots='".$plot_list."' WHERE log_id=$id";
		} else {
			$query="UPDATE input_log SET plots='".$plot_list."' WHERE input_log_id=$id";
		}
		$result = mysqli_query($dbh,$query);
		$plots=explode(",",$plot_list);
	}
	
	$proceed=true;
	
}

if($proceed){
?>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/w3.css">
<title>Agroeco Research</title>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Edit plots</h2>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<input name="id" type="hidden" id="id" value="<? echo($id); ?>">
<input name="task" type="hidden" id="task" value="<? echo($task); ?>">
<p><div class="w3-text-green">
<b><?php if($task=="lm") { echo("Affected plot"); } else { echo("Affected plots"); } ?>:</b><br>
<?php
	for($i=0;$i<sizeof($plots);$i++){
		$plot=$plots[$i];
		$plot_name=calculatePlotLabels($dbh,$field_id,$plot);
		if($task!="lm"){
			$remove_link='<a href="edit_plots.php?id='.$id.'&task='.$task.'&remove='.$plot.'">Remove</a><br>';
		} else {
			$remove_link='';
		}
		echo($plot_name.' '.$remove_link);
	}
?><br><br>
<?php
	$remaining_plots=getRemainingPlots($dbh,$field_id,$plots,$id,$task);
	if($remaining_plots!=""){
?>
<b>Available plots:</b><br>
<select class="w3-select w3-text-green" name="added_plot">
<?php
	$remaining_plots_list=explode(",",$remaining_plots);
	for($i=0;$i<sizeof($remaining_plots_list);$i++){
		$remaining_plot=$remaining_plots_list[$i];
		$plot_name=calculatePlotLabels($dbh,$field_id,$remaining_plot);
		echo('<option value="'.$remaining_plot.'">'.$plot_name.'</option>');
	}
?>
</select><br><br><button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" id="add" name="add"><?php if($task=="lm") { echo("Change"); } else { echo("Add"); } ?></button><br><br>
<?php
}
?>
</div>
<button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" id="back" name="back">Back</button> <button class="w3-button w3-green w3-round w3-border w3-border-green w3-large w3-round-large" onclick="javascript:window.close();">Close</button><br><br>
</form>
</div>
</body>
</html>
<?php
} 
?>