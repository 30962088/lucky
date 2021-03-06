package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.google.gson.Gson;
import com.mengle.lucky.network.model.Game;

public class CaiRequest extends Request{

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
	
	private Game game;
	
	public CaiRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}
	
	public Game getGame() {
		return game;
	}

	public void onSuccess(String data) {
		game = new Gson().fromJson(data, Game.class);
		
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
		return HOST+"game/day/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
