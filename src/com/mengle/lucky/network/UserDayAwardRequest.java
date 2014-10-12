package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mengle.lucky.network.model.User;

public class UserDayAwardRequest extends Request{

	public static class Param{
		protected Integer uid;
		protected String token;
		public Param(Integer uid, String token) {
			super();
			this.uid = uid;
			this.token = token;
		}
		
	}
	
	public static class Result extends User{
		protected int award_gold_coin;
		public int getAward_gold_coin() {
			return award_gold_coin;
		}
	}
	
	private Param param;
	
	private Result result;
	
	public UserDayAwardRequest(Context context, Param param) {
		super(context);
		this.param = param;
	}
	
	

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			result = new Gson().fromJson(data, Result.class);
		}
		
	}
	
	public Result getResult() {
		return result;
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
		return HOST+"user/day/award/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
