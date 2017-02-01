<?php
 //Change the values according to your database
 
 define('HOST','localhost');
 define('USER','root');
 define('PASS','');
 define('DB','u502452270_andro');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
 
?>