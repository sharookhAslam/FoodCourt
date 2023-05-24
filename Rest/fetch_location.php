<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);



$id = $_POST['id'];
$longitude = $_POST['longi'];
$latitude= $_POST['lati'];


$sql1 = "SELECT id, name, email, password, longitude, latitude FROM users WHERE id='{$id}'";


$result = mysqli_query($con, $sql1);

if(mysqli_num_rows($result)>0)

{

  $row = mysqli_fetch_assoc($result);
  
 // echo print_r($row);
  if(($longitude==isset($row['longitude'])) && ($latitude==isset($row['latitude']))){
      
    $response = print_r(json_encode(array('message' => 'hey you are at this mall', 'status' => true)));
}else {
    
    $response = print_r(json_encode(array('message' => 'You can no torder', 'status' => false)));
}

}