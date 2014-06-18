package com.mengle.lucky.fragments;

import com.mengle.lucky.R;
import com.mengle.lucky.wiget.AwardAnimView;
import com.mengle.lucky.wiget.AwardAnimView.Callback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AwardFragment extends Fragment implements Callback{
	
	private AwardAnimView animView;
	
	private View flowerView;
	
	private Animation fadeaAnimation;
	
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
		animView.setCallback(this);
		flowerView = view.findViewById(R.id.flower);
		fadeaAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.flower);
		fadeaAnimation.setFillAfter(true);
		view.findViewById(R.id.btn_enter);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		animView.start();
	}

	@Override
	public void onsuccess() {
		flowerView.startAnimation(fadeaAnimation);
		
	}
	
	

}
