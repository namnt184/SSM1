package com.namnt.apps.ssm.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ErrorDialogFragment extends DialogFragment {

	private String mDialogTitle;
	private String mMessage;
	private TDialogListener mOnDialogListener;
	private String data;
	public ErrorDialogFragment() {
		super();
	}

	public ErrorDialogFragment(String title, String message) {
		super();
		mDialogTitle = title;
		mMessage = message;
	}

	public void setListener(TDialogListener listener) {
		mOnDialogListener = listener;
	}
	public void setData(String imei){
		data=imei;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(mDialogTitle);
		builder.setCancelable(false);
		
		builder.setMessage(mMessage);
		builder.setPositiveButton(
				"OK",
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
						if (mOnDialogListener != null) {
							mOnDialogListener.onDialogDismiss();
						}
					}
				});
		SharedPreferences preferences =getActivity().getSharedPreferences("MYADMIN", Context.MODE_PRIVATE);
		boolean isSuper = preferences.getBoolean("SUPER",false);
		if(isSuper){
		builder.setNegativeButton(
				"Add New",
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
						if (mOnDialogListener != null) {
							mOnDialogListener.onAddNew(data);
						}
					}
				});
		}
		return builder.create();
	}

	/**
	 * @return the mDialogTitle
	 */
	public String getmDialogTitle() {
		return mDialogTitle;
	}

	/**
	 * @param mDialogTitle
	 *            the mDialogTitle to set
	 */
	public void setmDialogTitle(String mDialogTitle) {
		this.mDialogTitle = mDialogTitle;
	}

	/**
	 * @return the mMessage
	 */
	public String getmMessage() {
		return mMessage;
	}

	/**
	 * @param mMessage
	 *            the mMessage to set
	 */
	public void setmMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public interface TDialogListener {
		void onDialogDismiss();
		void onAddNew(String imei);
	}
}
