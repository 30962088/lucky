package com.mengle.lucky;



import java.util.ArrayList;

import com.mengle.lucky.adapter.Row2ListAdapter;
import com.mengle.lucky.adapter.Row2ListAdapter.Row2;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.UserMe;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.WigetUtils;
import com.mengle.lucky.utils.WigetUtils.OnItemClickListener;
import com.mengle.lucky.wiget.UserHeadView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ZoneActivity extends FragmentActivity implements OnItemClickListener{

	private ListView listView;
	
	private UserHeadView userHeadView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zone_layout);
		
		View headView = View.inflate(this, R.layout.zone_header, null);
		userHeadView = (UserHeadView) headView.findViewById(R.id.userhead);
		listView =  (ListView) findViewById(R.id.listview);
		
		listView.addHeaderView(headView);
		
		listView.setAdapter(new Row2ListAdapter(this, new ArrayList<Row2ListAdapter.Row2>(){{
			add(new Row2(new Row2ListAdapter.Row("结束时间", Color.parseColor("#e47033"), Color.parseColor("#f9e3d6")), 
					new Row2ListAdapter.Row("结束时间", Color.parseColor("#e47033"), Color.parseColor("#f9e3d6"))));
		}}));
		
		WigetUtils.onChildViewClick((ViewGroup) headView.findViewById(R.id.tab) ,this);
		
	}
	
	private void login(){
		Preferences.User user = new Preferences.User(this);
		final UserMe userMe = new UserMe(new UserMe.Params(user.getUid(), user.getToken()));
		RequestAsync.request(userMe, new Async() {
			
			public void onPostExecute(Request request) {
				if(userMe.getStatus() == Request.Status.SUCCESS){
					userHeadView.setData(userMe.getUser().toUserHeadData());
				}
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		login();
		
	}
	
	private View lastView;
	
	
	
	private void onItemClick(int position){
		
	}

	public void onItemClick(ViewGroup group, View view, int position, long id) {
		if(lastView != null){
			lastView.setSelected(false);
		}
		
		onItemClick(position);
		
		view.setSelected(true);
		
		lastView = view;
		
	}
	
	
	
	
	
	
}
