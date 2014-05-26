package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

public class UserMeUnFollow extends Request{

	public static class Params{
		protected int uid;
		protected String token;
		protected int fuid;
		public Params(int uid, String token, int fuid) {
			super();
			this.uid = uid;
			this.token = token;
			this.fuid = fuid;
		}
		
	}
	
	private Params params;
	
	
	
	public UserMeUnFollow(Params params) {
		super();
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
		return HOST+"me/unfollow/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
