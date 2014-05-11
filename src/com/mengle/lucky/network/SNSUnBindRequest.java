package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.mengle.lucky.network.model.User;

public class SNSUnBindRequest extends Request{

	public static class Params{
		protected int uid;
		protected String token;
		protected String via;
		public Params(int uid, String token, String via) {
			super();
			this.uid = uid;
			this.token = token;
			this.via = via;
		}
		
	}
	
	private Params params;
	
	public SNSUnBindRequest(Params params) {
		super();
		this.params = params;
	}
	
	private User user;
	
	public User getUser() {
		return user;
	}

	public void onSuccess(String data) {
		user = new Gson().fromJson(data, User.class);
		
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
		return HOST+"me/sns/unbind/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		
		return build(params);
	}

}
