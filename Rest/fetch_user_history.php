<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$user_id = $_POST['user_id'];


$sql1 = "SELECT item_id, mall_id, food_id, title, price, qty, order_id FROM order_detail WHERE user_id ='{$user_id}'";


$response = array();
$response['data'] = array();

$result = mysqli_query($con, $sql1);

if(mysqli_num_rows($result)>0){

while($row = mysqli_fetch_assoc($result)){
  
  $ds['item_id'] = $row['item_id'];
  $ds['title'] = $row['title'];
  $ds['price'] = $row['price'];
  $ds['mall_id'] = $row['mall_id'];
  $ds['qty'] = $row['qty'];
  $ds['food_id'] = $row['food_id'];
  $ds['order_id'] = $row['order_id'];
  
 $item_id = $row['item_id'];
  
  $sql2 = "SELECT picture FROM items WHERE item_id = '{$item_id}'";
  $result2 = mysqli_query($con, $sql2);
  $row2 = mysqli_fetch_assoc($result2);
    $ds['picture'] = $row2['picture'];
  
  
  
  
  array_push($response['data'], $ds);
  
 }
 
 $response['success'] = "1";
 echo json_encode($response);

}else {
    
    $reponse = print_r(json_encode(array('message' => 'you have no any order yet', 'status' => true)));
    
}
?>