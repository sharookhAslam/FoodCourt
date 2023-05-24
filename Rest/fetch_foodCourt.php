<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$mall_id = $_POST['mall_id'];

$sql1 = "SELECT f_id, f_name, f_pic FROM food_stalls WHERE mall_id ='{$mall_id}'";


$response = array();
$response['data'] = array();

$result = mysqli_query($con, $sql1);

if(mysqli_num_rows($result) >0){
while($row = mysqli_fetch_assoc($result)){
  
  $ds['f_name'] = $row['f_name'];
  $ds['f_id'] = $row['f_id'];
  $ds['f_pic'] = $row['f_pic'];
  array_push($response['data'], $ds);
  
 }
 
 $response['success'] = "1";
 echo json_encode($response);
 
}else {
    
    $reponse = print_r(json_encode(array('message' => 'No any food court added yet', 'status' => true)));
    
}
?>