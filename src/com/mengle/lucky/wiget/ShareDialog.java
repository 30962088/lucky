package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.umeng.socialize.bean.SHARE_MEDIA;

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

public class ShareDialog implements OnClickListener{
	
	public static interface Callback{
		public void onClick(SHARE_MEDIA media,String name);
	}
	
	private PopupWindow mPopupWindow;
	
	private Context context;

	private String title;
	
	private View parent;
	
	private Callback callback;
	
	private ShareDialog(Context context,String title,Callback callback,View parent) {
		this.context = context;
		this.title = title;
		this.parent = parent;
		this.callback = callback;
		init();
	}
	
	public static void open(Context context,String text,Callback callback,View parent){
		new ShareDialog(context,text,callback,parent);
	}
	
	private void init(){
		View view = LayoutInflater.from(context).inflate(R.layout.share_dialog_layout,
				null);
		view.findViewById(R.id.wechat).setOnClickListener(this);
		view.findViewById(R.id.timeline).setOnClickListener(this);
		view.findViewById(R.id.sina).setOnClickListener(this);
		view.findViewById(R.id.tencent).setOnClickListener(this);
		view.findViewById(R.id.renren).setOnClickListener(this);
		view.setOnClickListener(this);
		((TextView)view.findViewById(R.id.title)).setText(title);
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
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alert:
		case R.id.cancel:
			mPopupWindow.dismiss();
			break;
		case R.id.sina:
			mPopupWindow.dismiss();
			callback.onClick(SHARE_MEDIA.SINA,"新浪微博");
			break;
		case R.id.timeline:
			mPopupWindow.dismiss();
			callback.onClick(SHARE_MEDIA.WEIXIN_CIRCLE,"微信");
			break;
		case R.id.wechat:
			mPopupWindow.dismiss();
			callback.onClick(SHARE_MEDIA.WEIXIN,"微信");
			break;
		case R.id.tencent:
			mPopupWindow.dismiss();
			callback.onClick(SHARE_MEDIA.TENCENT,"腾讯微博");
			break;
		case R.id.renren:
			mPopupWindow.dismiss();
			callback.onClick(SHARE_MEDIA.RENREN,"人人");
			break;
		default:
			break;
		}

	}
	
	
	
}
