<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);

$title = $_POST['title'];
$stall_id = $_POST['stall_id'];
$mall_id = $_POST['mall_id'];

$item_id = $_POST['item_id'];
$price = $_POST['price'];
$qty = $_POST['qty'];
$user_id = $_POST['user_id'];
$image = $_POST['image'];





$sql = "INSERT INTO temp_cart(item_id, user_id, mall_id, food_id, price, qty, item_pic. title) VALUES ('{$item_id}','{$user_id}','{$mall_id}','{$stall_id}', '{$price}', '{$qty}', '{$title}', '{$image}')";

    
    $sql1 = "INSERT INTO temp_cart(item_id, user_id, mall_id, food_id, title, price, qty, item_pic) VALUES ('{$item_id}', '{$user_id}', '{$mall_id}', '{$stall_id}', '{$title}', '{$price}', '{$qty}', '{$image}')";
    $res = mysqli_query($con, $sql1);
    
    if($res==true){
        
    
    
    $response = json_encode(array('message' => 'Item Added to cart', 'status' => true));
        
    }else {
         $response = json_encode(array('message' => 'Item not Added to cart', 'status' => false));
        
       
    }
    
    echo $response;


 

?>