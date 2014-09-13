package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

public class TipGameGet extends Request{

	public static class Param{
		protected Integer uid;
		protected String token;
		public Param(Integer uid, String token) {
			super();
			this.uid = uid;
			this.token = token;
		}
		
	}
	
	private Param param;
	
	private int count;
	
	
	
	public TipGameGet(Param param) {
		super();
		this.param = param;
	}
	
	public int getCount() {
		return count;
	}
	

	@Override
	public void onSuccess(String data) {
		count = Integer.parseInt(data);
		
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
		return HOST+"tip/game/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
