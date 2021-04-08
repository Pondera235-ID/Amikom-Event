<?php 
	require_once('koneksi.php');

	class user{}

	$nim = $_POST["nim"];
	$password = $_POST["password"];

	if ((empty($nim) || empty($password))) {
		$response = new user();
		$response -> success = 0;
		$response -> message = "Kolom tidak boleh kosong";
		die(json_encode($response));
	}

	$query = mysqli_query($koneksi, "SELECT * FROM user WHERE nim = '$nim' AND password='$password'");
	$row = mysqli_fetch_array($query);

	if (!empty($row)) {
		$response = new user();
		$response -> success = 1;
		$response -> message = "Selamat datang ".$row['nim'];
		$response -> id_user = $row['id_user'];
		$response -> nim = $row['nim'];
		die(json_encode($response));
	}else {
		$response = new user();
		$response -> success = 0;
		$response -> message = "nim atau password salah";
		die(json_encode($response));
	}

	mysqli_close($koneksi);
 ?>