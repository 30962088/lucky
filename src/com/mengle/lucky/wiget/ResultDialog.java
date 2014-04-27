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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ResultDialog implements OnClickListener{

	public enum Status{
		CHECK,SUCCESS,FAIL
	}
	
	public static class Result{
		private Status status;
		private int coin;
		public Result(Status status, int coin) {
			super();
			this.status = status;
			this.coin = coin;
		}
		
	}
	
	private PopupWindow mPopupWindow;
	
	private Context context;
	
	private Result result;
	
	private View checkWeibo;
	
	private View checkRenren;
	
	private View checkTencent;

	public ResultDialog(Context context, Result result) {
		super();
		this.context = context;
		this.result = result;
		init();
	}
	
	private void init(){
		View view = LayoutInflater.from(context).inflate(R.layout.result_layout,
				null);
		
		view.setOnClickListener(this);
		
		TextView introView = (TextView) view.findViewById(R.id.intro);
		String introStr = null;
		if(result.status == Status.FAIL){
			introStr = context.getString(R.string.popup_intro_fail, result.coin);
		}else if(result.status == Status.CHECK){
			introStr = context.getString(R.string.popup_intro_check, result.coin);
		}else{
			introStr = context.getString(R.string.popup_intro_success, result.coin);
		}
		introView.setText(introStr);
		
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, true);
		
		
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#80b7bbc2")));
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		
		Animation rotation = AnimationUtils.loadAnimation(context,
				R.anim.zoom_center);

		View popup = view.findViewById(R.id.popup);
		if(result.status == Status.FAIL){
			popup.setBackgroundResource(R.drawable.bg_popup_blue);
		}else{
			popup.setBackgroundResource(R.drawable.bg_popup_red);
		}
		popup.setOnClickListener(this);
		popup.startAnimation(rotation);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		if(result.status == Status.SUCCESS){
			cancel.setText("收取");
		}else{
			cancel.setText("关闭");
		}
		TextView coin = (TextView) view.findViewById(R.id.coin);
		if(result.status == Status.FAIL){
			coin.setText("-"+result.coin);
		}else{
			coin.setText(""+result.coin);
		}
		cancel.setOnClickListener(this);
		
		checkWeibo = view.findViewById(R.id.check_weibo);
		checkWeibo.setOnClickListener(this);
		checkRenren = view.findViewById(R.id.check_renren);
		checkRenren.setOnClickListener(this);
		checkTencent = view.findViewById(R.id.check_tencent);
		checkTencent.setOnClickListener(this);
		mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		
		
	}
	
	private void switchCheck(View v){
		v.setSelected(!v.isSelected());
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.check_renren:
		case R.id.check_tencent:
		case R.id.check_weibo:
			switchCheck(v);
			break;
		case R.id.result_dialog:
		case R.id.cancel:
			mPopupWindow.dismiss();
		default:
			break;
		}

	}
	
	

	
}
