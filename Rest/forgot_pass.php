<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);
$user_email = $_POST['user_email'];

$sql1 = "SELECT id, name, email, password, otp FROM users WHERE email='{$user_email}'";


$result = mysqli_query($con, $sql1);

if(mysqli_num_rows($result)>0)

{

  $row = mysqli_fetch_assoc($result);
  

      
       $to = $user_email;
       
$subject = "OTP";


$txt = "your otp is".$row['otp'];
$headers = "From: p.bughio8191@gmail.com";

mail($to,$subject,$txt);
$response = print_r(json_encode(array('message' => 'check email for otp', 'status' => true)));
    


}else{
$response = print_r(json_encode(array('message' => 'Email does not exist', 'status' => false)));
}