package com.namnt.apps.ssm.fragment;

import com.linhnv.apps.ssm.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminFragment extends Fragment {
	EditText mCurrent;
	EditText mNewPwd;
	EditText mConfirmPwd;
	String current;
	String newPwd;
	String confirmPwd;
	Button mBtnChange;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View convertView = inflater.inflate(R.layout.admin, container, false);
		
		mCurrent=(EditText)convertView.findViewById(R.id.currentPwd);
		mNewPwd=(EditText)convertView.findViewById(R.id.newPwd);
		mConfirmPwd=(EditText)convertView.findViewById(R.id.confirmPwd);
		mBtnChange=(Button)convertView.findViewById(R.id.btnChange);
		mBtnChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changePwd();
			}
		});
		return convertView;
	}
	
	private void changePwd(){
		current = mCurrent.getText().toString();
		SharedPreferences preferences =getActivity().getSharedPreferences("MYADMIN", Context.MODE_PRIVATE);
		String pwd = preferences.getString("admin", "1234");
		if(current==null||current.trim().length()<=0){
			Toast.makeText(getActivity(), "Enter current Admin password", Toast.LENGTH_LONG).show();
			mCurrent.setText("");
			mNewPwd.setText("");
			mConfirmPwd.setText("");
			return;
		}
		if(!current.equals(pwd)){
			Toast.makeText(getActivity(), "Wrong Admin password!", Toast.LENGTH_LONG).show();
			mCurrent.setText("");
			mNewPwd.setText("");
			mConfirmPwd.setText("");
			return;
		}
		newPwd=mNewPwd.getText().toString();
		if(newPwd==null||newPwd.trim().length()<=0){
			Toast.makeText(getActivity(), "New Admin password could not be null!", Toast.LENGTH_LONG).show();
			mCurrent.setText("");
			mNewPwd.setText("");
			mConfirmPwd.setText("");
			return;
		}
		confirmPwd=mConfirmPwd.getText().toString();
		if(!newPwd.equals(confirmPwd)){
			Toast.makeText(getActivity(), "Confirm Admin password does not match!", Toast.LENGTH_LONG).show();
			mCurrent.setText("");
			mNewPwd.setText("");
			mConfirmPwd.setText("");
			return;
		}
		
		Editor editor = preferences.edit();
		editor.putString("admin", newPwd);
		editor.commit();
		Toast.makeText(getActivity(), "Change Admin password successfully!", Toast.LENGTH_LONG).show();
	}
}
