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

public class MsgFragment extends Fragment{

	public static MsgFragment newInstance(){
		return new MsgFragment();
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
			add(new MsgListAdapter.Message("1", "一起玩呀","威廉萌", true));
			add(new MsgListAdapter.Message("1", "一起玩呀","威廉萌", false));
			add(new MsgListAdapter.Message("1", "一起玩呀","威廉萌", false));
			add(new MsgListAdapter.Message("1", "一起玩呀","威廉萌", false));
			add(new MsgListAdapter.Message("1", "一起玩呀","威廉萌", false));
		}}));
		
	}
	
}
