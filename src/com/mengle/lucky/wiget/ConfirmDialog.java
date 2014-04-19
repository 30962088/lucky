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

public class ConfirmDialog implements OnClickListener{
	
	private PopupWindow mPopupWindow;
	
	private Context context;

	private String text;
	
	private View parent;
	
	private OnConfirmClick confirmClick;
	
	public static interface OnConfirmClick{
		public void onConfirm();
		public void onCancel();
	}
	
	private ConfirmDialog(Context context,String text,OnConfirmClick confirmClick,View parent) {
		this.context = context;
		this.text = text;
		this.parent = parent;
		this.confirmClick = confirmClick;
		init();
	}
	
	public static void open(Context context,String text,OnConfirmClick confirmClick,View parent){
		new ConfirmDialog(context,text,confirmClick,parent);
	}
	
	private void init(){
		View view = LayoutInflater.from(context).inflate(R.layout.confirm_layout,
				null);
		view.setOnClickListener(this);
		((TextView)view.findViewById(R.id.msg)).setText(text);
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
		view.findViewById(R.id.ok).setOnClickListener(this);
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
			confirmClick.onCancel();
			break;
		case R.id.ok:
			mPopupWindow.dismiss();
			confirmClick.onConfirm();
			break;
		default:
			break;
		}

	}
	
	
	
}
