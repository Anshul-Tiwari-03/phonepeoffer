<?php
include 'inc/conn.php';
    if(isset($_POST)){
        
        $images_count=$_POST['marrayuri'];
        $arr=array();
        for($i=0;$i<$images_count;$i++){
            
            $base = $_POST["image".$i.""];
            $binary=base64_decode($base);
            $image_name="coupons_images/".date('Ymd')."image".($i+1).date('His').".jpg";
            
            // header('Content-Type: bitmap; charset=utf-8');
            $file = fopen($image_name, 'w');
            fwrite($file, $binary);
            fclose($file);
            $arr[]=$image_name;
            
        }
        $atttachment=$arr[0];
        $img_string=implode("##",$arr);
        $insert="INSERT INTO `coupons_list`( `title`, `images_strinng`, `expiry_date`, `code`, `code_attachment`, `offer_status`,  `offer_posted_by`,`amount_without_tax`) VALUES ('".$_POST['title']."','".$img_string."','".$_POST['end_date']."','".$_POST['code']."','".$atttachment."','".$_POST['offer_status']."','".$_POST['offer_posted_by']."','".$_POST['amount']."')";
        
        $query=mysqli_query($con,$insert);
        if($query)
        echo("quer_submitted");
        
    }
?>