<?php
define("MYSQL_DB_HOST", "localhost");
define("MYSQL_DB_USER", "admin");
define("MYSQL_DB_PASSWORD", "angelus25");
define("MYSQL_DB_DATABASE", "dataPenguna");
$conn = mysqli_connect(MYSQL_DB_HOST, MYSQL_DB_USER, MYSQL_DB_PASSWORD, MYSQL_DB_DATABASE);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$jamStart = mysqli_real_escape_string($conn, $_POST['startHour']);
$jamBerakhir = mysqli_real_escape_string($conn, $_POST['endHour']);
$intervalWaktu = mysqli_real_escape_string($conn, $_POST['intervalWaktu']);

if(!empty($user)&&!empty($email)&&!empty($ponsel)&&!empty($password)){
	$sql = "INSERT INTO dataWaktuTimer(jamStart, jamBerakhir, intervalWaktu) VALUES ('{$jamStart}', '{$jamBerakhir}', '{$intervalWaktu}');";
	$query = mysqli_query($conn, $sql) or die("alarm sudah ada");
	die("OK");
}
else {
    die("Semua isian tidak boleh kosong!");
}
die("FAILED");
?>