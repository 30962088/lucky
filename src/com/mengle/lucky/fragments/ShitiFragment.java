package com.mengle.lucky.fragments;

import java.util.ArrayList;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.ShitiListAdapter;
import com.mengle.lucky.adapter.ShitiListAdapter.Shiti;
import com.mengle.lucky.adapter.ShitiListAdapter.ShitiList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ShitiFragment extends Fragment implements OnItemClickListener{

	
	public static ShitiFragment newInstance(){
		return new ShitiFragment();
	}
	
	private TextView shitiNoView;
	
	private TextView coinView;
	
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
		listView.setOnItemClickListener(this);
		shitiNoView = (TextView) view.findViewById(R.id.shiti_no);
		Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SHOWG.TTF");
		shitiNoView.setTypeface(face);
		
		coinView = (TextView) view.findViewById(R.id.coin);
		coinView.setTypeface(face);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		list.setIndex(position);
		adapter.notifyDataSetChanged();
		
	}
	
	
	
}
