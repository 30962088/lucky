package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.mengle.lucky.App;
import com.mengle.lucky.utils.Utils;

public class GameBetRequest extends Request{

	public static class Param{
		protected int uid;
		protected String token;
		protected int game_id;
		protected int opt_id;
		protected int coin;
		public Param(int uid, String token, int game_id, int opt_id, int coin) {
			super();
			this.uid = uid;
			this.token = token;
			this.game_id = game_id;
			this.opt_id = opt_id;
			this.coin = coin;
		}
		
	}
	
	private Param param;
	
	
	
	public GameBetRequest(Context context, Param param) {
		super(context);
		this.param = param;
	}

	@Override
	public void onSuccess(String data) {
//		Utils.tip(App.getInstance(), data);
		
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
		return HOST+"game/bet/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
