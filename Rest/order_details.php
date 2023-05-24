<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);

//$title = $_POST['title'];
//$food_id = $_POST['stall_id'];
//$mall_id = $_POST['mall_id'];
//$price = $_POST['price'];
//$qty = $_POST['qty'];


$order_id= $_POST['order_id'];


//$item_id = $_POST['item_id'];

$user_id = $_POST['user_id'];

$status = 'new';

$sql1 = "INSERT INTO order_detail(user_id, mall_id, food_id, item_id, title, price, qty, order_id)
SELECT user_id, mall_id, food_id, item_id, title, price, qty, '{$order_id}' FROM temp_cart WHERE user_id = '{$user_id}'";


$sql2 = "INSERT INTO order_detail(status) VALUES ('{$status}')";


    $res = mysqli_query($con, $sql1);

    
    if($res ==true){
        

    $response = json_encode(array('message' => 'order confirmed', 'status' => true));
        
    }else {
        
        $response = "failed".mysqli_error($con);
    }
    
    echo $response;


 

?>