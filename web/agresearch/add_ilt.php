<?php
header("Cache-Control: no-cache, must-revalidate");
include_once "includes/init_database.php";
include_once "includes/variables.php";
include_once "includes/functions.php";
$dbh = initDB();
session_start();

if(isset($_POST['add'])){
	
	$id=$_POST['id'];
	$field=$_POST['field'];
	$plots=$_POST['plots'];
	$user=$_SESSION['user_id'];
	
	$dd=$_POST['dd'];
	$mm=$_POST['mm'];
	$yyyy=$_POST['yyyy'];
	$date=$yyyy."-".$mm."-".$dd;
	$material=reverseParseIngredients($_POST['material']);
	if($material==-1){
		$material="";
	} 
	$method=$_POST['method'];
	$cost=floatval($_POST['cost']);
	$comments=$_POST['comments'];
	if(isset($_FILES['input_picture']['name'])){
		$image_file=$_FILES['input_picture']['name'];
		$upload = "images/".$image_file;
		if(is_uploaded_file($_FILES['input_picture']['tmp_name'])) {
			move_uploaded_file($_FILES['input_picture']['tmp_name'],$upload);
			$picture=$upload;
		} else {
			$picture="";
		}
	} else {
		$picture="";
	}
	
	$query="INSERT INTO input_log (treatment_id, user_id, field_id, plots, input_log_date, input_treatment_material, input_treatment_preparation_method, input_cost, input_comments, input_picture) VALUES ($id,$user,$field,'$plots','$date','$material','$method',$cost,'$comments','$picture')";

	$result = mysqli_query($dbh,$query);
	echo "<script type='text/javascript'>";
	echo "window.opener.location.reload(false);";
	echo "window.close();";
	echo "</script>";
	
} else if(isset($_SESSION['admin']) && $_SESSION['admin']==true && isset($_GET['id'])){
	
	$id=$_GET['id'];
	$field=$_GET['field'];
	
	$query="SELECT treatment_name, treatment_category FROM treatment WHERE treatment_id=$id";
	$result = mysqli_query($dbh,$query);
	$row = mysqli_fetch_array($result,MYSQL_NUM);
	$treatment_name = $row[0];
	$treatment_category = $row[1];

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
		if(plot_id!=""){
			var plot_label = e.options[e.selectedIndex].text;
			var p = document.getElementById("plots");
			var pl = document.getElementById("plot_labels");
			if(p.value==""){
				p.value=plot_id;
				pl.value=plot_label;
			} else {
				p.value+=","+plot_id;
				pl.value+=", "+plot_label;
			}
			var ip = document.getElementById("included_plots");
			ip.innerHTML=formatPlotsHTML(pl.value,p.value);
		
			e.remove(e.selectedIndex);
			if(e.length==1){
				e.options[0].text="All plots added";
				e.disabled=true;
			} else {
				e.options[0].text="Add a plot:";
				e.disabled=false;
			}
		}
	   }
	   
	   function formatPlotsHTML(plots,ids){
		   var plot_labels_list = plots.split(",");
		   var plot_ids_list = ids.split(",");
		   var stringHTML="";
		   for(var i=0;i<plot_ids_list.length;i++){
			   if(stringHTML==""){
				   stringHTML='<a href="javascript:removePlot(\''+ plot_labels_list[i].trim() +'\','+ plot_ids_list[i] +')">'+ plot_labels_list[i].trim() +'</a>';
			   } else {
				   stringHTML+=', <a href="javascript:removePlot(\''+ plot_labels_list[i].trim() +'\','+ plot_ids_list[i] +')">'+ plot_labels_list[i].trim() +'</a>';
			   }
		   }
		   return stringHTML;
	   }
	   
	   function removePlot(plot,id){
		    
		    var e = document.getElementById("a_plots");
			var o = document.createElement("option");
			o.text = plot;
			o.value = id;
			e.add(o);
			e.options[0].text="Add a plot:";
			e.disabled=false;
			
			var p = document.getElementById("plots");
			var pl = document.getElementById("plot_labels");
			var plot_list = p.value.split(",");
			var plot_labels_list = pl.value.split(",");
			var string_ids="";
			var string_labels="";
			for (var i=0; i<plot_list.length; i++){
				if(plot_list[i]==id){
					
				} else {
					if(string_ids==""){
						string_ids=plot_list[i];
						string_labels=plot_labels_list[i];
					} else {
						string_ids+=","+plot_list[i];
						string_labels+=", "+plot_labels_list[i];
					}
				}
			}
			p.value=string_ids;
			pl.value=string_labels;
			var ip = document.getElementById("included_plots");
			ip.innerHTML=formatPlotsHTML(pl.value,p.value);
	   }
       //-->
</script>
</head>
<body>
<div class="w3-container w3-card-4">
<h2 class="w3-green">Add treatment input</h2>
<form method="post" enctype="multipart/form-data" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
<input name="id" type="hidden" id="id" value="<? echo($id); ?>">
<input name="field" type="hidden" id="field" value="<? echo($field); ?>">
<input name="plots" type="hidden" id="plots" value="">
<input name="plot_labels" type="hidden" id="plot_labels" value="">
<p><div class="w3-text-green">
<b>Registered by:</b> <?php echo(getUserNameFromId($dbh,$_SESSION['user_id'])); ?><br>
<b>Field:</b> <?php echo(getFieldNameFromId($dbh,$field)); ?><br>
<b>Plots (click on a plot to remove):</b> <span id="included_plots"></span><br>
<?php
$plots=getRemainingPlots($dbh,$field,"",($id*-1),"it");
if($plots!=""){
	$plot_list=explode(",",$plots);
	$plot_labels=calculatePlotLabels($dbh,$field,$plots);
	$plot_labels_list=explode(",",$plot_labels);
?>
<span id="available_plots">
<select class="w3-select w3-text-green" name="a_plots" id="a_plots" onchange="addPlot();">
  <option value="" selected>Add a plot:</option>
<?php

for($i=0;$i<sizeof($plot_list);$i++){
	echo('<option value="'.$plot_list[$i].'">'.$plot_labels_list[$i].'</option>');
}
?>
</select>
</span><br>
<?php }
?>
<br>
<b>Treatment:</b> <?php echo($treatment_name." (".$treatment_category.")"); ?><br><br>
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
<br><b>Ingredients (comma separated, use format: <i>ingredient: quantity units</i>):</b> <input class="w3-input w3-border-green w3-text-green" name="material" type="text" value="">
<b>Preparation method:</b> <input class="w3-input w3-border-green w3-text-green" name="method" type="text" value="">
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