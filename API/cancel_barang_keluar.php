<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $itemCode = $_POST['itemCode'];
        $qtyInput = $_POST['qtyInput'];
        $lastIdHistory = $_POST['lastIdHistory'];

      $sql = $MySQLiconn->query("SELECT i.itemCode, b.idBarang, p.idPalet

            FROM item i

            left join barang b on i.itemCode = b.itemCode
            left join palet p on b.idPalet = p.idPalet

            WHERE i.itemCode = '$itemCode'
            GROUP BY i.itemCode");
     
        if(mysqli_num_rows($sql) > 0){
            while($row = $sql->fetch_array()){
                $response["responses"] = TRUE;

                date_default_timezone_set("Asia/Jakarta");
                $now = date("Y-m-d H:i:s");
                $idPalet = $row['idPalet'];
                $idBarang = $row['idBarang'];

                $sql1 = $MySQLiconn->query("UPDATE item set status = 2, jumlahItem = jumlahItem + '$qtyInput' WHERE itemCode = '$itemCode'");
                $sql2 = $MySQLiconn->query("UPDATE historybarang set status = 1 WHERE idHistory = '$lastIdHistory'");
                $sql3 = $MySQLiconn->query("UPDATE palet SET gpioStatus = 1 where idPalet = '$idPalet'");
            }
        echo json_encode($response);
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
