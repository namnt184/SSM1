package com.namnt.apps.ssm.utils;

public class Constants {
	public static String BASE_URL = "http://192.168.100.4";
	public static String ALL_STAFF_URL = BASE_URL + "/SSM/staffs/index";
	public static String ADD_STAFF_URL = BASE_URL + "/SSM/staffs/add";
	public static String UPDATE_STAFF_URL = BASE_URL + "/SSM/staffs/edit";
	public static String DELETE_STAFF_URL = BASE_URL + "/SSM/staffs/delete";
	public static String FIND_STAFF_URL = BASE_URL + "/SSM/staffs/staff";
	public static String CONNECT_STAFF_URL = BASE_URL + "/SSM/staffs/connect";

	public static void setBaseURL(String url) {
		if(!url.contains("http")){
		BASE_URL = "http://"+url;
		}else{
			BASE_URL=url;
		}
		ALL_STAFF_URL = BASE_URL + "/SSM/staffs/index";
		ADD_STAFF_URL = BASE_URL + "/SSM/staffs/add";
		UPDATE_STAFF_URL = BASE_URL + "/SSM/staffs/edit";
		DELETE_STAFF_URL = BASE_URL + "/SSM/staffs/delete";
		FIND_STAFF_URL = BASE_URL + "/SSM/staffs/staff";
		CONNECT_STAFF_URL = BASE_URL + "/SSM/staffs/connect";
	}
}
