<?php

header('Content-Type: application/JSON');

header('Access-Control-Allow-Origin: *');


header('Access-Control-Allow-Method: POST');

header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Authorization');


include('config.php');
 
$data = json_decode(file_get_contents("php://input"), true);

$title = $_POST['title'];
$stall_id = $_POST['stall_id'];
$mall_id = $_POST['mall_id'];
$price = $_POST['price'];
$qty = $_POST['qty'];
$description = $_POST['description'];
$category = $_POST['category'];
$additional = $_POST['additional'];
$image = $_POST['image'];
$p_name= $_POST['p_name'];
$image2 = uniqid().'.jpg';
$upload_path = "fcimages/items/$image2";




$sql = "INSERT INTO items(stall_id, title, price, qty, category, additional, description, picture, mall_id) VALUES ('{$stall_id}','{$title}','{$price}','{$qty}', '{$category}', '{$additional}', '{$description}', '{$image2}', '{$mall_id}')";

    $res = mysqli_query($con, $sql);
    
    if($res==true){
        
    
    file_put_contents($upload_path, base64_decode($image));
    $response = json_encode(array('message' => 'Item Added Successfully', 'status' => true));
        
    }else {
        
        $response = "failed";
    }
    
    echo $response;


 

?>