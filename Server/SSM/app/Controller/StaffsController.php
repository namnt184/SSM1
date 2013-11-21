<?php
class StaffsController extends AppController {
	public $helpers = array (
			'Html',
			'Form' 
	);
	public function index() {
		$data = Array (
				"name" => "Sergio",
				"age" => 23 
		);
// 		$this->RequestHandler->respondAs('json');		
		$data = $this->Staff->find ( 'all' );
		return new CakeResponse(array('body' => json_encode($data)));
// 		$this->set ( 'posts', $data );
	}
	public function staff($id = NULL) {
		$imei = '1231312';
		$data = $this->Staff->findByimei ( $imei );
		$this->set ( 'posts', $data );
	}
	public function edit($id = null) {
		$error = array (
				"code" => 101,
				"message" => "error" 
		);
		$success = array (
				"code" => 100,
				"message" => "successfull" 
		);
		if (isset ( $_REQUEST ['staffid'] ) && isset ( $_REQUEST ['name'] ) && isset ( $_REQUEST ['avatar'] ) && isset ( $_REQUEST ['imei'] ) && isset ( $_REQUEST ['serial'] ) && isset ( $_REQUEST ['location'] )) {
			$staffid = $_REQUEST ['staffid'];
			
			$name = $_REQUEST ['name'];
			
			$avatar = $_REQUEST ['avatar'];
			
			$imei = $_REQUEST ['imei'];
			
			$location = $_REQUEST ['location'];
			$serial = $_REQUEST ['serial'];
			$staff = $this->Staff->findByimei ( $imei );
			if (! $staff) {
				// throw new NotFoundException ( __ ( 'Invalid staff' ) );
				return new CakeResponse(array('body' => json_encode($error)));
			}
			if ($this->Staff->updateAll ( array (
					"id" => "'{$staffid}'",
					"name" => "'{$name}'",
					"avatar" => "'{$avatar}'",
					"imei" => "'{$imei}'",
					"location" => "'{$location}'",
					"serial" => "'{$serial}'" 
			), "imei =  '{$imei}'" )) {
				$this->Session->setFlash ( __ ( 'Your post has been updated.' ) );
				return new CakeResponse(array('body' => json_encode($success)));
			}
			return new CakeResponse(array('body' => json_encode($error)));
			$this->Session->setFlash ( __ ( 'Unable to update your staff.' ) );
		} else {
			return new CakeResponse(array('body' => json_encode($error)));
			
			$this->Session->setFlash ( __ ( 'Unable to update your staff.' ) );
		}
		// }
	}
	public function delete($id = null) {
		$error = array (
				"code" => 101,
				"message" => "error" 
		);
		$success = Array (
				"code" => 100,
				"message" => "successfull" 
		);
		if (isset ( $_REQUEST ['imei'] )) {
			$imei = $_REQUEST ['imei'];
			if ($this->Staff->deleteAll ( array (
					"imei =" => $imei 
			), false )) {
				$this->Session->setFlash ( __ ( 'The staff with id: %s has been deleted.', h ( $imei ) ) );
				return new CakeResponse(array('body' => json_encode($success)));
// 				die();
			} else {
				$this->Session->setFlash ( __ ( 'Unable to delete your staff.' ) );
				return new CakeResponse(array('body' => json_encode($error)));
			}
		}
	}
	public function  connect(){
		$success = Array (
				"code" => 100,
				"message" => "successfull"
		);
		return new CakeResponse(array('body' => json_encode($success)));
	}
	public function add() {
		$error = array (
				"code" => 101,
				"message" => "error" 
		);
		$success = Array (
				"code" => 100,
				"message" => "successfull" 
		);
		if ($this->request->is ( 'post' )) {
			$this->Staff->create ();
			if ($this->Staff->save ( $this->request->data )) {
				$this->Session->setFlash ( __ ( 'Your post has been saved.' ) );
					return new CakeResponse(array('body' => json_encode($success)));
			}
			$this->Session->setFlash ( __ ( 'Unable to add your post.' ) );
				return new CakeResponse(array('body' => json_encode($error)));
			return;
		} else {
			if (isset ( $_REQUEST ['staffid'] ) && isset ( $_REQUEST ['name'] ) && isset ( $_REQUEST ['avatar'] ) && isset ( $_REQUEST ['imei'] ) && isset ( $_REQUEST ['serial'] ) && isset ( $_REQUEST ['location'] )) {
				$staffid = $_REQUEST ['staffid'];
				
				$name = $_REQUEST ['name'];
				
				$avatar = $_REQUEST ['avatar'];
				
				$imei = $_REQUEST ['imei'];				
				$location = $_REQUEST ['location'];
				$serial = $_REQUEST ['serial'];
				$data = Array (
						"staffid" => $staffid,
						"name" => $name,
						"avatar" => $avatar,
						"imei" => $imei,
						"location" => $location,
						"serial" => $serial 
				);
				$this->Staff->create ();
				if ($this->Staff->save ( $data )) {
					$this->Session->setFlash ( __ ( 'Your staff has been saved.' ) );
					return new CakeResponse(array('body' => json_encode($success)));
				}
				return new CakeResponse(array('body' => json_encode($error)));
				$this->Session->setFlash ( __ ( 'Unable to add staff post.' ) );
			}
		}
	}
}

?>