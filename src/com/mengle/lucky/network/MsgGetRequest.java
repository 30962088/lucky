package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.network.model.Msg;
import com.mengle.lucky.network.model.Notice;

public class MsgGetRequest extends Request{

	public static class Params{
		protected int uid;
		protected String token;
		public Params(int uid, String token) {
			super();
			this.uid = uid;
			this.token = token;
		}
		
	}
	
	private Params params;
	
	
	
	public MsgGetRequest(Params params) {
		super();
		this.params = params;
	}
	
	private List<Msg> msgs = new ArrayList<Msg>();

	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			msgs = new Gson().fromJson(data, new TypeToken<List<Notice>>(){}.getType());
		}
		
	}
	
	public List<Msg> getMsgList() {
		return msgs;
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
		return HOST+"me/letter/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
