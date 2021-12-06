<?php
function make_a_history($couponid,$personid,$offerpostedby,$price){
    
    $date=date('d-m-Y');
    $make_order="INSERT INTO `orders`( `coupon_id`, `coupon_owner_id`, `coupon_buyer_id`, `price`, `offer_buyed_date`) VALUES ('".$couponid."','".$offerpostedby."','".$personid."','".$price."',NOW())";
    
    global $con;
    
    $insert_order=mysqli_query($con,$make_order);
    
    if($insert_order){
        echo("success");
    }
    else{
        echo("failed");
    }
    
    
}