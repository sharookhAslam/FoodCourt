<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);



$sql1 = "SELECT * FROM users WHERE user_role = 'customer'";


$response = array();
$response['data'] = array();

$result = mysqli_query($con, $sql1);

while($row = mysqli_fetch_assoc($result)){
    
    $ds['id'] = $row['id'];
  $ds['name'] = $row['name'];
  $ds['email'] = $row['email'];
   $ds['status'] = $row['status'];
   
 $ds['otp'] = $row['otp'];
  
  
  array_push($response['data'], $ds);
  
 


 }
 
 if($response['data'] >0){
   
    $response['success'] = "1";
    
   


    
 echo json_encode($response);  
 

   
   
 
 }else {
  
     $response['success'] = "No items order yet";
     echo json_encode($response);
}

?>