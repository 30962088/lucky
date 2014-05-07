package com.mengle.lucky.fragments;

import java.util.ArrayList;

import com.mengle.lucky.adapter.Row2ListAdapter;
import com.mengle.lucky.adapter.Row2ListAdapter.Row2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class OnGoingFragment extends Fragment{
	
	public static OnGoingFragment newInstance(){
		return new OnGoingFragment();
	}
	
	private ListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		listView = new ListView(getActivity());
		
		return listView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		listView.setAdapter(new Row2ListAdapter(getActivity(), new ArrayList<Row2ListAdapter.Row2>(){{
			add(new Row2(new Row2ListAdapter.Row("结束时间", Color.parseColor("#e47033"), Color.parseColor("#f9e3d6")), 
					new Row2ListAdapter.Row("结束时间", Color.parseColor("#e47033"), Color.parseColor("#f9e3d6"))));
		}}));
		
	}

}
