<?php
$fruit = array('apple','orange','grape');
$key = array_search('orange', $fruit);
echo($key."-".sizeof($fruit));

$fruit = array();
echo("<br>".sizeof($fruit));

for($i=0;$i<5;$i++){
	echo("<br>*");
}
?>