package com.namnt.apps.ssm.fragment;

import java.io.IOException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.linhnv.apps.ssm.R;
import com.namnt.apps.ssm.EditStaff;
import com.namnt.apps.ssm.dao.StaffEntryDAO;
import com.namnt.apps.ssm.model.StaffEntry;
import com.namnt.apps.ssm.utils.AsyncTask;
import com.namnt.apps.ssm.utils.Constants;
import com.namnt.apps.ssm.utils.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddStaffProfileDialog extends DialogFragment {
	EditText mStaffName;
	EditText mStaffId;
	StaffEntry entry;
	Button mBtnOk;
	Button mBtnCancel;
	EditText mSerial;
	EditText mLocale;
	EditText mPwdText;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View convertView = inflater.inflate(R.layout.add_item_layout, null);
		mStaffName = (EditText) convertView.findViewById(R.id.staff_name_in);
		mStaffId = (EditText) convertView.findViewById(R.id.staff_id_in);
		mSerial=(EditText)convertView.findViewById(R.id.staff_serial_in);
		mLocale=(EditText)convertView.findViewById(R.id.staff_locale_in);
		mPwdText=(EditText)convertView.findViewById(R.id.adminPwd);
		mBtnCancel=(Button)convertView.findViewById(R.id.btn_Cancel);
		mBtnOk=(Button)convertView.findViewById(R.id.btn_OK);
		mBtnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		mBtnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
//				dismiss();
				addNew();
				
			}
		});
		builder.setView(convertView);
		builder.setTitle("Profile");
		return builder.create();
	}
	private void addNew(){
		SharedPreferences preferences =getActivity().getSharedPreferences("MYADMIN", Context.MODE_PRIVATE);
		String pwd = preferences.getString("admin", "1234");
//		if(mPwdText.getText()!=null&&mPwdText.getText().toString().trim().length()>0){
//			if(!mPwdText.getText().toString().equals(pwd)){
//				Toast.makeText(getActivity(), "Wrong Admin password!", Toast.LENGTH_LONG).show();
//				return;
//			}
//		}else{
//			Toast.makeText(getActivity(), "Confirm Admin password!", Toast.LENGTH_LONG).show();
//			return;
//		}
		
		if(mStaffId.getText()!=null&&mStaffId.getText().toString().trim().length()>0){
			entry.setStaffId(mStaffId.getText().toString());
		}else{
			Toast.makeText(getActivity(), "Staff Id could not be null!", Toast.LENGTH_LONG).show();
			return;
		}
		if(mStaffName.getText()!=null&&mStaffName.getText().toString().trim().length()>0){
			entry.setName(mStaffName.getText().toString());
		}else{
			Toast.makeText(getActivity(), "Staff Name could not be null!", Toast.LENGTH_LONG).show();
			return;
		}
		if(mSerial.getText()!=null&&mSerial.getText().toString().trim().length()>0){
			entry.setSerial(mSerial.getText().toString());
		}else{
			Toast.makeText(getActivity(), "Serial Num could not be null!", Toast.LENGTH_LONG).show();
			return;
		}
		if(mLocale.getText()!=null&&mLocale.getText().toString().trim().length()>0){
			entry.setStaffLocale(mLocale.getText().toString());
		}else{
			Toast.makeText(getActivity(), "Locale  could not be null!", Toast.LENGTH_LONG).show();
			return;
		}
		AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				String url=Constants.ADD_STAFF_URL+"?staffid="+URLEncoder.encode(""+entry.getStaffId())+"&imei="+entry.getImei()
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
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				
				if(!isAdded()){
					return;
				}
				dismiss();
				if(result){
				Toast.makeText(getActivity(), "add staff succcessful!", Toast.LENGTH_LONG)
						.show();
				}else{
					Toast.makeText(getActivity(), "add staff failed!", Toast.LENGTH_LONG)
					.show();
				}
			}

		};
		task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR);
	}
	public void setData(StaffEntry entry) {
		this.entry = entry;
//		if (entry != null) {
//			if(mStaffId)
//			mStaffId.setText("ID : " + entry.getStaffId());
//			mStaffName.setText(entry.getName());
//		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (entry != null) {
			mStaffId.setText("");
			mStaffName.setText("");
		}
	}

}
