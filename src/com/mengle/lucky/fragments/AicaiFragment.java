package com.mengle.lucky.fragments;

import java.util.ArrayList;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.AicaiListAdapter;
import com.mengle.lucky.adapter.AicaiListAdapter.Aicai;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AicaiFragment extends Fragment{

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.aicai_layout, null);
	}
	
	private ListView listView;
	
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView) view.findViewById(R.id.listview);
		listView.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.aicai_header, null));
		listView.setAdapter(new AicaiListAdapter(getActivity(), new ArrayList<AicaiListAdapter.Aicai>(){{
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
			add(new Aicai(1, "Elin", "http://tp2.sinaimg.cn/3084436105/180/5682903051/1", 15));
		}}));
	}
	
}
