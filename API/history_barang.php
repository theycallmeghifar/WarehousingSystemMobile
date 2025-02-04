<?php
require_once 'mysqli_connect.php';

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

        $tanggalAwal = $_POST['tanggalAwal'];
        $tanggalAkhir = $_POST['tanggalAkhir'];

        if ($tanggalAwal == '' or $tanggalAwal == null AND $tanggalAkhir == '' or $tanggalAkhir == null) {
            $query = "SELECT h.purchaseOrder, h.deliveryNote, p.deskripsiPalet, r.deskripsiRak, l.deskripsiLemari, h.mainItemCode, mi.mainItemName, h.itemCode, i.itemName, h.jumlahMasuk, h.jumlahKeluar, h.tanggal

            FROM historybarang h
            LEFT JOIN mainitem mi on h.mainItemCode = mi.mainItemCode
            LEFT JOIN item i on h.itemCode = i.itemCode
            LEFT JOIN palet p on h.idPalet = p.idPalet
            LEFT JOIN rak r on p.idRak = r.idRak
            LEFT JOIN lemari l on r.idLemari = l.idLemari

            WHERE h.warehouse = 100 && h.tanggal >= ( CURDATE() - INTERVAL 7 DAY ) && h.status != 1
            GROUP BY h.idHistory
            ORDER BY h.tanggal DESC";
        }else{
            $query = "SELECT h.purchaseOrder, h.deliveryNote, p.deskripsiPalet, r.deskripsiRak, l.deskripsiLemari, h.mainItemCode, mi.mainItemName, h.itemCode, i.itemName, h.jumlahMasuk, h.jumlahKeluar, h.tanggal 

            FROM historybarang h 
            LEFT JOIN mainitem mi on h.mainItemCode = mi.mainItemCode 
            LEFT JOIN item i on h.itemCode = i.itemCode 
            LEFT JOIN palet p on h.idPalet = p.idPalet
            LEFT JOIN rak r on p.idRak = r.idRak 
            LEFT JOIN lemari l on r.idLemari = l.idLemari

            WHERE h.warehouse = 100 && (h.tanggal BETWEEN '$tanggalAwal 00:00:00' AND '$tanggalAkhir 23:59:59') && h.status != 1 
            GROUP BY h.idHistory ORDER BY h.tanggal DESC";
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
