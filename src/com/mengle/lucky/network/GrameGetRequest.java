package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.network.UserRank.Result;
import com.mengle.lucky.network.model.Game;

public class GrameGetRequest extends Request{

	public static class Params{
		protected Integer uid;
		protected String token;
		protected int game_id;
		public Params(Integer uid, String token, int game_id) {
			super();
			this.uid = uid;
			this.token = token;
			this.game_id = game_id;
		}
		
	}
	
	
	
	
	
	private Result result;
	
	public static class Result extends Game{
		public static class Join{
			private int option_id;
			private long gold_coin;
			private int state;
			private String apply_time;
			public int getOption_id() {
				return option_id;
			}
			public long getGold_coin() {
				return gold_coin;
			}
			public int getState() {
				return state;
			}
			public String getApply_time() {
				return apply_time;
			}
			
		}
		protected int option_id;
		protected String apply_time;
		protected Join join;
		public int getOption_id() {
			return option_id;
		}
		
		public Join getJoin() {
			return join;
		}
		
		public int getState() {
			return state;
		}
		public String getApply_time() {
			return apply_time;
		}
		
	}
	
	private Params params;
	
	
	
	public GrameGetRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			try {
				JSONObject object = new JSONObject(data);
				
				if(object.has("join") && TextUtils.isEmpty(object.getString("join"))){
					object.remove("join");
				}
				result = new Gson().fromJson(object.toString(), Result.class);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
