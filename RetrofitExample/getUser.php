<?php
 require_once('dbConnect.php');
 
 $sql = "SELECT * FROM retrofit_users";
 
 $result = mysqli_query($con,$sql);
 
$rows = array();
while($temp = mysqli_fetch_assoc($result)) {
    $rows[] = $temp;
}
echo json_encode($rows);
 
?>