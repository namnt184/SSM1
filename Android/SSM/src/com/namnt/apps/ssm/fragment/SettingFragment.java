package com.namnt.apps.ssm.fragment;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.linhnv.apps.ssm.R;
import com.namnt.apps.ssm.EditStaff;
import com.namnt.apps.ssm.LoginActivity;
import com.namnt.apps.ssm.utils.AsyncTask;
import com.namnt.apps.ssm.utils.Constants;
import com.namnt.apps.ssm.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingFragment extends Fragment {
	private Button mButtonLogin;
	private Button mButtonManual;
	private EditText mPwdText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.setting_layout, container, false);
		mButtonLogin = (Button) view.findViewById(R.id.btnConnect);
		mPwdText = (EditText) view.findViewById(R.id.serverIp);

		mButtonLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected Boolean doInBackground(Void... params) {

						String url = Constants.CONNECT_STAFF_URL;
						try {
							JSONObject obj = new JSONObject(Utils.downloadUrl(url));
							if (obj != null) {
								int code = obj.getInt("code");
								if (code == 100) {
									
									return true;
								}
								return false;
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return false;

					}

					@Override
					protected void onPostExecute(Boolean result) {
						if(!isAdded()){
							return;
						}
						if (result) {
							SharedPreferences preferences =getActivity().getSharedPreferences("MYADMIN", Context.MODE_PRIVATE);
							Editor editor = preferences.edit();
							editor.putString("ip",Constants.BASE_URL);
							editor.commit();
							Toast.makeText(getActivity(), "connect to success!", Toast.LENGTH_LONG)
							.show();
						} else {
							Toast.makeText(getActivity(), "connect to failed!", Toast.LENGTH_LONG)
									.show();
						}
					}

				};
				if (mPwdText.getText() != null && mPwdText.getText().toString().trim().length() > 0) {
					Constants.BASE_URL=mPwdText.getText().toString();
					Constants.setBaseURL(Constants.BASE_URL);
					Log.i("Setting", "base URL :" +Constants.BASE_URL );
					task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR);
				} else {
					Toast.makeText(getActivity(), "Invalid ip address!", Toast.LENGTH_LONG).show();
				}
			}
		});
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
	}

	
}
