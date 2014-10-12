package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.google.gson.Gson;

public class GamePraiseRequest extends Request{

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
	
	private Params params;
	
	public static class Result{
		private int game_id;
		private int praise;
		public Result(int game_id, int praise) {
			super();
			this.game_id = game_id;
			this.praise = praise;
		}
		public int getGame_id() {
			return game_id;
		}
		public int getPraise() {
			return praise;
		}
		
	}
	
	private Result result;
	
	public GamePraiseRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public void onSuccess(String data) {
		result = new Gson().fromJson(data, Result.class);
		
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
		return HOST+"game/praise/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
