<?php 
	require_once('koneksi.php');

	$result = array();

	$query = mysqli_query($koneksi, "SELECT * FROM event");

	while ($i = mysqli_fetch_assoc($query)) {
		$result[] = $i;
	}

	echo json_encode(array('result' => $result));
 ?>