<?php
    
    if (is_uploaded_file($_FILES['myfile']['tmp_name'])) {
        $uploads_dir = './';
        $tmp_name = $_FILES['myfile']['tmp_name'];
        $pic_name = $_FILES['myfile']['name'];
        move_uploaded_file($tmp_name, $uploads_dir.$pic_name);
    }
    else{
        echo "File Not Uploaded";
    }
    
    ?>
