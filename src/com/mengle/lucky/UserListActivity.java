package com.mengle.lucky;

import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.adapter.UserListAdapter;

import com.mengle.lucky.network.IUserListRequest;
import com.mengle.lucky.network.IUserListRequest.Result;
import com.mengle.lucky.network.UserFollowersRequest;
import com.mengle.lucky.network.UserFollowingRequest;
import com.mengle.lucky.network.UserMeFollowersRequest;
import com.mengle.lucky.network.UserMeFollowingsRequest;

import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.wiget.BaseListView;
import com.mengle.lucky.wiget.UserItemView;
import com.mengle.lucky.wiget.BaseListView.OnLoadListener;
import com.mengle.lucky.wiget.UserItemView.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UserListActivity extends Activity implements OnLoadListener,OnClickListener{
	
	public enum Type {
		FOLLOWS,FOLLERS
	}
	
	public static void open(Context context,int fuid,String title,Type type){
		Intent intent = new Intent(context, UserListActivity.class);
		intent.putExtra("type", type);
		intent.putExtra("fuid",fuid);
		intent.putExtra("title",title);
		context.startActivity(intent);
	}
	
	private int fuid;
	
	private Type type;
	
	private String title;
	
	private BaseListView listView;
	
	private List<Model> list = new ArrayList<UserItemView.Model>();
	
	private UserListAdapter adapter;
	
	private TextView titleView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fuid = getIntent().getIntExtra("fuid", 0);
		type = (Type) getIntent().getSerializableExtra("type");
		setContentView(R.layout.user_list_layout);
		findViewById(R.id.btn_back).setOnClickListener(this);
		titleView = (TextView) findViewById(R.id.title);
		listView = (BaseListView) findViewById(R.id.listview);
		listView.setOnLoadListener(this);
		adapter = new UserListAdapter(this, list);
		listView.setAdapter(adapter);
		title = getIntent().getStringExtra("title");
		setTitle();
	}
	
	
	
	private void setTitle(){
		titleView.setText(title);
	}
	
	
	

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(offset == 0){
			
			listView.load(true);
				
		}
		
	}

	private int offset = 0;
	
	private List<Result> results;
	
	@Override
	public boolean onLoad(int offset, int limit) {
		this.offset = offset;
		Preferences.User user = new Preferences.User(this);
		IUserListRequest request = null;
		if(user.getUid() == fuid){
			if(type == Type.FOLLERS){
				request = new UserMeFollowersRequest(new UserMeFollowersRequest.Params(fuid, user.getToken(), offset, limit));
			}else{
				request = new UserMeFollowingsRequest(new UserMeFollowingsRequest.Params(fuid, user.getToken(), offset, limit));
			}
		}else{
			if(type == Type.FOLLERS){
				request = new UserFollowersRequest(new UserFollowersRequest.Params(user.getUid(), user.getToken(),fuid, offset, limit));
			}else{
				request = new UserFollowingRequest(new UserFollowingRequest.Params(user.getUid(), user.getToken(),fuid, offset, limit));
			}
		}
		boolean rev = false;
		if(request != null){
			results = null;
			request.request();
			results = request.getResults();
			rev = results.size()>=limit?true:false;
		}
		return rev;
	}

	@Override
	public void onLoadSuccess() {
		if(results != null){
			if(offset == 0){
				list.clear();
			}
			list.addAll(Result.toModels(results));
			adapter.notifyDataSetChanged();
		}
		
		
	}





	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;

		default:
			break;
		}
		
	}





}
