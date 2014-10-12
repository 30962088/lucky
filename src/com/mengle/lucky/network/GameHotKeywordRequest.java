package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GameHotKeywordRequest extends Request{

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
	
	private List<String> results = new ArrayList<String>();
	
	public GameHotKeywordRequest(Context context, Param param) {
		super(context);
		this.param = param;
	}
	
	public List<String> getResults() {
		return results;
	}

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			results = new Gson().fromJson(data, new TypeToken<List<String>>(){}.getType());
		}
		
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
		return HOST+"game/hotkeyword/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
