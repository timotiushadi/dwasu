<?php

define("MYSQL_DB_HOST", "localhost");
define("MYSQL_DB_USER", "admin");
define("MYSQL_DB_PASSWORD", "angelus25");
define("MYSQL_DB_DATABASE", "dataPengguna");
$conn = mysqli_connect(MYSQL_DB_HOST, MYSQL_DB_USER, MYSQL_DB_PASSWORD, MYSQL_DB_DATABASE);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$pos = mysqli_real_escape_string($conn, $_GET['pos']);
$startHour = mysqli_real_escape_string($conn, $_GET['jamStart']);
$endHour = mysqli_real_escape_string($conn, $_GET['jamBerakhir']);
$intervalWaktu = mysqli_real_escape_string($conn, $_GET['intervalWaktu']);

$sql = "SELECT * FROM dataWaktuTimer;";

//creating an array for storing the data
$heroes = array();

//creating an statment with the query
$stmt = $conn->prepare($sql);

//executing that statment
$stmt->execute();

//binding results for that statment
$stmt->bind_result($pos,$startHour, $endHour, $intervalWaktu);

//looping through all the records
while($stmt->fetch()){

 //pushing fetched data in an array
 $temp = [
 'pos'=>$pos,
 'startHour'=>$startHour,
 'endHour'=>$endHour,
 'intervalWaktu'=>$intervalWaktu,
 ];

 //pushing the array inside the hero array
 array_push($heroes, $temp);
}

//displaying the data in json format
echo json_encode($heroes);
