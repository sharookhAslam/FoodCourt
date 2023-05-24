<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$user_id = $_POST['user_id'];


$sql1 = "DELETE FROM temp_cart WHERE user_id = '{$user_id}'";


$res = mysqli_query($con, $sql1);

if($res == true){
    
     $response = print_r(json_encode(array('message' => 'Item deleted', 'status' => true)));
}else {
    
    $response= "failed";
}



?>