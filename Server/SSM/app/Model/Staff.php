<?php
class Staff extends AppModel {
	public $validate = array (
			'staffid' => array (
					'rule' => 'notEmpty' 
			),
			'name' => array (
					'rule' => 'notEmpty' 
			),
			'location' => array (
					'rule' => 'notEmpty' 
			),
			'avatar' => array (
					'rule' => 'notEmpty' 
			),
			'imei' => array (
					'rule' => 'notEmpty' 
			),
			'serial' => array (
					'rule' => 'notEmpty' 
			) 
	);
}

?>