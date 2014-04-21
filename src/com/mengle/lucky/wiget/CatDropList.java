package com.mengle.lucky.wiget;

import com.mengle.lucky.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.view.View.OnClickListener;;

public class CatDropList extends FrameLayout implements AnimationListener,OnClickListener{

	public static interface OnStateChange{
		public void onShow();
		public void onDismiss();
	}
	
	public CatDropList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CatDropList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CatDropList(Context context) {
		super(context);
		init();
	}
	
	private GridView gridView;
	private Animation animIn;
	private Animation animOut;
	private View innerView;
	private OnStateChange onStateChange;
	public void setOnStateChange(OnStateChange onStateChange) {
		this.onStateChange = onStateChange;
	}
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.cat_layout, this);
		setVisibility(View.GONE);
		animIn = AnimationUtils.loadAnimation(getContext(),
				R.anim.slide_from_top);
		animIn.setFillAfter(true);
		
		animOut = AnimationUtils.loadAnimation(getContext(),
				R.anim.slide_up_top);
		animOut.setFillBefore(true);
		animOut.setAnimationListener(this);
		gridView = (GridView) findViewById(R.id.catgrid);
		innerView = findViewById(R.id.inner);
		innerView.setOnClickListener(this);
		findViewById(R.id.outer).setOnClickListener(this);
		findViewById(R.id.close).setOnClickListener(this);
	}
	
	public void setAdapter(ListAdapter adapter){
		gridView.setAdapter(adapter);
	}
	
	public void show(){
		setVisibility(View.VISIBLE);
		startAnimation(animIn);
		onStateChange.onShow();
	}
	
	public void dismiss(){
		startAnimation(animOut);
	}

	public void onAnimationEnd(Animation animation) {
		if(animation == animOut){
			setVisibility(View.GONE);
			if(onStateChange != null){
				onStateChange.onDismiss();
			}
		}
		
	}

	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.outer:
			
		case R.id.close:
			dismiss();
			break;
		}
		
	}
	
	
}
