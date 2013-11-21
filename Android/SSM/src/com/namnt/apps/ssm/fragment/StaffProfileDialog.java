package com.namnt.apps.ssm.fragment;

import com.linhnv.apps.ssm.R;
import com.namnt.apps.ssm.model.StaffEntry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class StaffProfileDialog extends DialogFragment {
	TextView mStaffName;
	TextView mStaffId;
	TextView mSerial;
	TextView mLocale;
	TextView mImei;
	StaffEntry entry;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View convertView = inflater.inflate(R.layout.staff_layout, null);
		mStaffName = (TextView) convertView.findViewById(R.id.staff_name);
		mStaffId = (TextView) convertView.findViewById(R.id.staff_id);
		mSerial = (TextView) convertView.findViewById(R.id.staff_serial);
		mLocale = (TextView) convertView.findViewById(R.id.staff_locale);
		mImei = (TextView) convertView.findViewById(R.id.staff_imei);
		builder.setView(convertView);
		builder.setTitle("Profile");
		return builder.create();
	}

	public void setData(StaffEntry entry) {
		this.entry = entry;
		if (entry != null) {
			if (mStaffId != null) {
				mStaffId.setText("ID : " + entry.getStaffId());
			}
			if (mStaffName != null) {
				mStaffName.setText(entry.getName());
			}
			if (mSerial != null) {
				mSerial.setText("SERIAL : " + entry.getSerial());
			}
			if (mLocale != null) {
				mLocale.setText(entry.getStaffLocale());
			}
			if (mImei != null) {
				mImei.setText("IMEI : " + entry.getImei());
			}
			
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (entry != null) {
			if (mStaffId != null) {
				mStaffId.setText("ID : " + entry.getStaffId());
			}
			if (mStaffName != null) {
				mStaffName.setText(entry.getName());
			}
			if (mSerial != null) {
				mSerial.setText("SERIAL : " + entry.getSerial());
			}
			if (mLocale != null) {
				mLocale.setText(entry.getStaffLocale());
			}
			if (mImei != null) {
				mImei.setText("IMEI : " + entry.getImei());
			}
		}
	}

}
