package com.mengle.lucky.network;

import java.util.List;

import org.apache.http.NameValuePair;

import com.mengle.lucky.App;
import com.mengle.lucky.utils.Utils;

public class GameComplainRequest extends Request{

	public static class Param{
		protected Integer uid;
		protected String token;
		protected int game_id;
		public Param(Integer uid, String token, int game_id) {
			super();
			this.uid = uid;
			this.token = token;
			this.game_id = game_id;
		}
		
	}
	
	private Param param;
	
	
	
	public GameComplainRequest(Param param) {
		super();
		this.param = param;
	}

	@Override
	public void onSuccess(String data) {
		Utils.tip(App.getInstance(), data);
		
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
		return HOST+"complain/game/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
