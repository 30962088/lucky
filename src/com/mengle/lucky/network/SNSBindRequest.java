package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

public class SNSBindRequest extends Request{

	public static class Params{
		protected int uid;
		protected String token;
		protected String via;
		protected String openid;
		protected String nickname;
		protected String avatar;
		public Params(int uid,String token,Login.Params params) {
			this.uid = uid;
			this.token = token;
			via=params.getVia();
			openid = params.getOpenid();
			nickname = params.getNickname();
			avatar = params.getAvatar();
		}
	}
	
	private Params params;

	
	public SNSBindRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public void onSuccess(String data) {
		
		
	}

	@Override
	public void onError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResultError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return HOST+"me/sns/bind/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
