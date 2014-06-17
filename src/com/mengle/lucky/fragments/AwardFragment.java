package com.mengle.lucky.fragments;

import com.mengle.lucky.R;
import com.mengle.lucky.wiget.AwardAnimView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AwardFragment extends Fragment{
	
	private AwardAnimView animView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.award_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		animView = (AwardAnimView) view.findViewById(R.id.animView);
		animView.setAward(5);
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		animView.start();
	}

}
