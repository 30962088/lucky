package com.mengle.lucky.wiget;

import java.util.Map;

import com.mengle.lucky.R;
import com.mengle.lucky.network.Login.Params;
import com.mengle.lucky.utils.OauthUtils;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.OauthUtils.Callback;
import com.umeng.socialize.bean.MultiStatus;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.MulStatusListener;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.media.UMImage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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

public class ResultDialog implements OnClickListener,SnsPostListener{

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
	
	private OauthUtils oauthUtils;
	
	private UMSocialService mController;

	public ResultDialog(Context context, Result result) {
		super();
		this.context = context;
		this.result = result;
		oauthUtils = new OauthUtils(context);
		init();
		mController  = UMServiceFactory.getUMSocialService("com.umeng.login", RequestType.SOCIAL);
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
	
	
	private void switchCheck(final View v){
		SHARE_MEDIA media = SHARE_MEDIA.SINA;
		switch (v.getId()) {
		case R.id.check_renren:
			media = SHARE_MEDIA.RENREN;
			break;
		case R.id.check_tencent:
			media = SHARE_MEDIA.TENCENT;
			break;
		case R.id.check_weibo:
			media = SHARE_MEDIA.SINA;
			break;
		}
		
		
		v.setSelected(!v.isSelected());
		if(v.isSelected()){
			mController.checkTokenExpired(context, new SHARE_MEDIA[]{media}, new UMDataListener() {
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onComplete(int arg0, Map<String, Object> arg1) {
					String key = arg1.keySet().toArray()[0].toString();
					if((Boolean)arg1.get(key)  == false){
						v.setSelected(false);
						oauthUtils.setCallback(new Callback() {
							
							@Override
							public void onSuccess(Params params) {
								switchCheck(v);
								
							}
						});
						switch (v.getId()) {
						case R.id.check_renren:
							oauthUtils.renrenOauth();
							break;
						case R.id.check_tencent:
							oauthUtils.tencentOauth();
							break;
						case R.id.check_weibo:
							oauthUtils.sinaOauth();
							break;
						}
						
						
					}
					
				}
			});
		}
		
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
			View view = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
			Bitmap bitmap = Utils.convertViewToBitmap(view);
			mController.setShareImage(new UMImage(context, bitmap));
			mController.setShareContent("亲，别抠脚看热闹了。猜中就有金币拿，你也来试试？");
			if(checkRenren.isSelected()){
				
				mController.directShare(context,SHARE_MEDIA.RENREN, this);
			}
			if(checkWeibo.isSelected()){
				mController.directShare(context,SHARE_MEDIA.SINA, this);
			}
			if(checkTencent.isSelected()){
				mController.directShare(context,SHARE_MEDIA.TENCENT, this);
			}
			
		default:
			break;
		}

	}


	@Override
	public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
