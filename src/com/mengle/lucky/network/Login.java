package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mengle.lucky.App;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.User;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.Push;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.exception.SocializeException;

public class Login extends Request {

	public static interface OnLoginListener {
		public void onLoginSuccess(Result result);

		public void onLoginError(String msg);
	}
	
	

	




	public class Result extends User {
		private String token;

		public String getToken() {
			return token;
		}
	}

	public static class Params {
		
		protected String user_id;

		protected String channel_id;// 云推送的渠道Id

		protected String os = "android";// 用户当前登录的操作系统

		protected String via;// SNS的提供商(weibo/tqq/qq/renren/kaixin)

		protected String openid;// SNS平台用户的uid

		protected String nickname;// nickname

		protected int gender;// 性别 可以为空;-1: 未知道; 0:女性; 1:男性

		protected String avatar;// SNS平台用户的头像(如果用户在SNS平台设置了,就必须传)

		protected String access_token;// 用户认证过后SNS平台分配的token(暂时未用)

		protected String expires_in;// 用户认证过后SNS平台分配的token的有效时间(暂时未用)

		public Params() {
			Push push = new Push(App.getInstance());
			user_id = push.getUserId();
			channel_id = push.getChanelId();
		}

		
		
		

		
		


		public String getVia() {
			return via;
		}



		public void setVia(String via) {
			this.via = via;
		}



		public String getOpenid() {
			return openid;
		}



		public void setOpenid(String openid) {
			this.openid = openid;
		}



		public String getNickname() {
			return nickname;
		}



		public void setNickname(String nickname) {
			this.nickname = nickname;
		}



		public int getGender() {
			return gender;
		}



		public String getAvatar() {
			return avatar;
		}



		public String getAccess_token() {
			return access_token;
		}



		public String getExpires_in() {
			return expires_in;
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
	
	private Context context;

	private Result result;

	public Login(Context context,Params params) {
		super(context);
		this.context = context;
		this.params = params;
	}

	public void onSuccess(String data) {
		result = new Gson().fromJson(data, Result.class);
		Preferences.User user = new Preferences.User(context);
		user.setToken(result.token);
		user.setUid(result.getUid());
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
		return HOST + "user/sns/login/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
