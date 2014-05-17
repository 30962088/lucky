package com.mengle.lucky;

import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.adapter.UserListAdapter;

import com.mengle.lucky.wiget.UserItemView;
import com.mengle.lucky.wiget.UserItemView.Model;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class UserListActivity extends Activity {
	
	private ListView listView;
	
	private List<Model> list;
	
	private UserListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list_layout);
		listView = (ListView) findViewById(R.id.listview);
	}
	

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		list = new ArrayList<UserItemView.Model>();
		for(int i = 0;i<20;i++){
			if(i%2 ==0){
				list.add(new Model(15, "威廉萌", "", "地主", 20, 10, 10, 10, true));
			}else{
				list.add(new Model(15, "威廉萌", "", "地主", 20, 10, 10, 10, false));
			}
			
		}
		adapter = new UserListAdapter(this, list);

		listView.setAdapter(adapter);
		
	}





}
