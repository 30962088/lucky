package com.mengle.lucky.network;

import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;

import com.mengle.lucky.network.model.Chance;

import android.content.Context;

public class GameLibraryDayChanceRequest extends Request{

	public static interface Callback{
		public void onChanceCount(int count);
	}
	
	
	
	public static class Params{
		protected int uid;
		protected String token;
		public Params(int uid, String token) {
			super();
			this.uid = uid;
			this.token = token;
		}
		
	}
	
	private Params params;
	
	private int change;
	
	public GameLibraryDayChanceRequest(Params params) {
		super();
		this.params = params;
	}

	public int getChange() {
		return change;
	}
	
	@Override
	public void onSuccess(String data) {
		change = Integer.parseInt(data);
		
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
		return HOST+"game/library/day/chance/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
