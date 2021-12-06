<?php
include 'inc/conn.php';
   
     $select="SELECT `id_account`, `email_address`, `phone_no`, `password`, `wallet_amount`, `withdrawn_amount`,`recharge_amount` FROM `accounts` WHERE `phone_no`='".$_POST['first']."' ";
    
    $fetch= mysqli_query($con,$select);
    
     if(!mysqli_num_rows($fetch)>0){
        // echo("not found");
        
        
        $insert="INSERT INTO `accounts`( `phone_no`, `password`, `wallet_amount`, `withdrawn_amount`) VALUES ('".$_POST['first']."','".$_POST['first']."','0','0')";
        
        $query=mysqli_query($con,$insert);
        if($query){
            $fetch=mysqli_query($con,$select);
            if($fetch){
                $data=mysqli_fetch_assoc($fetch);
                echo(json_encode($data));
            }
        }
        
        
    } else {
        $data=mysqli_fetch_assoc($fetch);
        // if($_POST['isemail']=="yes"){
        //     // send email
        //     $otp = rand(1000,9999);
        //     mail($data['email_address'],"Your OTP for current login is ".$otp,$msg);
        // }
        print_r(json_encode($data));
    }