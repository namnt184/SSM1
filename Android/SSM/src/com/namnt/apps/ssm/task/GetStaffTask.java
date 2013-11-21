package com.namnt.apps.ssm.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;

import com.namnt.apps.ssm.model.StaffEntry;
import com.namnt.apps.ssm.provider.SSMDBContracts.StaffColums;
import com.namnt.apps.ssm.utils.AsyncTask;

public class GetStaffTask extends AsyncTask<Void, Void, List<StaffEntry>>{
	final static String DEBUG_TAG =GetStaffTask.class.getCanonicalName();
	@Override
	protected List<StaffEntry> doInBackground(Void... params) {
		String url="http://192.168.100.4/SSM/staffs/index";
		try {
			JSONArray jsonArray = new JSONArray(downloadUrl(url));
			if(jsonArray!=null){
				List<StaffEntry> result  = new ArrayList<StaffEntry>();

				for(int i=0;i<jsonArray.length();i++){
					JSONObject obj = jsonArray.getJSONObject(i);
					 String staffId =obj.getString("id");
					 String name=obj.getString("name");
					 String staffAvatar=obj.getString("avatar");				
					 String imei=obj.getString("imei");
					 String serial=obj.getString("serial");
					 String locale=obj.getString("location");
					 StaffEntry entry = new StaffEntry(name, imei, staffId,locale, staffAvatar);
					 entry.setSerial(serial);
					 result.add(entry);				 
					obj=null;
				}
				return result;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(JSONException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	private String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	    // Only display the first 500 characters of the retrieved
	    // web page content.
	    int len = 500;
	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d(DEBUG_TAG, "The response is: " + response);
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = readIt(is, len);
	        Log.i(DEBUG_TAG, contentAsString);
	        return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}
}
