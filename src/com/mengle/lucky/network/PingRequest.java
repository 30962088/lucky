package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.google.gson.Gson;
import com.mengle.lucky.network.model.Game;

public class PingRequest extends Request{

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
	
	
	public PingRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}
	

	public void onSuccess(String data) {
		
		
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
		return HOST+"user/ping/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
