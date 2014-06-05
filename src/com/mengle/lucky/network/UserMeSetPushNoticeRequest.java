package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

public class UserMeSetPushNoticeRequest extends Request{

	public static class Param{
		protected int uid;
		protected String token;
		protected int state;
		public Param(int uid, String token, int state) {
			super();
			this.uid = uid;
			this.token = token;
			this.state = state;
		}
		
	}
	
	private Param param;
	
	
	
	public UserMeSetPushNoticeRequest(Param param) {
		super();
		this.param = param;
	}

	@Override
	public void onSuccess(String data) {
		// TODO Auto-generated method stub
		
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
		return HOST+"me/set/push/notice/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
