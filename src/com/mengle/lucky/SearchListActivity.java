package com.mengle.lucky;

import java.util.ArrayList;

import com.mengle.lucky.adapter.ZhuangListAdapter;
import com.mengle.lucky.adapter.ZhuangListAdapter.ZhuangModel;
import com.mengle.lucky.network.GameSearchRequest;
import com.mengle.lucky.network.GameSearchRequest.Pamras;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.wiget.GameListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SearchListActivity extends BaseActivity{
	
	
	
	public static void open(Context context,String keyword){
		Intent intent = new Intent(context, SearchListActivity.class);
		intent.putExtra("keyword", keyword);
		context.startActivity(intent);
	}
	
	private GameListView listView;
	
	private String keyword;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchlist_layout);
		listView = (GameListView) findViewById(R.id.listview);
		keyword = getIntent().getStringExtra("keyword");
		Preferences.User user = new Preferences.User(this);
		listView.setRequest(new GameSearchRequest(new Pamras(user.getUid(), user.getToken(), keyword)));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		listView.load(true);
	}



}
