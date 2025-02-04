<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
      $mainItemCode = $_POST['mainItemCode'];

      $sql = $MySQLiconn->query("SELECT mi.itemCode, p.idPalet

            FROM mainitem mi

            left join barang b on mi.itemCode = b.itemCode
            left join palet p on b.idPalet = p.idPalet

            WHERE mi.mainItemCode = '$mainItemCode'");

      $sql2 = $MySQLiconn->query("SELECT MIN(i.jumlahItem) AS minQuantity FROM item i
            LEFT JOIN mainitem mi on mi.itemCode = i.itemCode
            WHERE mi.mainItemCode = '$mainItemCode'");
     
        if(mysqli_num_rows($sql) > 0){
            while($row = $sql->fetch_array()){
                $response["idPalet"] = $row['idPalet'];
                $response["responses"] = TRUE;

                $idPalet = $row['idPalet'];
                $itemCode = $row['itemCode'];
                $sql_first = $MySQLiconn->query("UPDATE palet SET gpioStatus = 1 where idPalet = '$idPalet'");
                $sql_second = $MySQLiconn->query("UPDATE item SET status = 2 where itemCode = '$itemCode'");
            }

        $row2 = $sql2->fetch_array();
        $response["minQuantity"] = $row2['minQuantity'];

        echo json_encode($response);
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
