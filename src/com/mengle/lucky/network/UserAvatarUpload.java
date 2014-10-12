package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

public class UserAvatarUpload extends Request{

	public UserAvatarUpload(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private int uid;
	private String token;
	
	
	public void onSuccess(String data) {
		// TODO Auto-generated method stub
		
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
		return null;
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return null;
	}

}
