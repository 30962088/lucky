package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.Preferences.Push;

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

public class PushDialog implements OnClickListener{
	
	private PopupWindow mPopupWindow;
	
	private Context context;

	
	private View parent;
	
	private PushDialog(Context context,View parent) {
		this.context = context;
		this.parent = parent;
		init();
	}
	
	public static void open(Context context,View parent){
		new PushDialog(context,parent);
	}
	
	private void init(){
		View view = LayoutInflater.from(context).inflate(R.layout.pushmanager_layout,
				null);
		view.setOnClickListener(this);
		initSwitch(view);
		if(parent != null){
			int height = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
			mPopupWindow = new PopupWindow(view, parent.getWidth(),
					height
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
	
	private void initSwitch(View view){
		Push push = new Push(context);
		View msg = view.findViewById(R.id.msg);
		msg.setOnClickListener(this);
		msg.setSelected(push.isMsg());
		View result = view.findViewById(R.id.result);
		result.setSelected(push.isResult());
		result.setOnClickListener(this);
		View announcement = view.findViewById(R.id.announcement);
		announcement.setOnClickListener(this);
		announcement.setSelected(push.isAnnouncement());
	}
	
	public void onClick(View v) {
		Push push = new Push(context);
		switch (v.getId()) {
		case R.id.alert:
		case R.id.cancel:
			mPopupWindow.dismiss();
			break;
		case R.id.msg:
			v.setSelected(!v.isSelected());
			push.setMsg(v.isSelected());
			break;
		case R.id.announcement:
			v.setSelected(!v.isSelected());
			push.setAnnouncement(v.isSelected());
			break;
		case R.id.result:
			v.setSelected(!v.isSelected());
			push.setResult(v.isSelected());
			break;
		}

	}
	
	
	
}
