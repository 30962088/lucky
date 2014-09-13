package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.network.IUserListRequest.Result;

import android.text.TextUtils;

public class UserMeFollowingsRequest extends Request implements IUserListRequest{

	public static class Params{
		protected int uid;
		protected String token;
		protected int start;
		protected int limit;
		public Params(int uid, String token, int start, int limit) {
			super();
			this.uid = uid;
			this.token = token;
			this.start = start;
			this.limit = limit;
		}
		
	}
	
	private Params params;
	
	private List<Result> results = new ArrayList<IUserListRequest.Result>();
	
	public UserMeFollowingsRequest(Params params) {
		super();
		this.params = params;
	}

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			results = new Gson().fromJson(data, new TypeToken<List<Result>>(){}.getType());
			for(Result result : results){
				for(TipGameGets.Result rs : results2){
					if(result.getUid() == rs.getUid()){
						result.setHasNewGame(true);
					}else{
						result.setHasNewGame(false);
					}
				}
			}
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
	private List<TipGameGets.Result> results2;
	@Override
	public void request() {
		if(params.start == 0){
			TipGameGets gameGets = new TipGameGets(new TipGameGets.Param(params.uid,params.token));
			gameGets.request();
			results2 = gameGets.getResults();
		}
		
		super.request();
	}

	@Override
	public void onServerError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return HOST+"me/followings/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

	@Override
	public List<Result> getResults() {
		// TODO Auto-generated method stub
		return results;
	}

}
