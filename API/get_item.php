<?php
require_once 'mysqli_connect.php';

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

        $itemCode = $_POST['itemCode'];

        if ($itemCode == '' or $itemCode == null) {
            $query = "SELECT i.itemCode
            
            FROM item i

            WHERE i.warehouse = 100 && i.itemCode = '$itemCode'";
        }else{
            $query = "SELECT i.itemCode, i.itemName, i.jumlahItem, i.netQuantity, p.deskripsiPalet, r.deskripsiRak, l.deskripsiLemari, (CASE WHEN mi.position is null then '0' else mi.position end) as position
            
            FROM item i
            LEFT JOIN mainitem mi on mi.itemCode = i.itemCode
            LEFT JOIN barang b on b.itemCode = i.itemCode
            LEFT JOIN palet p on p.idPalet = b.idPalet
            LEFT JOIN rak r on p.idRak = r.idRak
            LEFT JOIN lemari l on r.idLemari = l.idLemari

            WHERE i.warehouse = 100 && i.itemCode LIKE '%$itemCode%'
            GROUP BY i.itemCode";
        }
     
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
