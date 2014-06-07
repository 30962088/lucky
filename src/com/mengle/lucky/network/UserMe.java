package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.google.gson.Gson;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.User;
import com.mengle.lucky.utils.Preferences;

public class UserMe extends Request implements IUserGet{
	
	public static interface Callback{
		public void onsuccess(UserResult userResult);
	}
	
	public static void get(Context context,final Callback callback){
		if(userResult != null){
			callback.onsuccess(userResult);
			return;
		}
		
		Preferences.User user = new Preferences.User(context);
		final UserMe userMe = new UserMe(new Params(user.getUid(), user.getToken()));
		RequestAsync.request(userMe, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				if(userMe.getStatus() == Status.SUCCESS){
					callback.onsuccess(userMe.getUserResult());
				}
				
				
			}
		});
	}
	
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
	
	private UserResult user;
	
	private static UserResult userResult;
	
	public UserMe(Params params) {
		super();
		this.params = params;
	}

	public void onSuccess(String data) {
		user = new Gson().fromJson(data, UserResult.class);
		userResult = user;
	}

	public UserResult getUserResult() {
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
