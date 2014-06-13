package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import com.mengle.lucky.App;
import com.mengle.lucky.utils.Utils;

public class UserLogoutRequest extends Request{

	public static class Param{
		protected int uid;
		protected String token;
		public Param(int uid, String token) {
			super();
			this.uid = uid;
			this.token = token;
		}
		
	}
	
	private Param param;
	
	
	
	public UserLogoutRequest(Param param) {
		super();
		this.param = param;
	}

	@Override
	public void onSuccess(String data) {
		Utils.tip(App.getInstance(), data);
		
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
		return HOST+"user/logout/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
