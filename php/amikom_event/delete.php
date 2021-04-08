<?php 
	require_once('koneksi.php');

	$id_event = $_GET['id_event'];

	if (!$id_event) {
		echo json_encode(array('message' => 'required field is empty'));
	}else {
		$query = mysqli_query($koneksi, "DELETE FROM event WHERE id_event='$id_event'");

		if ($query) {
			echo json_encode(array('message' => 'event data successfully deleted.'));
		} else {
			echo json_encode(array('message' => 'event data failed to deleted.'));
		}
	}
 ?>