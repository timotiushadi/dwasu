<?php
define("MYSQL_DB_HOST", "localhost");
define("MYSQL_DB_USER", "admin");
define("MYSQL_DB_PASSWORD", "angelus25");
define("MYSQL_DB_DATABASE", "dataPengguna");
$conn = mysqli_connect(MYSQL_DB_HOST, MYSQL_DB_USER, MYSQL_DB_PASSWORD, MYSQL_DB_DATABASE);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$startHour = mysqli_real_escape_string($conn, $_POST['startHour']);
$endHour = mysqli_real_escape_string($conn, $_POST['endHour']);
$intervalWaktu = mysqli_real_escape_string($conn, $_POST['intervalWaktu']);

if(!empty($startHour)&&!empty($endHour)&&!empty($intervalWaktu)){
	$sql = "INSERT INTO dataWaktuTimer(jamStart, jamBerakhir, intervalWaktu) VALUES ('{$startHour}', '{$endHour}', '{$intervalWaktu}');";
	$query = mysqli_query($conn, $sql) or die("Alarm sudah ada");
	die("OK");
}
else {
    die("Semua isian tidak boleh kosong!");
}
die("FAILED");
?>