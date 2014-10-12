package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.network.UserRank.Result;

import android.content.Context;
import android.text.TextUtils;

public class CampaignsGetRequest extends Request{

	public static class Param{
		protected Integer uid;
		protected String token;
		public Param(Integer uid, String token) {
			super();
			this.uid = uid;
			this.token = token;
		}
		
	}
	
	public static class Result{
		private int id;
		private String image;
		private int gold_coin;
		private String url;
		private int width;
		private int height;
		public int getId() {
			return id;
		}
		public String getImage() {
			return image;
		}
		public int getGold_coin() {
			return gold_coin;
		}
		public String getUrl() {
			return url;
		}
		public int getWidth() {
			return width;
		}
		public int getHeight() {
			return height;
		}
		
	}
	
	private Param param;
	
	private List<Result> results;
	
	public List<Result> getResults() {
		return results;
	}
	
	
	public CampaignsGetRequest(Context context, Param param) {
		super(context);
		this.param = param;
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
		return HOST+"campaigns/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
