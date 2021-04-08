<?php 
	include_once "koneksi.php";
	class emp{}
	$img_name = $_POST["nama_gambar"];
	$img_url = $_POST["url_gambar"];

	if (empty($img_name)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Please dont empty Name."; 
		die(json_encode($response));
	} else {
		$random = random_word(20);
		
		$path = "image/".$random.".png";
		
		// sesuiakan ip address laptop/pc atau URL server
		$actualpath = "http://192.168.43.111/android/UploadImages/$path";
		
		$query = mysqli_query($koneksi, "INSERT INTO event (nama_gambar,url_gambar) VALUES ('$img_name','$actualpath')");
		
		if ($query){
			file_put_contents($path,base64_decode($img_url));
			
			$response = new emp();
			$response->success = 1;
			$response->message = "Successfully Uploaded";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error Upload image";
			die(json_encode($response)); 
		}
	}	
	
	// fungsi random string pada gambar untuk menghindari nama file yang sama
	function random_word($id = 20){
		$pool = '1234567890abcdefghijkmnpqrstuvwxyz';
		
		$word = '';
		for ($i = 0; $i < $id; $i++){
			$word .= substr($pool, mt_rand(0, strlen($pool) -1), 1);
		}
		return $word; 
	}

	mysqli_close($koneksi);
 ?>