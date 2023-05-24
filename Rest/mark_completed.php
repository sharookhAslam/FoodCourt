<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);

$order_id = $_POST['order_id'];

 $completed = "COMPLETED"; 
  

         
          $sql = "UPDATE order_detail SET status = '{$completed}' WHERE order_id = '{$order_id}'";   
          
          
            $res = mysqli_query($con, $sql);
            
            
  if($res == true){
    $response = print_r(json_encode(array('message' => 'order_completed'.mysqli_error($con), 'status' => true)));
     }   
     
     else {
           $response = print_r(json_encode(array('message' => 'Sory marked again '.mysqli_error($con), 'status' => false)));  
         
     }

  
  
    



 

?>