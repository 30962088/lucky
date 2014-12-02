package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.network.model.GameLite;

public class UserGamingRequest extends Request{

	public static class Params{
		protected Integer uid;
		protected String token;
		protected int start;
		protected int limit;
		protected int vuid;
		public Params(Integer uid, String token,int vuid, int start, int limit) {
			super();
			this.uid = uid;
			this.token = token;
			this.vuid = vuid;
			this.start = start;
			this.limit = limit;
		}
		
	}
	
	private Params params;
	
	private List<GameLite> gameLite = new ArrayList<GameLite>();;
	
	public List<GameLite> getGameLite() {
		return gameLite;
	}
	
	
	public UserGamingRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			gameLite = new Gson().fromJson(data, new TypeToken<List<GameLite>>(){}.getType());
		}
		
		
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
		
		return HOST+"user/game/ings/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
