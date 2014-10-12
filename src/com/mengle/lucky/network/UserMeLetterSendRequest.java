package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

public class UserMeLetterSendRequest extends Request{

	public static class Param{
		protected int uid;
		protected String token;
		protected int to;
		protected String content;
		public Param(int uid, String token, int to, String content) {
			super();
			this.uid = uid;
			this.token = token;
			this.to = to;
			this.content = content;
		}
		
	}
	
	private Param param;
	
	
	
	public UserMeLetterSendRequest(Context context, Param param) {
		super(context);
		this.param = param;
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
		return HOST+"me/letter/send/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

	
	
}
