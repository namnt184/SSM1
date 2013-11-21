package com.namnt.apps.ssm.dao;

import java.util.ArrayList;
import java.util.List;

import com.namnt.apps.ssm.model.StaffEntry;
import com.namnt.apps.ssm.provider.SSMDBContracts.StaffColums;
import com.namnt.apps.ssm.provider.SSMDBContracts.Staffs;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

public class StaffEntryDAO {
	private final static StaffEntryDAO instance = new StaffEntryDAO();
	private StaffEntryDAO(){
		
	}
	
	public static StaffEntryDAO getInstance(){
		return instance;
	}
	
	public void addStaff(ContentResolver contentResolver,StaffEntry entry){
		ContentValues values = getStaffValues(entry);
		contentResolver.insert(Staffs.CONTENT_URI, values);
	}
	private ContentValues getStaffValues(StaffEntry entry){
		ContentValues contentValues= new ContentValues();
		contentValues.put(StaffColums.AVATAR, entry.getStaffAvatar());
		contentValues.put(StaffColums.ID, entry.getStaffId());
		contentValues.put(StaffColums.IMEI, entry.getImei());
		contentValues.put(StaffColums.NAME, entry.getName());
		contentValues.put(StaffColums.SERIAL, entry.getSerial());
		contentValues.put(StaffColums.LOCALE, entry.getStaffLocale());
		return contentValues;
	}
	public List<StaffEntry> getAllStaff(ContentResolver contentResolver,String selection){
		List<StaffEntry> result  = new ArrayList<StaffEntry>();
		final Cursor cursor = contentResolver.query(Staffs.CONTENT_URI, null, selection, null, null);
		if(cursor!=null){
			while(cursor.moveToNext()){
				 String staffId =cursor.getString(cursor.getColumnIndexOrThrow(StaffColums.ID));
				 String name=cursor.getString(cursor.getColumnIndexOrThrow(StaffColums.NAME));
				 String staffAvatar=cursor.getString(cursor.getColumnIndexOrThrow(StaffColums.AVATAR));				
				 String imei=cursor.getString(cursor.getColumnIndexOrThrow(StaffColums.IMEI));
				 String serial=cursor.getString(cursor.getColumnIndexOrThrow(StaffColums.SERIAL));
				 String locale=cursor.getString(cursor.getColumnIndexOrThrow(StaffColums.LOCALE));
				 StaffEntry entry = new StaffEntry(name, imei, staffId,locale, staffAvatar);
				 entry.setSerial(serial);
				 result.add(entry);				 
			}
			cursor.close();
			return result;
			
		}
		return result;
	}
	public void deleteStaff(ContentResolver contentResolver ,String id){
		contentResolver.delete(Staffs.CONTENT_URI, StaffColums.IMEI+"="+"'"+id+"'", null);
	}
	public void updateStaff(ContentResolver contentResolver ,StaffEntry entry){
		ContentValues values = getStaffValues(entry);
//		contentResolver.delete(Staffs.CONTENT_URI, StaffColums.ID+"="+"'"+id+"'", null);
		contentResolver.update(Staffs.CONTENT_URI, values, StaffColums.IMEI+"="+"'"+entry.getImei()+"'", null);
	}
}
