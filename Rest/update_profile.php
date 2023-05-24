<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);


$name = $_POST['name'];
$password = $_POST['pass'];
$cpass = $_POST['cpass'];
$image = $_POST['image'];
$user_email = $_POST['user_email'];

$image2 = uniqid().'.jpg';
$upload_path = "fcimages/$image2";





 $sql1 = "SELECT password FROM users WHERE email = '{$user_email}'";  
   
    
    $res2 = mysqli_query($con, $sql1);
    
 
        
$row = mysqli_fetch_assoc($res2);

      
        if($password != isset($row['password'])){
         $response = json_encode(array('message' => 'Password did not match with old password', 'status' => false));
  } else {
       
      $sql = "UPDATE users SET name = '{$name}', password = '{$cpass}', profile_pic = '{$image2}' WHERE email = '{$user_email}'";   
            $res = mysqli_query($con, $sql);
    file_put_contents($upload_path, base64_decode($image));
    $response = json_encode(array('message' => 'Updated Successfully', 'status' => true));
  } 
  
    
    echo $response;


 

?>