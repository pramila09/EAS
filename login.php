<?php

     include 'config.inc.php';
	 
	 // Check whether username or password is set from android	
     if(isset($_POST['emailid']) && isset($_POST['password']))
     {
		  // Innitialize Variable
		  $result='';
	   	  $emailid = $_POST['emailid'];
          $password = $_POST['password'];
		  
		  // Query database for row exist or not
          $sql = 'SELECT * FROM tblemployee WHERE  emailid = :emailid AND password = :password';
          $stmt = $conn->prepare($sql);
          $stmt->bindParam(':emailid', $emailid, PDO::PARAM_STR);
          $stmt->bindParam(':password', $password, PDO::PARAM_STR);
          $stmt->execute();
          if($stmt->rowCount())
          {
			 $result="true";	
          }  
          elseif(!$stmt->rowCount())
          {
			  	$result="false";
          }
		  
		  // send result back to android
   		  echo $result;
  	}
	
?>