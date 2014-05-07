package com.mengle.lucky.fragments;

import java.util.ArrayList;

import com.mengle.lucky.adapter.MsgListAdapter;
import com.mengle.lucky.adapter.MsgListAdapter.Msg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NofityFragment extends Fragment{

	public static NofityFragment newInstance(){
		return new NofityFragment();
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
		
		listView.setAdapter(new MsgListAdapter(getActivity(), new ArrayList<MsgListAdapter.Msg>(){{
			add(new MsgListAdapter.Nofity("1", "3月1日18:00时系统维护", true));
			add(new MsgListAdapter.Nofity("1", "3月1日18:00时系统维护", true));
			add(new MsgListAdapter.Nofity("1", "3月1日18:00时系统维护", false));
			add(new MsgListAdapter.Nofity("1", "3月1日18:00时系统维护", false));
			add(new MsgListAdapter.Nofity("1", "3月1日18:00时系统维护", false));
		}}));
		
	}
	
}
