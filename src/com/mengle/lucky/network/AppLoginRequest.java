package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mengle.lucky.network.model.User;

public class AppLoginRequest extends Request{

	public static class Params{
		protected Integer uid;
		protected String channel;
		protected String os;
		protected String os_version;
		protected String platform;
		protected String lng;
		protected String lat;
		protected String device;
		protected String version;
		protected int is_new;
		public Params(Integer uid, String channel, String lng, String lat,String version, int is_new) {
			super();
			this.uid = uid;
			this.channel = channel;
			this.os = "android";
			this.os_version = ""+android.os.Build.VERSION.SDK_INT;
			this.platform = android.os.Build.MANUFACTURER;
			this.lng = lng;
			this.lat = lat;
			this.device = android.os.Build.MODEL;
			this.version = version;
			this.is_new = is_new;
		}
		
		
		
	}
	
	private Params params;
	
	
	public AppLoginRequest(Context context, Params params) {
		super(context);
		this.params = params;
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
		return HOST+"app/login/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}



	@Override
	public void onSuccess(String data) {
		// TODO Auto-generated method stub
		
	}

}
