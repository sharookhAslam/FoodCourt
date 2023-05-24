<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$user_email = $_POST['f_email'];




$sql1 = "SELECT f_id, f_name, f_email, mall_id, f_pic FROM food_stalls WHERE f_email='{$user_email}'";


$response = array();
$response['data'] = array();

$result = mysqli_query($con, $sql1);


if(mysqli_num_rows($result)>0)

{

  $row = mysqli_fetch_assoc($result);
  
  $ds['f_id'] = $row['f_id'];
  $ds['f_email'] = $row['f_email'];
  $ds['f_name'] = $row['f_name'];
  $ds['f_pic'] = $row['f_pic'];
  $ds['mall_id'] = $row['mall_id'];
  
  array_push($response['data'], $ds);
  
 // echo print_r($row);

      
   
   $response['status'] = true;
   $response['message'] = 'fetched';
   echo json_encode($response); 
   
}
   //$response = print_r(json_encode(array('message' => 'Admin Login', 'status' => true)));
else {
  
    $response = json_encode(array('message' => 'not fetched', 'status' => false));

        
    }
  
    echo json_encode($response);


 

?>