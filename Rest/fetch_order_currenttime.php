<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);

$status = 'new';
$user_id = $_POST['food_id'];

$sql1 = "SELECT * FROM order_detail WHERE food_id ='{$user_id}' && status = '{$status}'";


$response = array();
$response['data'] = array();

$result = mysqli_query($con, $sql1);

while($row = mysqli_fetch_assoc($result)){
  
  $ds['item_id'] = $row['item_id'];
  $ds['title'] = $row['title'];
  $ds['price'] = $row['price'];
  $ds['user_id'] = $row['user_id'];
$ds['order_id'] = $row['order_id'];
  $ds['qty'] = $row['qty'];
  
   $ds['time'] = $row['time'];
  array_push($response['data'], $ds);
  
 


 }
 
 if($response['data'] >0){
   
    $response['success'] = "1";
    
   


    
 echo json_encode($response);  
 

   
   
 
 }else {
  
     $response['success'] = "No items order yet";
     echo json_encode($response);
}

?>