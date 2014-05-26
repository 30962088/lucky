package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.network.UserRank.Result;
import com.mengle.lucky.network.model.Game;

public class GrameGetRequest extends Request{

	public static class Params{
		protected int uid;
		protected String token;
		protected int game_id;
		public Params(int uid, String token, int game_id) {
			super();
			this.uid = uid;
			this.token = token;
			this.game_id = game_id;
		}
		
	}
	
	
	
	private Result result;
	
	public static class Result extends Game{
		protected int option_id;
		protected int state;
		protected String apply_time;
		public int getOption_id() {
			return option_id;
		}
		
		public int getState() {
			return state;
		}
		public String getApply_time() {
			return apply_time;
		}
		
	}
	
	private Params params;
	
	
	
	public GrameGetRequest(Params params) {
		super();
		this.params = params;
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
		return HOST+"game/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
