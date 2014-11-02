package com.mengle.lucky.network;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.mengle.lucky.network.model.User;

import android.content.Context;

public class UserMeHeadUploadRequest extends Request{

	public static class Param{
		private int uid;
		private String token;
		private File file;
		private String head_url;
		public Param(int uid, String token, File file) {
			super();
			this.uid = uid;
			this.token = token;
			this.file = file;
		}
		public Param(int uid, String token, String head_url) {
			super();
			this.uid = uid;
			this.token = token;
			this.head_url = head_url;
		}
		
		
	}
	
	private Param param;
	
	private User user;
	
	public UserMeHeadUploadRequest(Context context, Param param) {
		super(context);
		this.param = param;
	}
	
	public User getUser() {
		return user;
	}

	@Override
	public void onSuccess(String data) {
		user = new Gson().fromJson(data, User.class);
		
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
		return HOST+"me/head/upload/";
	}
	
	@Override
	protected List<BinaryBody> fillFiles() {
		if(param.file == null){
			return null;
		}
		ArrayList<BinaryBody> bodies = new ArrayList<Request.BinaryBody>();
		bodies.add(new BinaryBody("head.jpg", param.file, "image/jpeg", "head"));
		return bodies;
	}

	@Override
	public List<NameValuePair> fillParams() {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("uid", ""+param.uid));
		pairs.add(new BasicNameValuePair("token", ""+param.token));
		if(param.head_url != null){
			pairs.add(new BasicNameValuePair("head_url", ""+param.head_url));
		}
		return pairs;
	}

}
