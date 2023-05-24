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


if(mysqli_num_rows($result)>0)

{

  $row = mysqli_fetch_assoc($result);
  
  $ds['id'] = $row['id'];
  $ds['email'] = $row['email'];
  $ds['name'] = $row['name'];
  $ds['profile_pic'] = $row['profile_pic'];
  
  array_push($response['data'], $ds);
  
 // echo print_r($row);
  if($user_email==isset($row['email'])){
      
   
   $response['status'] = true;
   $response['message'] = 'fetched';
   echo json_encode($response); 
   //$response = print_r(json_encode(array('message' => 'Admin Login', 'status' => true)));
}
else {
  
    $response = json_encode(array('message' => 'not fetched', 'status' => false));

        
    }
}  
    echo json_encode($response);


 

?>