package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.adapter.AicaiListAdapter.Aicai;
import com.mengle.lucky.network.IGameGet.Result;

public class UserRank extends Request{

	public static class Params{
		protected Integer uid;
		protected String token;
		protected int start;
		protected int limit;
		public Params(Integer uid, String token, int start, int limit) {
			super();
			this.uid = uid;
			this.token = token;
			this.start = start;
			this.limit = limit;
		}
		
	}
	
	public static class Result{
		private int uid;
		private String nickname;
		private String avatar;
		private int gold_coin;
		public Result(int uid, String nickname, String avatar, int gold_coin) {
			super();
			this.uid = uid;
			this.nickname = nickname;
			this.avatar = avatar;
			this.gold_coin = gold_coin;
		}
		
		public Aicai toAicai(){
			return new Aicai(uid, nickname, nickname, gold_coin);
		}
		public static List<Aicai> toAicaiList(List<Result> list){
			List<Aicai> aicais = new ArrayList<Aicai>();
			for(Result result : list){
				aicais.add(result.toAicai());
			}
			return aicais;
		}
		
	}
	
	private Params params;
	
	private List<Result> results = new ArrayList<UserRank.Result>();
	
	public UserRank(Params params) {
		super();
		this.params = params;
	}
	
	public List<Result> getResults() {
		return results;
	}

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			results = new Gson().fromJson(data, new TypeToken<List<Result>>(){}.getType());
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
		return HOST+"user/rank/coin/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
