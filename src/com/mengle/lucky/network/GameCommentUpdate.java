package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.mengle.lucky.network.GameCommentRequest.Result;

public class GameCommentUpdate extends Request{

	public static class Params{
		protected int uid;
		protected String token;
		protected int game_id;
		protected Integer reply_uid;
		protected String content;
		public Params(int uid, String token, int game_id, Integer reply_uid,
				String content) {
			super();
			this.uid = uid;
			this.token = token;
			this.game_id = game_id;
			this.reply_uid = reply_uid;
			this.content = content;
		}
		
	}
	
	private Params params;
	
	private Result result;
	
	public GameCommentUpdate(Params params) {
		super();
		this.params = params;
	}

	@Override
	public void onSuccess(String data) {
		result =  new Gson().fromJson(data, Result.class);
		
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
		return HOST+"game/comment/update/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
