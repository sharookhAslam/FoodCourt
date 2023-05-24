<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);

$payment = $_POST['payment'];
$name = $_POST['name'];
$phone = $_POST['phone'];
$user_id = $_POST['user_id'];

$t_amount = $_POST['t_amount'];
$order_id = $_POST['order_id'];

$sql2 = "INSERT INTO payment_details(name, phone, payemnt_method, user_id, t_amount, order_id) VALUES ('{$name}', '{$phone}', '{$payment}', '{$user_id}', '{$t_amount}', '{$order_id}')";

  
    $res2 = mysqli_query($con, $sql2);
    
    if($res2==true){
        

    $response = json_encode(array('message' => 'details recorded', 'status' => true));
        
    }else {
        
        $response = "failed";
    }
    
    echo $response;


 

?>