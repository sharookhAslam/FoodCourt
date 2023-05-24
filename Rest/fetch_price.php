<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$user_id = $_POST['user_id'];

$sql1 = "SELECT id, price FROM temp_cart WHERE user_id='{$user_id}'";

$response = array();
$response['data'] = array();

$result = mysqli_query($con, $sql1);

while($row = mysqli_fetch_assoc($result)){
  
  $ds['id'] = $row['id'];
  $ds['price'] = $row['price'];


 

  
  
  array_push($response['data'], $ds);
  
 }
 
 $response['success'] = "1";
 echo json_encode($response);


?>