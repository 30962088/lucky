package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.mengle.lucky.network.model.User;

public class UserMe extends Request{

	public static class Params{
		protected int uid;
		protected String token;
		public Params(int uid, String token) {
			super();
			this.uid = uid;
			this.token = token;
		}
		
	}
	
	private Params params;
	
	private User user;
	
	public UserMe(Params params) {
		super();
		this.params = params;
	}

	public void onSuccess(String data) {
		user = new Gson().fromJson(data, User.class);
		
	}

	public User getUser() {
		return user;
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
		return HOST+"me/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
