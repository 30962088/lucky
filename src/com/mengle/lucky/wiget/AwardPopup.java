package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.wiget.AwardAnimView.Callback;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.PopupWindow;

public class AwardPopup implements Callback,AnimationListener,OnClickListener{
	private PopupWindow mPopupWindow;

	public static void open(Context context,int award){
		new AwardPopup(context,award);
	}
	

	private AwardAnimView animView;
	
	private View flowerView;
	
	private Animation fadeaAnimation;
	
	private AwardPopup(Context context, int award) {
		View view = LayoutInflater.from(context).inflate(R.layout.award_layout,
				null);
		
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#80b7bbc2")));
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		animView = (AwardAnimView) view.findViewById(R.id.animView);
		animView.setAward(award);
		animView.setCallback(this);
		flowerView = view.findViewById(R.id.flower);
		fadeaAnimation = AnimationUtils.loadAnimation(context, R.anim.flower);
		fadeaAnimation.setFillAfter(true);
		view.findViewById(R.id.btn_enter).setOnClickListener(this);
//		mPopupWindow.setAnimationStyle(R.anim.slide_from_buttom);
		
		mPopupWindow.showAtLocation(view, Gravity.FILL, 0, 0);
		animView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				animView.start();
				
			}
		}, 1000);
		
	}
	

	@Override
	public void onsuccess() {
		flowerView.startAnimation(fadeaAnimation);
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_enter:
			mPopupWindow.dismiss();
			break;

		default:
			break;
		}
		
	}

}
