package com.mengle.lucky.wiget;

import android.app.ActionBar.LayoutParams;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnimationAdd extends Animation{
	
	private TextView target;
	
	private View source;
	
	private RelativeLayout.LayoutParams layoutParams;
	
	private RelativeLayout.LayoutParams layoutParams1;

	public AnimationAdd(View source, TextView target) {
		this.target = target;
		
		this.source = source;
		
		layoutParams = (RelativeLayout.LayoutParams) source.getLayoutParams();
		
		layoutParams1 = (RelativeLayout.LayoutParams) target.getLayoutParams();
		
		setDuration(1000);
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// TODO Auto-generated method stub
		super.applyTransformation(interpolatedTime, t);
		
		
		
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) source.getLayoutParams();
		
		layoutParams.width = (int) ((layoutParams.width - layoutParams1.width)*getDuration()/interpolatedTime);
		
		layoutParams.height = (int) ((layoutParams.width - layoutParams1.width)*getDuration()/interpolatedTime);
		
		
		
	}
	
	
	
	

}
