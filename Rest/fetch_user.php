<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$user_email = $_POST['email'];

$sql1 = "SELECT id, name, email, password, user_role, profile_pic FROM users WHERE email='{$user_email}'";

$response = array();
$response['data'] = array();

$result = mysqli_query($con, $sql1);

while($row = mysqli_fetch_assoc($result)){
  
  $ds['id'] = $row['id'];
  $ds['name'] = $row['name'];
 $ds['password'] = $row['password'];

  $ds['profile_pic'] = $row['profile_pic'];

  
  
  array_push($response['data'], $ds);
  
 }
 
 $response['success'] = "1";
 echo json_encode($response);


?>