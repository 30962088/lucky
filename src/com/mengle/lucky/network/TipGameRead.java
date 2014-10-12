package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

public class TipGameRead extends Request{

	public static class Param{
		protected int uid;
		protected String token;
		protected int vuid;
		public Param(int uid, String token, int vuid) {
			super();
			this.uid = uid;
			this.token = token;
			this.vuid = vuid;
		}
		
		
	}
	
	private Param param;
	
	
	
	
	public TipGameRead(Context context, Param param) {
		super(context);
		this.param = param;
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
		return HOST+"tip/game/read/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
