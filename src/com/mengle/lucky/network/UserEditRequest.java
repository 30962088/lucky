package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.mengle.lucky.network.model.User;

public class UserEditRequest extends Request{

	public static class Params{
		protected int uid;
		protected String token;
		protected String nickname;
		protected Integer gender;
		protected int province=-1;
		protected int city = -1;
		protected String qq;
		protected String mobile;
		public Params(int uid, String token, String nickname, Integer gender,
				int province, int city, String qq, String mobile) {
			super();
			this.uid = uid;
			this.token = token;
			this.nickname = nickname;
			this.gender = gender;
			this.province = province;
			this.city = city;
			this.qq = qq;
			this.mobile = mobile;
		}
		
	}
	
	private Params params;
	
	private User user;
	
	public UserEditRequest(Params params) {
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
		return HOST+"me/edit/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
