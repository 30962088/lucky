package com.mengle.lucky.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout.LayoutParams;

public class AnimationMargin extends Animation{

	private View view;
	
	private float left;
	
	public AnimationMargin(View view,float left) {
		super();
		this.left = left;
		this.view = view;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		super.start();
		
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		
		
		view.layout( (int)(left * interpolatedTime), 0,  (int)(left * interpolatedTime) + view.getMeasuredWidth(), 0 + view.getMeasuredHeight() );
	}
	
}
