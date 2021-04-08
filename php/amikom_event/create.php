<?php 
	require_once('koneksi.php');

	$id_event = $_POST['id_event'];
	$judul_event = $_POST['judul_event'];
	$deskripsi_event = $_POST['deskripsi_event'];
	$lokasi_event = $_POST['lokasi_event'];
	$tanggal_event = $_POST['tanggal_event'];
	$waktu_event = $_POST['waktu_event'];
	$pembuat_event = $_POST['pembuat_event'];

	if (!$judul_event || !$deskripsi_event || !$lokasi_event || !$tanggal_event || !$waktu_event || !$pembuat_event) {
		echo json_encode(array('message' => 'required field is empty.'));
	} else {
		$query = mysqli_query($koneksi, "INSERT INTO event VALUES ('$id_event','$judul_event','$deskripsi_event','$lokasi_event','$tanggal_event','$waktu_event','$pembuat_event')");

		if ($query) {
			echo json_encode(array('message' => 'event data successfully added.' ));
		}else {
			echo json_encode(array('message' => 'event data failed to added.'));
		}
	}

 ?>	