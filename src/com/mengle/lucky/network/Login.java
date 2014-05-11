package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.mengle.lucky.App;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.User;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.ShareAccount;
import com.mengle.lucky.utils.ShareUtils;
import com.mengle.lucky.utils.ShareAccount.SINAToken;
import com.mengle.lucky.utils.ShareUtils.OperateListener;

public class Login extends Request{
	
	public static interface OnLoginListener{
		public void onLoginSuccess(Result result);
		public void onLoginError(String msg);
	}
	
	private static void onWeiboLoginSuccess(JSONObject obj,final OnLoginListener loginListener) throws JSONException{
		Params params = new Login.Params("92", "android", "weibo", obj.getString("id"), obj.getString("name"));
		String gender =  obj.getString("gender");
		if("m".equals(gender)){
			params.setGender(1);
		}else if("f".equals(gender)){
			params.setGender(0);
		}else{
			params.setGender(-1);
		}
		params.setAvatar(obj.getString("avatar_hd"));
		final Login login = new Login(params);
		RequestAsync.request(login, new Async() {
			
			public void onPostExecute(Request request) {
				if(login.getStatus() == Request.Status.SUCCESS){
					Result result = login.getResult();
					loginListener.onLoginSuccess(result);
					onLoginSuccess(App.getInstance().getApplicationContext(), result.getUid() , result.getToken());
				}else{
					loginListener.onLoginError(login.getErrorMsg());
				}
				
			}
		});
	}
	
	public static void onWeiboLogin(final Context context,final OnLoginListener loginListener){
		new ShareUtils.Weibo(context)
		.login(new OperateListener<ShareAccount.SINAToken>() {

			public void onSuccess(SINAToken t) {
				
				new ShareUtils.Weibo(context).getUser(t,
						new OperateListener<JSONObject>() {

							public void onSuccess(JSONObject t) {
								
								try {
									onWeiboLoginSuccess(t,loginListener);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

							public void onError(String msg) {
								// TODO Auto-generated method stub

							}
						});

			}

			public void onError(String msg) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void onLoginSuccess(Context context,int uid,String token){
		Preferences.User user = new Preferences.User(context);
		user.setToken(token);
		user.setUid(uid);
	}
	
	public class Result extends User{
		private String token;
		public String getToken() {
			return token;
		}
	}

	public static class Params{
		
		protected String channel_id;//云推送的渠道Id
		
		protected String os;//用户当前登录的操作系统
		
		protected String via;//SNS的提供商(weibo/tqq/qq/renren/kaixin)
		
		protected String openid;//SNS平台用户的uid
		
		protected String nickname;//nickname
		
		protected int gender;//性别 可以为空;-1: 未知道; 0:女性; 1:男性
		
		protected String avatar;//SNS平台用户的头像(如果用户在SNS平台设置了,就必须传)
		
		protected String access_token;//用户认证过后SNS平台分配的token(暂时未用)
		
		protected String expires_in;//用户认证过后SNS平台分配的token的有效时间(暂时未用)

		public Params(String channel_id, String os, String via, String openid,
				String nickname) {
			super();
			this.channel_id = channel_id;
			this.os = os;
			this.via = via;
			this.openid = openid;
			this.nickname = nickname;
		}
		
		public void setGender(int gender) {
			this.gender = gender;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public void setExpires_in(String expires_in) {
			this.expires_in = expires_in;
		}
		
	}
	
	private Params params;
	
	private Result result;
	
	public Login(Params params) {
		this.params = params;
	}

	public void onSuccess(String data) {
		result = new Gson().fromJson(data, Result.class);
		
	}
	
	public Result getResult() {
		return result;
	}

	public void onError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	public void onResultError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	public void onServerError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return HOST+"user/sns/login/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}