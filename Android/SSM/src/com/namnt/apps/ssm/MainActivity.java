package com.namnt.apps.ssm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.linhnv.apps.ssm.R;
import com.namnt.apps.ssm.dao.StaffEntryDAO;
import com.namnt.apps.ssm.fragment.AboutFragment;
import com.namnt.apps.ssm.fragment.AddStaffProfileDialog;
import com.namnt.apps.ssm.fragment.AdminFragment;
import com.namnt.apps.ssm.fragment.ErrorDialogFragment;
import com.namnt.apps.ssm.fragment.HomeFragment;
import com.namnt.apps.ssm.fragment.ListDevicesFragment;
import com.namnt.apps.ssm.fragment.LoginFragment;
import com.namnt.apps.ssm.fragment.SettingFragment;
import com.namnt.apps.ssm.fragment.StaffProfileDialog;
import com.namnt.apps.ssm.fragment.ErrorDialogFragment.TDialogListener;
import com.namnt.apps.ssm.fragment.LoginFragment.ILoginProvider;
import com.namnt.apps.ssm.menu.MenuListFragment;
import com.namnt.apps.ssm.model.StaffEntry;
import com.namnt.apps.ssm.provider.ISlidingMenuProvider;
import com.namnt.apps.ssm.provider.SSMDBContracts.StaffColums;
import com.namnt.apps.ssm.utils.AsyncTask;
import com.namnt.apps.ssm.utils.Constants;
import com.namnt.apps.ssm.utils.Utils;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
public class MainActivity extends SlidingFragmentActivity implements ISlidingMenuProvider,
		CreateNdefMessageCallback, OnNdefPushCompleteCallback, TDialogListener, ILoginProvider {
	NfcAdapter mNfcAdapter;
	ListFragment mFragment;
	ErrorDialogFragment mErrorDialogFragment;
	StaffProfileDialog mProfileDialog;
	AddStaffProfileDialog mAddStaffProfileDialog;
	private boolean isNew =false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isNew=true;
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = getSupportFragmentManager().beginTransaction();
			mFragment = new MenuListFragment();
			t.replace(R.id.menu_frame, mFragment);
			t.commit();
		} else {
			mFragment = (ListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.menu_frame);
		}
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSupportActionBar().setIcon(R.drawable.main_menu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			// mInfoText = (TextView) findViewById(R.id.textView);
			Toast.makeText(this, "NFC is not available on this device.", Toast.LENGTH_LONG).show();
		} else {
			// Register callback to set NDEF message
			mNfcAdapter.setNdefPushMessageCallback(this, this);
			// Register callback to listen for message-sent success
			mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
		}
		setContentView(R.layout.activity_main);

		// setting inneractive listener
		// bannerAd.setInneractiveListener(this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		isNew=true;
		setIntent(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == android.R.id.home) {

			try {
				getSlidingMenu().showMenu();

				// Toast.makeText(this, "home pressed",
				// Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				e.printStackTrace();

			}

			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSlidingMenuSelected(int id) {
		// TODO Auto-generated method stub
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();

		switch (id) {
		case 0:
			HomeFragment home = new HomeFragment();
			fragmentTransaction.replace(R.id.main_container, home);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();

			break;
		case 1:
			ListDevicesFragment devices = new ListDevicesFragment();
			fragmentTransaction.replace(R.id.main_container, devices);
			fragmentTransaction.addToBackStack(null);

			fragmentTransaction.commit();

			break;
		case 2:
			AdminFragment admin = new AdminFragment();
			fragmentTransaction.replace(R.id.main_container, admin);
			fragmentTransaction.addToBackStack(null);

			fragmentTransaction.commit();

			break;
		case 3:
			LoginFragment login = new LoginFragment();
			fragmentTransaction.replace(R.id.main_container, login);
			fragmentTransaction.addToBackStack(null);

			fragmentTransaction.commit();

			break;
		case 4:
			SettingFragment setting = new SettingFragment();
			fragmentTransaction.replace(R.id.main_container, setting);
			fragmentTransaction.addToBackStack(null);

			fragmentTransaction.commit();

			break;
		case 5:
			AboutFragment aboutFragment = new AboutFragment();
			fragmentTransaction.replace(R.id.main_container, aboutFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			break;
		default:
			break;
		}
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().showContent();
	}

	@Override
	public void onNdefPushComplete(NfcEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	void processIntent(Intent intent) {
		Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		// only one message sent during the beam
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		// record 0 contains the MIME type, record 1 is the AAR, if present
		// mInfoText.setText(new String(msg.getRecords()[0].getPayload()));
		checkData(new String(msg.getRecords()[0].getPayload()));
	}

	@Override
	public void onResume() {
		super.onResume();
		// Check to see that the Activity started due to an Android Beam
		if (isNew &&NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			isNew=false;
			processIntent(getIntent());
		}
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		if (fm.findFragmentById(R.id.main_container) == null) {
			HomeFragment home = new HomeFragment();
			fragmentTransaction.add(R.id.main_container, home).commit();
		}
	}

	private void checkData(final String imei) {
		AsyncTask<Void, Void, StaffEntry> task = new AsyncTask<Void, Void, StaffEntry>() {

			@Override
			protected StaffEntry doInBackground(Void... params) {
				String url=Constants.FIND_STAFF_URL+"?imei="+imei;
				try {
					JSONObject jsonArray = new JSONObject(Utils.downloadUrl(url));
					if(jsonArray!=null){
						List<StaffEntry> result  = new ArrayList<StaffEntry>();
						StaffEntry entry =null;
							JSONObject obj = jsonArray.getJSONObject("Staff");
							 String staffId =obj.getString("id");
							 String name=obj.getString("name");
							 String staffAvatar=obj.getString("avatar");				
							 String imei=obj.getString("imei");
							 String serial=obj.getString("serial");
							 String locale=obj.getString("location");
							  entry = new StaffEntry(name, imei, staffId,locale, staffAvatar);
							 entry.setSerial(serial);
							 result.add(entry);				 
							obj=null;
					
						return entry;
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
			protected void onPostExecute(StaffEntry result) {
				if (result == null) {
					if (mErrorDialogFragment != null) {
						mErrorDialogFragment.dismiss();
					}

					mErrorDialogFragment = new ErrorDialogFragment("Error", "Unregistered Device!");
					mErrorDialogFragment.setListener(MainActivity.this);
					mErrorDialogFragment.setData(imei);
					mErrorDialogFragment.show(getSupportFragmentManager(), "");
				} else {
					// found one
					if (mProfileDialog != null) {
						mProfileDialog.dismiss();
					}
					mProfileDialog = new StaffProfileDialog();
					mProfileDialog.setData(result);
					mProfileDialog.show(getSupportFragmentManager(), "mProfileDialog");
				}
			}

		};
		task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR);
	}

	@Override
	public void onDialogDismiss() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddNew(String imei) {
		// TODO Auto-generated method stub
		if (mAddStaffProfileDialog != null) {
			mAddStaffProfileDialog.dismiss();
		}
		StaffEntry entry = new StaffEntry();
		entry.setImei(imei);
		mAddStaffProfileDialog = new AddStaffProfileDialog();
		mAddStaffProfileDialog.setData(entry);
		mAddStaffProfileDialog.show(getSupportFragmentManager(), "mAddStaffProfileDialog");
	}

	@Override
	public void onLoginSuccess() {
		// TODO Auto-generated method stub
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		HomeFragment home = new HomeFragment();
		fragmentTransaction.replace(R.id.main_container, home);
		fragmentTransaction.commit();
	}

	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = (Fragment) fm.findFragmentById(R.id.main_container);
		if (fragment != null && fragment instanceof HomeFragment) {
			super.onBackPressed();

		} else {
			int count = fm.getBackStackEntryCount();
			for (int i = 0; i < count; i++) {
				super.onBackPressed();
			}

		}
	}

}
