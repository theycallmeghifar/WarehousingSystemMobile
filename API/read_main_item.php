<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
      $mainItemCode = $_POST['mainItemCode'];

      $sql = $MySQLiconn->query("SELECT mainItemCode, mainItemName
                  FROM mainitem
                  WHERE mainItemCode = '$mainItemCode'");
    
      if(mysqli_num_rows($sql) > 0){
        while($row = $sql->fetch_array()){
             //$response["message"] = "Login Successfull";
             $response["mainItemCode"] = $row['mainItemCode'];
             $response["mainItemName"] = $row['mainItemName'];
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
