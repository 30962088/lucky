package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.text.TextUtils;

public class UserRankMe extends Request{

	public static class Params{
		protected Integer uid;
		protected String token;
		public Params(Integer uid, String token) {
			super();
			this.uid = uid;
			this.token = token;
		}
		
	}
	
	private Params params;
	
	
	
	public UserRankMe(Params params) {
		super();
		this.params = params;
	}
	
	private int rank = -1;

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			rank = Integer.parseInt(data);
		}
		
	}
	
	public int getRank() {
		return rank;
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
		return HOST+"user/rank/coin/me/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
