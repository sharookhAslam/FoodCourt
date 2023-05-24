
<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$user_id = $_POST['user_id'];

$sql1 = "SELECT email FROM users WHERE user_id = '{$user_id}'";


$result = mysqli_query($con, $sql1);

if(mysqli_num_rows($result)>0){

$row = mysqli_fetch_assoc($result);

$user_email1 = $row['email'];


  
 mail($user_email1, "Your order", "Hi your order is ready");
 
   
    $response = json_encode(array('message' => 'we Notify you', 'status' => true));
}

