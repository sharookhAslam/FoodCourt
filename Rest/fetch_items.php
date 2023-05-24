<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$stall_id = $_POST['stall_id'];

$sql1 = "SELECT item_id, title, price, description, qty, additional, category, picture, mall_id  FROM items WHERE stall_id ='{$stall_id}'";


$response = array();
$response['data'] = array();

$result = mysqli_query($con, $sql1);

while($row = mysqli_fetch_assoc($result)){
  
  $ds['item_id'] = $row['item_id'];
  $ds['title'] = $row['title'];
  $ds['price'] = $row['price'];
  $ds['mall_id'] = $row['mall_id'];
  $ds['description'] = $row['description'];
  $ds['picture'] = $row['picture'];
  $ds['category'] = $row['category'];
  $ds['additional'] = $row['additional'];
  
  
  array_push($response['data'], $ds);
  
 }
 
 $response['success'] = "1";
 echo json_encode($response);


?>