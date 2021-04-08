<?php 
	require_once('koneksi.php');

	class admin{}

	$username = $_POST["username"];
	$password = $_POST["password"];

	if ((empty($username) || empty($password))) {
		$response = new admin();
		$response -> success = 0;
		$response -> message = "Kolom tidak boleh kosong";
		die(json_encode($response));
	}

	$query = mysqli_query($koneksi, "SELECT * FROM admin WHERE username = '$username' AND password='$password'");
	$row = mysqli_fetch_array($query);

	if (!empty($row)) {
		$response = new admin();
		$response -> success = 1;
		$response -> message = "Selamat datang ".$row['name_admin'];
		$response -> id = $row['id_admin'];
		$response -> username = $row['username'];
		die(json_encode($response));
	}else {
		$response = new admin();
		$response -> success = 0;
		$response -> message = "username atau password salah";
		die(json_encode($response));
	}

	mysqli_close($koneksi);
 ?>