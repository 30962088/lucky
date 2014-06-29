package com.mengle.lucky.wiget;

import com.mengle.lucky.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

public class GameIntroDialog implements OnClickListener{
	
	private PopupWindow mPopupWindow;
	
	private Context context;

	
	private GameIntroDialog(Context context) {
		this.context = context;
		init();
	}
	
	public static void open(Context context){
		new GameIntroDialog(context);
	}
	
	private void init(){
		View view = LayoutInflater.from(context).inflate(R.layout.game_intro_layout,
				null);
		view.setOnClickListener(this);
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#b3000000")));

		mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		
	}
	
	public void onClick(View v) {
		mPopupWindow.dismiss();

	}
	
	
	
}
