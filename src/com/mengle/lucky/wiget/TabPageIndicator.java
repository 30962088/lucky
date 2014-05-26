package com.mengle.lucky.wiget;

import java.util.List;

import com.mengle.lucky.R;



import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class TabPageIndicator extends LinearLayout{

	public static interface OnTabClickLisenter{
		public boolean onclick(int index,View tabView);
	}
	
	private OnPageChangeListener onPageChangeListener;
	
	
	
	private View viewSelected;
	
	private ViewPager viewPager;

	
	
	
	public void setOnPageChangeListener(
			OnPageChangeListener onPageChangeListener) {
		this.onPageChangeListener = onPageChangeListener;
	}
	
	public TabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TabPageIndicator(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	

	
	private void init(){
		float margin = getResources().getDimension(R.dimen.tab_indicator_margin);
		int size = (int) getResources().getDimension(R.dimen.tab_size);
		removeAllViews();
		for(int i = 0;i<viewPager.getAdapter().getCount();i++){
			
			View view =  LayoutInflater.from(getContext()).inflate(R.layout.tab_indicator,null);
			
			
			
			LayoutParams params = new LayoutParams(size,size);
			params.leftMargin = (int) margin;
			params.rightMargin = (int) margin;
			addView(view, params);
			
		}
		if(viewPager.getAdapter().getCount()>0){
			setSelected(0);
		}
		
	}

	public void setSelected(int position) {
		if(viewSelected != null){
			viewSelected.setSelected(false);
		}
		viewSelected = getChildAt(position);
		viewSelected.setSelected(true);
		
	}
	
	

	public void setViewPager(ViewPager pager){
		this.viewPager = pager;
		init();
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			public void onPageSelected(int position) {
				if(onPageChangeListener != null){
					onPageChangeListener.onPageSelected(position);
				}
				setSelected(position);
				
				
			}
			
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if(onPageChangeListener != null){
					onPageChangeListener.onPageScrolled(arg0, arg1, arg2);
				}
				
			}
			
			public void onPageScrollStateChanged(int arg0) {
				if(onPageChangeListener != null){
					onPageChangeListener.onPageScrollStateChanged(arg0);
				}
				
			}
		});
	}
	

	
}
