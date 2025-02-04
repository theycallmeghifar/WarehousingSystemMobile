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
     
        if(mysqli_num_rows($sql) > 0){
            while($row = $sql->fetch_array()){
                $response["idPalet"] = $row['idPalet'];
                $response["responses"] = TRUE;

                $idPalet = $row['idPalet'];
                $itemCode = $row['itemCode'];
                $sql_secondary = $MySQLiconn->query("UPDATE palet SET gpioStatus = 0 where idPalet = '$idPalet'");
                $sql_third = $MySQLiconn->query("UPDATE item SET status = 1 where itemCode = '$itemCode'");
            }
        echo json_encode($response);
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
