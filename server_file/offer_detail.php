<?php
include 'inc/conn.php';
if(isset($_POST['offer_id'])){
    $select="SELECT * FROM `coupons_list` WHERE `id`='".$_POST['offer_id']."' ";
    $query=mysqli_query($con,$select);
    print_r(json_encode(mysqli_fetch_assoc($query)));
}