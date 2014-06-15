package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.DisplayUtils;

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

public class IntroDialog implements OnClickListener{
	
	private PopupWindow mPopupWindow;
	
	private Context context;

	private String text;
	
	private View parent;
	
	private String title;
	
	private IntroDialog(Context context,String title,String text,View parent) {
		this.context = context;
		this.title = title;
		this.text = text;
		this.parent = parent;
		init();
	}
	
	public static void open(Context context,String title,String text,View parent){
		new IntroDialog(context,title,text,parent);
	}
	
	private void init(){
		View view = LayoutInflater.from(context).inflate(R.layout.intro_dialog_layout,
				null);
		view.setOnClickListener(this);
		((TextView)view.findViewById(R.id.content)).setText(text);
		((TextView)view.findViewById(R.id.title)).setText(title);
		if(parent != null){
			int height = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
			mPopupWindow = new PopupWindow(view, parent.getWidth(),
					height-DisplayUtils.Dp2Px(context, 25)
					, true);
		}else{
			mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, true);
		}
		
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#80b7bbc2")));
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		
		Animation rotation = AnimationUtils.loadAnimation(context,
				R.anim.slide_from_buttom);

		View popup = view.findViewById(R.id.popup);
		popup.setOnClickListener(this);
		popup.startAnimation(rotation);
		view.findViewById(R.id.cancel).setOnClickListener(this);
		if(parent != null){
			mPopupWindow.showAtLocation(parent, Gravity.LEFT, 0, 0);
		}else{
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
		
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alert:
		case R.id.cancel:
			mPopupWindow.dismiss();
			
			break;
		default:
			break;
		}

	}
	
	
	
}
