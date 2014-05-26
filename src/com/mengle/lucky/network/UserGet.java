package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.mengle.lucky.network.model.User;

public class UserGet extends Request implements IUserGet{

	public static class Params{
		protected int uid;
		protected String token;
		protected int vuid;
		public Params(int uid, String token, int vuid) {
			super();
			this.uid = uid;
			this.token = token;
			this.vuid = vuid;
		}
		
		
	}
	
	private Params params;
	
	private UserResult user;
	
	public UserGet(Params params) {
		super();
		this.params = params;
	}

	public void onSuccess(String data) {
		try {
			JSONObject jsonObject = new JSONObject(data);
			if(TextUtils.isEmpty(jsonObject.getString("sns"))){
				jsonObject.put("sns", null);
			}
			user = new Gson().fromJson(jsonObject.toString(), UserResult.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
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
		return HOST+"user/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
