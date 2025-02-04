<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
    	$itemCode = $_POST['itemCode'];

      $sql = $MySQLiconn->query("SELECT itemCode, itemName
                  FROM item
                  WHERE itemCode = '$itemCode'");
    
      if(mysqli_num_rows($sql) > 0){
        while($row = $sql->fetch_array()){
             //$response["message"] = "Login Successfull";
             $response["itemCode"] = $row['itemCode'];
             $response["itemName"] = $row['itemName'];
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
