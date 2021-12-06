<?php

include 'inc/conn.php';
include 'transact_history.php';
if(isset($_POST['coupon_id'])){
    $select="SELECT * FROM `coupons_list` WHERE `id`='".$_POST['coupon_id']."' AND `offer_status`='new_offer' ";
    $query=mysqli_query($con,$select);
    
    
    if(mysqli_num_rows($query)>0){
        
        $coupon_details=mysqli_fetch_assoc($query);
        
        $fetch_amount=$_POST['bank_amount'];
        if($fetch_amount>$_POST['pay_amt']){
            $update="UPDATE `coupons_list` SET `offer_status`='delievered',`offer_taken_by`='".$_POST['person_id']."' WHERE `id`='".$_POST['coupon_id']."'";
            $update_query=mysqli_query($con,$update);
            
            if($update_query){
             
                make_a_history($_POST['coupon_id'],$_POST['person_id'],$coupon_details['offer_posted_by'],$coupon_details['amount_without_tax']);   
            }
            else{
                echo("failed");
            }
        }
        
    }
    else{
        echo("failed");
    }
}