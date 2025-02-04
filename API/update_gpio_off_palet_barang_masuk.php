<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $idBarang = $_POST['idBarang'];
        $idPalet = $_POST['idPalet'];
        $itemCode = $_POST['itemCode'];
        $qtyInput = $_POST['qtyInput'];
        $purchaseOrder = $_POST['purchaseOrder'];
        $deliveryNote = $_POST['deliveryNote'];

        $sql = $MySQLiconn->query("SELECT b.itemCode

            FROM barang b

            WHERE b.idBarang = '$idBarang' AND b.idPalet = '$idPalet' AND b.itemCode = '$itemCode'");

        if(mysqli_num_rows($sql) > 0){
            while($row = $sql->fetch_array()){
                date_default_timezone_set("Asia/Jakarta");
                $now = date("Y-m-d H:i:s");

                $sql1 = $MySQLiconn->query("UPDATE item set jumlahItem = jumlahItem + '$qtyInput' WHERE itemCode = '$itemCode'");
                $sql2 = $MySQLiconn->query("INSERT INTO historybarang (purchaseOrder, deliveryNote, idPalet, itemCode, jumlahMasuk, jumlahKeluar, tanggal, warehouse, status, createdDate) VALUES ('$purchaseOrder', '$deliveryNote', '$idPalet', '$itemCode', '$qtyInput', 0, '$now', 100, 0, '$now')");
            
                $response["responses"] = TRUE;
            }

        echo json_encode($response);

        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
