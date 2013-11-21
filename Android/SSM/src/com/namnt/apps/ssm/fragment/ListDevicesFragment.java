package com.namnt.apps.ssm.fragment;

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

import com.linhnv.apps.ssm.R;
import com.namnt.apps.ssm.EditStaff;
import com.namnt.apps.ssm.dao.StaffEntryDAO;
import com.namnt.apps.ssm.model.StaffEntry;
import com.namnt.apps.ssm.task.GetStaffTask;
import com.namnt.apps.ssm.utils.AsyncTask;
import com.namnt.apps.ssm.utils.Constants;
import com.namnt.apps.ssm.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListDevicesFragment extends Fragment{
	private static final String DEBUG_TAG=ListDevicesFragment.class.getCanonicalName();
	ListView mListView;
	StaffAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View convertView =inflater.inflate(R.layout.list_staff, container, false);
		mListView=(ListView)convertView.findViewById(R.id.listStaff);		
		return convertView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	private void initData(){
		AsyncTask<Void, Void, List<StaffEntry>> task  = new AsyncTask<Void, Void, List<StaffEntry>>(){

			@Override
			protected List<StaffEntry> doInBackground(Void... params) {
				String url=Constants.ALL_STAFF_URL;
				try {
					JSONArray jsonArray = new JSONArray(Utils.downloadUrl(url));
					if(jsonArray!=null){
						List<StaffEntry> result  = new ArrayList<StaffEntry>();

						for(int i=0;i<jsonArray.length();i++){
							JSONObject obj = jsonArray.getJSONObject(i).getJSONObject("Staff");
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

			@Override
			protected void onPostExecute(List<StaffEntry> result) {
				if(!isAdded()){
					return;
				}
				Log.i(DEBUG_TAG, "mListView :" +mListView);
				if(result!=null){
				mAdapter = new StaffAdapter(getActivity(), 0, result);
				mListView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
				}
			}
			
		};
		task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR);
		
//		GetStaffTask task = new GetStaffTask();
//		task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR);
	}
	private class StaffAdapter extends ArrayAdapter<StaffEntry>{

		public StaffAdapter(Context context, int resource, List<StaffEntry> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final Holder holder;
			final StaffEntry entry = getItem(position);
			if(convertView==null){
				convertView=getActivity().getLayoutInflater().inflate(R.layout.staff_item_layout, null);
				holder= new Holder();
				holder.avatar=(ImageView)convertView.findViewById(R.id.avatar);
				holder.id=(TextView)convertView.findViewById(R.id.staff_id);
				holder.name=(TextView)convertView.findViewById(R.id.staff_name);
				convertView.setTag(holder);
			}else{
				holder=(Holder)convertView.getTag();
			}
			holder.id.setText("ID :" +entry.getStaffId());
			holder.name.setText(entry.getName());
			convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SharedPreferences preferences =getActivity().getSharedPreferences("MYADMIN", Context.MODE_PRIVATE);
					boolean isSuper = preferences.getBoolean("SUPER",false);
					if(isSuper){
						Intent intent = new Intent(getActivity(), EditStaff.class);
						intent.putExtra("EXTRA", entry);
						getActivity().startActivity(intent);
					}else{
						return;
					}
				}
			});
			return convertView;
		}
		
		class Holder {
			ImageView avatar;
			TextView name;
			TextView id;
		}
	}
}
