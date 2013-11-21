package com.namnt.apps.ssm.provider;

import android.net.Uri;
import android.provider.BaseColumns;


public class SSMDBContracts {
	public interface StaffColums{
		public String NAME="name";
		public String IMEI="imei";
		public String ID="id";
		public String AVATAR="avatar";
		public String SERIAL="serial";
		public String LOCALE="locale";
	}
	public static class Staffs implements BaseColumns,StaffColums{
		public static final Uri CONTENT_URI=SSMDBProvider.BASE_CONTENT_URI.buildUpon().appendPath("staff").build();
		public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd.ssm.staff";
		public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd.ssm.staff";
		public static Uri buildPostUri(String is){
			return CONTENT_URI.buildUpon().appendPath(is).build();
		}
	}
}
