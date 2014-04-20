package com.mengle.lucky.fragments;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class StageItemFragment extends Fragment{

	private String photo;
	
	private String endTime;
	
	private String title;
	
	private int person;
	
	public static StageItemFragment newInstance(String title,String photo,String endTime,int person){
		StageItemFragment fragment = new StageItemFragment();
		Bundle arguments = new Bundle();
		arguments.putString("photo", photo);
		arguments.putString("endtime", endTime);
		arguments.putInt("person", person);
		arguments.putString("title", title);
		fragment.setArguments(arguments);
		return fragment;
	}
	
	public StageItemFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle arguments = getArguments();
		title = arguments.getString("title");
		person = arguments.getInt("person");
		photo = arguments.getString("photo");
		endTime = arguments.getString("endtime");
		return inflater.inflate(R.layout.stage_item, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ImageView imageView = (ImageView) view.findViewById(R.id.img);
		BitmapLoader.displayImage(getActivity(), photo, imageView);
		TextView textView = (TextView) view.findViewById(R.id.endtime);
		textView.setText(endTime);
		TextView personView = (TextView)view.findViewById(R.id.person);
		personView.setText(""+person);
		TextView titleView = (TextView)view.findViewById(R.id.title);
		titleView.setText(title);
	}
	
}