<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
      $itemCode = $_POST['itemCode'];
      $jumlahSebelum = $_POST['jumlahSebelum'];
      $jumlahSesudah = $_POST['jumlahSesudah'];
      $createdBy = $_POST['username'];

      $sql = $MySQLiconn->query("SELECT itemCode FROM item
            WHERE itemCode = '$itemCode'");
     
        if(mysqli_num_rows($sql) > 0){
            while($row = $sql->fetch_array()){
                $response["responses"] = TRUE;
                date_default_timezone_set("Asia/Jakarta");
                $now = date("Y-m-d H:i:s");

                $sql_secondary = $MySQLiconn->query("UPDATE item SET jumlahItem = '$jumlahSesudah' where itemCode = '$itemCode'");
                $sql_third = $MySQLiconn->query("INSERT INTO itemhistory (itemCode, jumlahSebelum, jumlahSesudah, createdBy, createdDate) VALUES ('$itemCode', '$jumlahSebelum', '$jumlahSesudah', 'admin mobile', '$now')");
            }
        echo json_encode($response);
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
