package com.namnt.apps.ssm;

import com.linhnv.apps.ssm.R;
import com.namnt.apps.ssm.utils.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button mButtonLogin;
	private Button mButtonManual;
	private EditText mPwdText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mPwdText = (EditText) findViewById(R.id.pwd);
		mButtonLogin = (Button) findViewById(R.id.btnLogin);
		mButtonManual = (Button) findViewById(R.id.btnManual);

		mButtonLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences preferences =getSharedPreferences("MYADMIN", Context.MODE_PRIVATE);
				Editor editor = preferences.edit();
				String pwd = preferences.getString("admin", "1234");
				String ip = preferences.getString("ip", "localhost");
				Constants.setBaseURL(ip);
				if(mPwdText.getText()!=null&&mPwdText.getText().toString().trim().length()>0){
					if(!mPwdText.getText().toString().equals(pwd)){
						Toast.makeText(LoginActivity.this, "Wrong Admin password!", Toast.LENGTH_LONG).show();
						return;
					}else{
						editor.putBoolean("SUPER",true);
						editor.commit();
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				}else{
					Toast.makeText(LoginActivity.this, "Confirm Admin password!", Toast.LENGTH_LONG).show();
					return;
				}
			}
		});
		mButtonManual.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences preferences =getSharedPreferences("MYADMIN", Context.MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putBoolean("SUPER",false);
				editor.commit();
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
