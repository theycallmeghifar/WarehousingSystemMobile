<?php
require_once 'mysqli_connect.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $mainItemCode = $_POST['mainItemCode'];
        $jumlahInput = $_POST['jumlahInput'];

      $query = "SELECT mi.mainItemCode, i.itemCode, p.idPalet, b.idBarang, i.itemName, i.jumlahItem, p.deskripsiPalet, r.deskripsiRak, l.deskripsiLemari, ('$jumlahInput' * i.netQuantity) as 'jumlahInput', p.maxBarang, i.status, p.ipAddress, p.gpio1, p.gpio2, p.gpio3, p.gpioStatus

            FROM mainitem mi

            left join item i on mi.itemCode = i.itemCode
            left join barang b on i.itemCode = b.itemCode
            left join palet p on b.idPalet = p.idPalet
            left join rak r on p.idRak = r.idRak
            left join lemari l on r.idLemari = l.idLemari

            WHERE mi.mainItemCode = '$mainItemCode' && i.itemCode IS NOT NULL";
     
        $result = mysqli_query($conn, $query);

        $array = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $array[] = $row;
        }

        echo ($result) ?
            json_encode(array("responses" => TRUE, "result" => $array)) :
            json_encode(array("responses" => FALSE));
        }

?>
