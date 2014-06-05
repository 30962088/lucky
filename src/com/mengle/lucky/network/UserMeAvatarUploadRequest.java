package com.mengle.lucky.network;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class UserMeAvatarUploadRequest extends Request{

	public static class Param{
		private int uid;
		private String token;
		private File file;
		public Param(int uid, String token, File file) {
			super();
			this.uid = uid;
			this.token = token;
			this.file = file;
		}
		
	}
	
	private Param param;
	
	
	
	public UserMeAvatarUploadRequest(Param param) {
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
		return HOST+"me/avatar/upload/";
	}
	
	@Override
	protected List<BinaryBody> fillFiles() {
		ArrayList<BinaryBody> bodies = new ArrayList<Request.BinaryBody>();
		bodies.add(new BinaryBody("avatar.jpg", param.file, "image/jpeg", "avatar"));
		return bodies;
	}

	@Override
	public List<NameValuePair> fillParams() {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("uid", ""+param.uid));
		pairs.add(new BasicNameValuePair("token", ""+param.token));
		return pairs;
	}

}
