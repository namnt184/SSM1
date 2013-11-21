package com.namnt.apps.ssm;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.linhnv.apps.ssm.R;
import com.namnt.apps.ssm.dao.StaffEntryDAO;
import com.namnt.apps.ssm.model.StaffEntry;
import com.namnt.apps.ssm.utils.AsyncTask;
import com.namnt.apps.ssm.utils.Constants;
import com.namnt.apps.ssm.utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditStaff extends Activity {
	private Button mBtnDelete;
	private Button mBtnSave;
	private Button mBtnCancel;
	EditText mStaffName;
	EditText mStaffId;
	StaffEntry entry;
	EditText mSerial;
	EditText mLocale;
	EditText mPwdText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		if (b == null) {
			finish();
		}
		entry = (StaffEntry) b.getSerializable("EXTRA");
		if (entry == null) {
			finish();
		}
		setContentView(R.layout.edit_staff_layout);
		mBtnDelete = (Button) findViewById(R.id.btnDelete);
		mBtnSave = (Button) findViewById(R.id.btnSave);
		mBtnCancel = (Button) findViewById(R.id.btnCancel);
		mStaffName = (EditText) findViewById(R.id.staff_name_edit);
		mStaffId = (EditText) findViewById(R.id.staff_id_edit);

		mSerial = (EditText) findViewById(R.id.staff_serial_edit);
		mLocale = (EditText) findViewById(R.id.staff_locale_edit);

		mBtnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mBtnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				save();
			}
		});
		mBtnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				delete();
			}
		});
		
		mSerial.setText(entry.getSerial());
		mLocale.setText(entry.getStaffLocale());
		mStaffName.setText(entry.getName());
		mStaffId.setText(entry.getStaffId());
	}

	private void save() {
		if (mStaffId.getText() != null && mStaffId.getText().toString().trim().length() > 0) {
			entry.setStaffId(mStaffId.getText().toString());
		} else {
			Toast.makeText(this, "Staff Id could not be null!", Toast.LENGTH_LONG).show();
			return;
		}
		if (mStaffName.getText() != null && mStaffName.getText().toString().trim().length() > 0) {
			entry.setName(mStaffName.getText().toString());
		} else {
			Toast.makeText(this, "Staff Name could not be null!", Toast.LENGTH_LONG).show();
			return;
		}
		if (mSerial.getText() != null && mSerial.getText().toString().trim().length() > 0) {
			entry.setSerial(mSerial.getText().toString());
		} else {
			Toast.makeText(this, "Serial Num could not be null!", Toast.LENGTH_LONG).show();
			return;
		}
		if (mLocale.getText() != null && mLocale.getText().toString().trim().length() > 0) {
			entry.setStaffLocale(mLocale.getText().toString());
		} else {
			Toast.makeText(this, "Locale  could not be null!", Toast.LENGTH_LONG).show();
			return;
		}
		AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				String url=Constants.UPDATE_STAFF_URL+"?staffid="+URLEncoder.encode(""+entry.getStaffId())+"&imei="+entry.getImei()
						+"&name="+URLEncoder.encode(""+entry.getName())+"&location="+URLEncoder.encode(""+entry.getStaffLocale())+"&avatar="+URLEncoder.encode(""+entry.getStaffAvatar())
						+"&serial="+entry.getSerial();
				try {
					JSONObject obj = new JSONObject(Utils.downloadUrl(url));
					if(obj!=null){
						int code = obj.getInt("code");
						if(code==100){
							return true;
						}
						return  false;
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

			@Override
			protected void onPostExecute(Boolean result) {
				if(result){
				Toast.makeText(EditStaff.this, "update staff succcessful!", Toast.LENGTH_LONG)
						.show();
				}else{
					Toast.makeText(EditStaff.this, "update staff failed!", Toast.LENGTH_LONG)
					.show();
				}
				finish();
			}

		};
		task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR);
	}

	private void delete() {
		AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				String url=Constants.DELETE_STAFF_URL+"?imei="+entry.getImei();
				try {
					JSONObject obj = new JSONObject(Utils.downloadUrl(url));
					if(obj!=null){
						int code = obj.getInt("code");
						if(code==100){
							return true;
						}
						return  false;
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

			@Override
			protected void onPostExecute(Boolean result) {
				if(result){
				Toast.makeText(EditStaff.this, "delete staff succcessful!", Toast.LENGTH_LONG)
						.show();
				}else{
					Toast.makeText(EditStaff.this, "delete staff failed!", Toast.LENGTH_LONG)
					.show();
				}
				finish();
			}

		};
		task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR);
	}
}
