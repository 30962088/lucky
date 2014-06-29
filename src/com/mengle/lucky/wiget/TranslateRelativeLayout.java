package com.mengle.lucky.wiget;

import java.util.Date;

import com.mengle.lucky.utils.AnimationMargin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class TranslateRelativeLayout extends RelativeLayout {

	public TranslateRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TranslateRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TranslateRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}



	public static interface ExpanedListener{
		public void change(boolean expand);
	}
	
	private ExpanedListener expanedListener;
	
	public void setExpanedListener(ExpanedListener expanedListener) {
		this.expanedListener = expanedListener;
	}
	
	private boolean expand = false;

	@SuppressLint("NewApi")
	public void setExpand(final boolean expand,long during){
		
		if(during == 0 || this.expand != expand){
			Animation translateAnimation;
			
			if(expand){
				translateAnimation = new AnimationMargin(this, max);
			}else{
				translateAnimation =  new AnimationMargin(this, 0);
			}
			translateAnimation.setDuration(during);
			translateAnimation.setFillAfter(true);
			
			startAnimation(translateAnimation);
			this.expand = expand;
			if(expanedListener != null){
				expanedListener.change(expand);
			}
		}
		
	}
	
	private float x;
	
	private long time;
	
	private float max = 50;

	
	public void setMax(float max) {
		this.max = max;
	}
	
	
	private OnClickListener onClickListener;
	
	@Override
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
	
	
	
	
	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX(0);
			time = new Date().getTime();
			return true;
			

	
		case MotionEvent.ACTION_UP:
			if(Math.abs(event.getX(0)-x)< 10){
				if(onClickListener != null){
					onClickListener.onClick(this);
					return super.onTouchEvent(event);
				}
			}
			if(new Date().getTime()-time<1000){
				if(event.getX(0)-x>0){
					setExpand(true,200);
				}else{
					setExpand(false,200);
				}
			}
			
			
			return true;
		}
		return super.onTouchEvent(event);
	}


}
