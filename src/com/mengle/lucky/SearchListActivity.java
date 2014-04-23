package com.mengle.lucky;

import java.util.ArrayList;

import com.mengle.lucky.adapter.ZhuangListAdapter;
import com.mengle.lucky.adapter.ZhuangListAdapter.ZhuangModel;

import android.os.Bundle;
import android.widget.ListView;

public class SearchListActivity extends BaseActivity{
	
	private ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchlist_layout);
		listView = (ListView) findViewById(R.id.listview);
		init();
	}

	private void init() {
		listView.setAdapter(new ZhuangListAdapter(this, new ArrayList<ZhuangListAdapter.ZhuangModel>(){{
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1,"2222"));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
		}}));
		
	}

}
