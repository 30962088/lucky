package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.network.model.Game;

public class GameSearchRequest extends Request implements IGameGet{

	public static class Pamras{
		protected Integer uid;
		protected String token;
		protected String keyword;
		protected int start;
		protected int limit;
		public Pamras(Integer uid, String token, String keyword) {
			super();
			this.uid = uid;
			this.token = token;
			this.keyword = keyword;
		}
		
	}
	
	private Pamras pamras;
	
	private List<Result> list = new ArrayList<Result>();
	
	public GameSearchRequest(Context context, Pamras pamras) {
		super(context);
		this.pamras = pamras;
	}

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			list = new Gson().fromJson(data, new TypeToken<List<Result>>(){}.getType());
		}
	}
	
	public List<Result> getResult(){
		return list;
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
		return HOST+"game/search/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(pamras);
	}

	@Override
	public void request(int offset, int limit) {
		pamras.start = offset;
		pamras.limit = limit;
		request();
		
	}
}
