package com.namnt.apps.ssm.fragment;

import com.linhnv.apps.ssm.R;

import android.app.Activity;
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

public class LoginFragment extends Fragment {
	private Button mButtonLogin;
	private Button mButtonManual;
	private EditText mPwdText;
	private ILoginProvider mLoginProvider;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.login_layout, container, false);
		mButtonLogin = (Button) view.findViewById(R.id.btnLogin);
		mPwdText = (EditText) view.findViewById(R.id.pwd);

		mButtonLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = getActivity().getSharedPreferences("MYADMIN",
						Context.MODE_PRIVATE);
				Editor editor = preferences.edit();
				String pwd = preferences.getString("admin", "1234");
				if (mPwdText.getText() != null && mPwdText.getText().toString().trim().length() > 0) {
					if (!mPwdText.getText().toString().equals(pwd)) {
						Toast.makeText(getActivity(), "Wrong Admin password!", Toast.LENGTH_LONG)
								.show();
						return;
					} else {
						editor.putBoolean("SUPER", true);
						editor.commit();
						mLoginProvider.onLoginSuccess();
					}
				} else {
					Toast.makeText(getActivity(), "Confirm Admin password!", Toast.LENGTH_LONG)
							.show();
					return;
				}
			}
		});
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			mLoginProvider=(ILoginProvider)activity;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public interface ILoginProvider{
		void onLoginSuccess();
	}
}
