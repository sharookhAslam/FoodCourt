<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$user_id = $_POST['user_id'];

$sql1 = "SELECT * FROM payment_details WHERE user_id = '{$user_id}'";


$response = array();
$response['data'] = array();

$result = mysqli_query($con, $sql1);

while($row = mysqli_fetch_assoc($result)){
  
  $ds['phone'] = $row['phone'];
  $ds['name'] = $row['name'];
  $ds['payemnt_method'] = $row['payemnt_method'];



 
  
  
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