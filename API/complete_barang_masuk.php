<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
      $itemCode = $_POST['itemCode'];

      $sql = $MySQLiconn->query("SELECT p.idPalet

            FROM mainitem mi

            left join barang b on mi.itemCode = b.itemCode
            left join palet p on b.idPalet = p.idPalet

            WHERE i.itemCode = '$itemCode'");
     
        if(mysqli_num_rows($sql) > 0){
            while($row = $sql->fetch_array()){
                $response["idPalet"] = $row['idPalet'];
                $response["responses"] = TRUE;

                $idPalet = $row['idPalet'];
                $sql_secondary = $MySQLiconn->query("UPDATE palet SET gpioStatus = 0 where idPalet = '$idPalet'");
            }
        echo json_encode($response);
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
