<?php
include 'inc/conn.php';

// if(isset($_POST['objective'])&&$_POST['objective']=="fetch_list"){

    $select="SELECT * from `coupons_list` where   `offer_status`='new_offer' AND `expiry_date` <=curdate() ORDER BY RAND() LIMIT 100 ";
    $fetch=mysqli_query($con,$select);
    if(mysqli_num_rows($fetch)>0){
        $data_list=array();
        while($arr=mysqli_fetch_assoc($fetch)){
            
            $data_list[]=$arr;
            
        }
        print_r(json_encode($data_list));
    }
    else{
        echo("fetched");
    }
// }
?>