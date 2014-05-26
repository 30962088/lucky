package com.mengle.lucky.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.mengle.lucky.network.Login.Params;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SnsAccount;
import com.umeng.socialize.bean.SocializeUser;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.FetchUserListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.exception.SocializeException;

public class OauthUtils implements UMAuthListener,FetchUserListener{
	
	private UMSocialService mController;
	
	private Context context;
	
	private SHARE_MEDIA media;
	
	
	public static interface Callback{
		public void onSuccess(Params params);
	}
	
	private Callback callback;
	
	public OauthUtils(Context context){
		this.context = context;
		mController = UMServiceFactory.getUMSocialService("com.umeng.login", RequestType.SOCIAL);
	}
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	public void tencentOauth(){
		media = SHARE_MEDIA.TENCENT;
		oauth();
	}
	
	public void sinaOauth(){
		media = SHARE_MEDIA.SINA;
		oauth();
	}
	
	public void renrenOauth() {
		media = SHARE_MEDIA.RENREN;
		oauth();
		
	}
	
	public void qqOauth(){
		media = SHARE_MEDIA.QQ;
		mController.getConfig().supportQQPlatform((Activity)context,"100424468","http://www.umeng.com/social","social");  
		oauth();
	}
	
	private void oauth(){
		mController.doOauthVerify(context, media, this);
	}

	@Override
	public void onCancel(SHARE_MEDIA arg0) {
		// TODO Auto-generated method stub
		
	}

	private Params params;
	
	@Override
	public void onComplete(Bundle arg0, SHARE_MEDIA arg1) {
		mController.getUserInfo(context,this);
		if(arg1 == SHARE_MEDIA.TENCENT){
			params = new Params();
			params.setOpenid(arg0.getString("uid"));
			params.setAccess_token(arg0.getString("access_key"));
			params.setExpires_in(arg0.getString("expires_in"));
			params.setVia("tqq");
		}else if(arg1 == SHARE_MEDIA.QQ){
			params = new Params();
			params.setOpenid(arg0.getString("uid"));
			params.setAccess_token(arg0.getString("access_token"));
			params.setExpires_in(arg0.getString("expires_in"));
			params.setVia("qq");
		}else if(arg1 == SHARE_MEDIA.SINA){
			params = new Params();
			params.setOpenid(arg0.getString("uid"));
			params.setAccess_token(arg0.getString("access_key"));
			params.setExpires_in(arg0.getString("expires_in"));
			params.setVia("weibo");
		}else if(arg1 == SHARE_MEDIA.RENREN){
			params = new Params();
			params.setOpenid(arg0.getString("uid"));
			params.setAccess_token(arg0.getString("access_key"));
			params.setExpires_in(arg0.getString("expires_in"));
			params.setVia("renren");
		}
	}

	@Override
	public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(SHARE_MEDIA arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComplete(int arg0, SocializeUser user) {
		SnsAccount snsAccount = null;
		if( user != null && user.mAccounts != null){
			for(SnsAccount account : user.mAccounts){
				if(media == SHARE_MEDIA.SINA && "sina".equals(account.getPlatform())){
					snsAccount= account;
					break;
				}else if(media == SHARE_MEDIA.TENCENT && "tencent".equals(account.getPlatform())){
					snsAccount= account;
					
					break;
				}else if(media == SHARE_MEDIA.QQ && "qq".equals(account.getPlatform())){
					snsAccount= account;
					break;
				}else if(media == SHARE_MEDIA.RENREN && "renren".equals(account.getPlatform())){
					snsAccount= account;
					break;
				}
				
			}
		}
		params.setAvatar(snsAccount.getAccountIconUrl());
		params.setNickname(snsAccount.getUserName());
		params.setGender(snsAccount.getGender().ordinal());
		if(callback != null){
			callback.onSuccess(params);
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}
	
}
