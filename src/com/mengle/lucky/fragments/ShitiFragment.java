package com.mengle.lucky.fragments;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.ShitiListAdapter;
import com.mengle.lucky.adapter.ShitiListAdapter.ShitiList;
import com.mengle.lucky.network.GameLibraryGets.Result;
import com.mengle.lucky.network.GameLibraryGets;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.Preferences;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShitiFragment extends Fragment implements OnItemClickListener,OnClickListener{

	
	public static ShitiFragment newInstance(){
		return new ShitiFragment();
	}
	
	private TextView shitiNoView;
	
	private TextView coinView;
	
	private TextView titleView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.shiti_layout, null);
	}
	
	private ListView listView;
	
	private ShitiList list;
	
	private ShitiListAdapter adapter;
	
	private List<Result> results;
	
	private HashMap<Integer, ShitiList> hashMap = new HashMap<Integer, ShitiListAdapter.ShitiList>();
	
	private View btnPrev;
	
	private View btnNext;
	
	private View headerView;
	
	private ImageView headerImg;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		btnPrev = view.findViewById(R.id.btn_prev);
		btnPrev.setOnClickListener(this);
		btnNext = view.findViewById(R.id.btn_next);
		btnNext.setOnClickListener(this);
		listView = (ListView) view.findViewById(R.id.listview);
		View hview = LayoutInflater.from(getActivity()).inflate(R.layout.shiti_header, null);;
		headerView = hview.findViewById(R.id.header_view);
		headerImg = (ImageView) headerView.findViewById(R.id.header_img);
		listView.addHeaderView(hview);
		listView.setOnItemClickListener(this);
		titleView = (TextView) view.findViewById(R.id.shiti_title);
		shitiNoView = (TextView) view.findViewById(R.id.shiti_no);
		Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SHOWG.TTF");
		shitiNoView.setTypeface(face);
		
		coinView = (TextView) view.findViewById(R.id.coin);
		coinView.setTypeface(face);
		
		request();
		
	}
	
	private void request(){
		Preferences.User user = new Preferences.User(getActivity());
		final GameLibraryGets gets = new GameLibraryGets(getActivity(), new GameLibraryGets.Params(user.getUid(), user.getToken(), 20));
		RequestAsync.request(gets, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				if(gets.getResults() != null){
					results = gets.getResults();
					build();
				}
				
			}
		});
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	private int i = 0;
	
	private void next(){
		i++;
		build();
	}
	
	private void prev(){
		i--;
		build();
	}
	
	private void build(){
		if(i==0){
			btnPrev.setVisibility(View.INVISIBLE);
		}else{
			btnPrev.setVisibility(View.VISIBLE);
		}
		if(i==results.size()-1){
			btnNext.setVisibility(View.INVISIBLE);
		}else{
			btnNext.setVisibility(View.VISIBLE);
		}
		
		shitiNoView.setText(""+(i+1));
		Result result = results.get(i);
		if(TextUtils.isEmpty(result.getImage())){
			headerView.setVisibility(View.GONE);
		}else{
			headerView.setVisibility(View.VISIBLE);
			headerImg.setImageBitmap(null);
			BitmapLoader.displayImage(getActivity(), result.getImage(), headerImg);
		}
		titleView.setText(result.getTitle());
		list = hashMap.get(result.getId());
		if(list == null){
			list = result.toShiti();
			hashMap.put(result.getId(), list);
		}
		
		adapter = new ShitiListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		list.setIndex(position-1);
		adapter.notifyDataSetChanged();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_prev:
			prev();
			break;
		case R.id.btn_next:
			next();
			break;
		default:
			break;
		}
		
	}
	
	
	
}
