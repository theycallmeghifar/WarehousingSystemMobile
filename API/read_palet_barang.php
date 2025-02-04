<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $idPalet = $_POST['idPalet'];
        $idPaletCompare = $_POST['idPaletCompare'];

        $sql1 = $MySQLiconn->query("SELECT idPalet
                  FROM palet
                  WHERE idPalet = '$idPalet'");
        
      if(mysqli_num_rows($sql1) > 0){
        while($row = $sql1->fetch_array()){
             $response["idPalet"] = $row['idPalet'];
             $response["responses"] = TRUE;

             if ($idPalet == $idPaletCompare) {
                 $sql2 = $MySQLiconn->query("UPDATE palet SET gpioStatus = 0 where idPalet = '$idPalet'");
             }else{

             } 
        }
            echo json_encode($response);
            
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
