<?php

$servername = "localhost";
$emailid = "root";
$password = "root";
$dbname = "employee";

try {
    	$conn = new PDO("mysql:host=$servername;dbname=$dbname", $emailid, $password);
    	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }
catch(PDOException $e)
    {
    	die("OOPs something went wrong");
    }

?>