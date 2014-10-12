package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.UserMeSetPushLetterRequest;
import com.mengle.lucky.network.UserMeSetPushNoticeRequest;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.Push;
import com.mengle.lucky.utils.Preferences.User;

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
	
	private boolean letter;
	
	private boolean msg;
	
	public static Boolean sletter;
	
	public static Boolean smsg;
	
	private PushDialog(Context context,View parent,boolean letter,boolean msg) {
		this.context = context;
		this.parent = parent;
		this.letter = letter;
		this.msg = msg;
		init();
	}
	
	public static void open(Context context,View parent,boolean letter,boolean msg){
		new PushDialog(context,parent,letter,msg);
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
		if(sletter == null){
			sletter = this.letter;
		}
		msg.setSelected(sletter);
		View result = view.findViewById(R.id.result);
		result.setSelected(push.isResult());
		result.setOnClickListener(this);
		View announcement = view.findViewById(R.id.announcement);
		announcement.setOnClickListener(this);
		if(smsg == null){
			smsg = this.msg;
		}
		announcement.setSelected(smsg);
	}
	
	private void changeLetter(boolean val){
		Preferences.User user = new Preferences.User(context);
		if(user.isLogin()){
			UserMeSetPushLetterRequest letterRequest = new UserMeSetPushLetterRequest(context, new UserMeSetPushLetterRequest.Param(user.getUid(), user.getToken(), val?1:0));
			RequestAsync.request(letterRequest, null);
		}
	}
	
	private void changeMsg(boolean val){
		Preferences.User user = new Preferences.User(context);
		if(user.isLogin()){
			UserMeSetPushNoticeRequest letterRequest = new UserMeSetPushNoticeRequest(context,new UserMeSetPushNoticeRequest.Param(user.getUid(), user.getToken(), val?1:0));
			RequestAsync.request(letterRequest, null);
		}
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
			sletter = v.isSelected();
			changeLetter(v.isSelected());
//			push.setMsg(v.isSelected());
			break;
		case R.id.announcement:
			v.setSelected(!v.isSelected());
			smsg = v.isSelected();
			changeMsg(v.isSelected());
//			push.setAnnouncement(v.isSelected());
			break;
		case R.id.result:
			v.setSelected(!v.isSelected());
			push.setResult(v.isSelected());
			break;
		}

	}
	
	
	
}
