package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.google.gson.Gson;
import com.mengle.lucky.wiget.ShitiOverView.Model;

public class GameLibrarySubmitRequest extends Request{

	
	
	public static class Param{
		
		public static class Log{
			protected int id;
			protected int u;
			protected int c;
			protected int coin = 1;
			public Log(int id, int u, int c) {
				super();
				this.id = id;
				this.u = u;
				this.c = c;
			}
			
		}
		
		protected int uid;
		protected String token;
		protected String log;
		public Param(int uid, String token, List<Log> logs) {
			super();
			this.uid = uid;
			this.token = token;
			this.log = new Gson().toJson(logs);
		}
		
		
		
	}
	
	public static class Result{
		private int uid;
		private int gold_coin;
		private int win_coin;
		private int reward_coin;
		private int star;
		public int getUid() {
			return uid;
		}
		public int getGold_coin() {
			return gold_coin;
		}
		public int getWin_coin() {
			return win_coin;
		}
		public int getReward_coin() {
			return reward_coin;
		}
		public int getStar() {
			return star;
		}
		public  Model toModel(){
			return new Model( reward_coin,win_coin,gold_coin, star);
		}
	}
	
	private Param param;
	
	private Result result;
	
	
	
	public GameLibrarySubmitRequest(Context context, Param param) {
		super(context);
		this.param = param;
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
		return HOST+"game/library/submit/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
