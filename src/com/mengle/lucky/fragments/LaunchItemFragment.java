package com.mengle.lucky.fragments;


import com.mengle.lucky.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class LaunchItemFragment extends BlurFragment{
	
	public static class Item{
		private int top;
		private int bottom;
		public Item(int top, int bottom) {
			super();
			this.top = top;
			this.bottom = bottom;
		}
		
		public int getBottom() {
			return bottom;
		}
		public int getTop() {
			return top;
		}
		
	}
	
	public static LaunchItemFragment newInstance(Item item){
		LaunchItemFragment fragment = new LaunchItemFragment();
		fragment.item = item;
		return fragment;
	}
	
	private ImageView topView;
	
	private ImageView bottomView;
	
	private Item item;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.guide_item, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		topView= (ImageView) view.findViewById(R.id.top);
		
		bottomView= (ImageView) view.findViewById(R.id.bottom);
		
		topView.setImageResource(item.top);
		
		bottomView.setImageResource(item.bottom);
		
	}
	
	@Override
	public void setBlur(boolean blur){
		if(blur){
			topView.setVisibility(View.INVISIBLE);
			bottomView.setVisibility(View.VISIBLE);
		}else{
			topView.setVisibility(View.VISIBLE);
			bottomView.setVisibility(View.INVISIBLE);
		}
	}
	
	

}
