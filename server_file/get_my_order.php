<?php
include 'inc/conn.php';
if(isset($_POST['owner_id'])){
    
    
    $select="SELECT * FROM `orders` WHERE `coupon_owner_id`='".$_POST['owner_id']."' OR `coupon_buyer_id`='".$_POST['owner_id']."' ORDER BY `order_id` DESC";
    $query=mysqli_query($con,$select);
    
    $data=array();
    
    while($orders=mysqli_fetch_assoc($query)){
        $data[]=$orders;
    }
    print_r(json_encode($data));
}