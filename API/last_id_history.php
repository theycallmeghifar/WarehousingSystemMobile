<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

        $mainItemCode = $_POST['mainItemCode'];
        $itemCode = $_POST['itemCode'];        
    	$jumlah = $_POST['jumlah'];

      $sql = $MySQLiconn->query("SELECT MAX(idHistory) AS lastIdHistory FROM historybarang WHERE mainItemCode = '$mainItemCode' AND itemCode = '$itemCode' AND jumlahKeluar = '$jumlah'");
    
      if(mysqli_num_rows($sql) > 0){
        while($row = $sql->fetch_array()){
             //$response["message"] = "Login Successfull";
             $response["lastIdHistory"] = $row['lastIdHistory'];
             $response["responses"] = TRUE;
        }

            echo json_encode($response);
            
        }else{
            $response["responses"] = FALSE;
            //$response["message"] = "Incorrect Email or Password!";

            echo json_encode($response);
        }
    }

?>
