package com.namnt.apps.ssm.menu;

import java.util.ArrayList;
import java.util.List;

import com.linhnv.apps.ssm.R;
import com.namnt.apps.ssm.provider.ISlidingMenuProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MenuListFragment extends ListFragment {		
	private ISlidingMenuProvider mISlidingMenuProvider;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<SlidingMenu> list = new ArrayList<SlidingMenu>();
		SlidingMenu menu00= new SlidingMenu(0, R.drawable.ic_launcher, "Home");
		list.add(menu00);
		SlidingMenu menu01= new SlidingMenu(1, R.drawable.ic_launcher, "Devices");
		list.add(menu01);
		SlidingMenu menu02= new SlidingMenu(2, R.drawable.ic_launcher, "Admin");
		list.add(menu02);
		SlidingMenu menu03= new SlidingMenu(3, R.drawable.ic_launcher, "Login");
		list.add(menu03);
		SlidingMenu menu04= new SlidingMenu(4, R.drawable.ic_launcher, "Setting");
		list.add(menu04);
		SlidingMenu menu07= new SlidingMenu(8,  R.drawable.ic_launcher, "About App");
		list.add(menu07);
		MenuAdapter adapter = new MenuAdapter(getActivity(),list);
		setListAdapter(adapter);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mISlidingMenuProvider=(ISlidingMenuProvider)activity;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if(mISlidingMenuProvider!=null){
			mISlidingMenuProvider.onSlidingMenuSelected(position);
		}
	}

	class MenuAdapter extends ArrayAdapter<SlidingMenu>{
		LayoutInflater layoutInflater;
		public MenuAdapter(Context context,List<SlidingMenu> list) {
			super(context,0,list);
			layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
			final Holder holder;
			final SlidingMenu menu = getItem(position);
			if(convertView==null){
				holder = new Holder();
				convertView=layoutInflater.inflate(R.layout.list_item_icon_text, null);
				holder.icon=(ImageView)convertView.findViewById(R.id.icon);
				holder.name=(TextView)convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			}else{
				holder=(Holder)convertView.getTag();
			}
			holder.icon.setImageResource(menu.getIcon());
			holder.name.setText(menu.getName());
			return convertView;
		}
		
		class Holder{
			ImageView icon;
			TextView name;
		}
	}

}
