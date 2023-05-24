<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);

$user_name = $_POST['name'];
$user_email = $_POST['email'];
$user_pass = $_POST['password'];
$image = $_POST['image'];
$p_name= $_POST['p_name'];
$image2 = $p_name.'.jpg';
$upload_path = "fcimages/$p_name.jpg";

$sql1 = "SELECT mall_id, mall_name, mall_email, mall_password FROM shopping_malls WHERE mall_email='{$user_email}'";


$result = mysqli_query($con, $sql1);

if(mysqli_num_rows($result)>0)

{

  $row = mysqli_fetch_assoc($result);
  
 // echo print_r($row);
 
if($user_email==isset($row['email'])){
      
    $response = print_r(json_encode(array('message' => 'Email already exist', 'status' => false)));
     
      
      
  }

if(empty($user_name) || empty($user_email) || empty($user_pass)){
    
    $response = print_r(json_encode(array('message' => 'Please fill all the information', 'status' => false)));
}

if (empty($image2)){
    
     $response = print_r(json_encode(array('message' => 'Please upload the image', 'status' => false)));
}
}else {
    $sql = "INSERT INTO shopping_malls(mall_name, mall_email, mall_password, mall_pic) VALUES ('{$user_name}','{$user_email}','{$user_pass}', '{$image2}')";

    $res = mysqli_query($con, $sql);
    
    if($res==true){
        
    
    file_put_contents($upload_path, base64_decode($image));
    $response = json_encode(array('message' => 'Added Successfully', 'status' => true));
        
    }else {
        
        $response = "failed";
    }
    
    echo $response;
}

 

?>