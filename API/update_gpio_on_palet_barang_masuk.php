<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $itemCode = $_POST['itemCode'];

      $sql = $MySQLiconn->query("SELECT b.idBarang, b.itemCode, i.itemName, i.jumlahItem, p.maxBarang, b.tanggalMasuk, b.idPalet, p.deskripsiPalet, r.idRak, r.deskripsiRak, l.idLemari, l.deskripsiLemari, p.ipAddress, p.gpio1, p.gpio2, p.gpio3, p.gpioStatus

            FROM barang b

            left join palet p on b.idPalet = p.idPalet
            left join rak r on p.idRak = r.idRak
            left join lemari l on r.idLemari = l.idLemari

            left join item i on b.itemCode = i.itemCode

            where b.itemCode= '$itemCode'");
     
      if(mysqli_num_rows($sql) > 0){
        while($row = $sql->fetch_array()){
             //$response["message"] = "Login Successfull";
             $response["idBarang"] = $row['idBarang'];
             $response["itemCode"] = $row['itemCode'];
             $response["itemName"] = $row['itemName'];
             $response["jumlahItem"] = $row['jumlahItem'];
             $response["maxBarang"] = $row['maxBarang'];
             $response["tanggalMasuk"] = $row['tanggalMasuk'];
             $response["idPalet"] = $row['idPalet'];
             $response["deskripsiPalet"] = $row['deskripsiPalet'];
             $response["idRak"] = $row['idRak'];
             $response["deskripsiRak"] = $row['deskripsiRak'];
             $response["idLemari"] = $row['idLemari'];
             $response["deskripsiLemari"] = $row['deskripsiLemari'];
             $response["ipAddress"] = $row['ipAddress'];
             $response["gpio1"] = $row['gpio1'];
             $response["gpio2"] = $row['gpio2'];
             $response["gpio3"] = $row['gpio3'];
             $response["gpioStatus"] = 1;
             $response["responses"] = TRUE;

            $idPalet = $row['idPalet'];
            $sql_secondary = $MySQLiconn->query("UPDATE palet SET gpioStatus = 1 where idPalet = '$idPalet'");
        }

        echo json_encode($response);
        }else{
            $response["responses"] = FALSE;
            //$response["message"] = "Incorrect Email or Password!";

            echo json_encode($response);
        }
    }

?>
