package com.mengle.lucky.fragments;

import java.util.ArrayList;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.ShitiListAdapter;
import com.mengle.lucky.adapter.ShitiListAdapter.Shiti;
import com.mengle.lucky.adapter.ShitiListAdapter.ShitiList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ShitiFragment extends Fragment{

	
	public static ShitiFragment newInstance(){
		return new ShitiFragment();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.shiti_layout, null);
	}
	
	private ListView listView;
	
	private ShitiList list;
	
	private ShitiListAdapter adapter;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView) view.findViewById(R.id.listview);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		request();
	}

	private void request() {
		list = new ShitiList(new ArrayList<ShitiListAdapter.Shiti>(){{
			add(new Shiti(1, "更快"));
			add(new Shiti(1, "更快"));
			add(new Shiti(1, "更快"));
			add(new Shiti(1, "更快"));
		}});
		adapter = new ShitiListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
	}
	
	
	
}
