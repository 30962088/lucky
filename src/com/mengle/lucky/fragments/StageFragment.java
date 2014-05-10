package com.mengle.lucky.fragments;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.adapter.StageAdapter;
import com.mengle.lucky.wiget.TabPageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StageFragment extends Fragment{

	
	private StageAdapter adapter;
	
	
	
	public void setAdapter(StageAdapter adapter) {
		this.adapter = adapter;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.stage_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		init();
	}
	
	private void init(){
		if(getActivity() instanceof MainActivity){
//			((MainActivity)getActivity()).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		}
		if(adapter != null){
			ViewPager viewpager = (ViewPager) getView().findViewById(R.id.viewpager);
			viewpager.setAdapter(adapter);
			
			TabPageIndicator indicator = (TabPageIndicator) getView().findViewById(R.id.indicator);
			indicator.setViewPager(viewpager);
		}
		
	}
	
}
