<?php
require_once 'mysqli.php';

    $response = array();

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        
        $itemCode = $_POST['itemCode'];
        $itemCodeCompare = $_POST['itemCodeCompare'];

        $sql1 = $MySQLiconn->query("SELECT itemCode
                  FROM item
                  WHERE itemCode = '$itemCode'");
        
      if(mysqli_num_rows($sql1) > 0){
        while($row = $sql1->fetch_array()){
             $response["itemCode"] = $row['itemCode'];
             $response["responses"] = TRUE;
        }
            echo json_encode($response);
            
        }else{
            $response["responses"] = FALSE;
            echo json_encode($response);
        }
    }

?>
