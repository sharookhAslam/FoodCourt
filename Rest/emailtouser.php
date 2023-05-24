
<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$user_id = $_POST['user_id'];

$sql1 = "SELECT email FROM user WHERE id = '{$user_id}'";


$result = mysqli_query($con, $sql1);

if(mysqli_num_rows($result)>0){

$row = mysqli_fetch_assoc($result);

$user_email1 = $row['f_email'];

$sender_email = "From: p.bughio8191@gmail.com";
  
 mail($user_email1, "Pick your order", "Hi your order si ready".$rand, $sender_email);
 
   
    $response = json_encode(array('message' => 'We are waiting', 'status' => true));
}

