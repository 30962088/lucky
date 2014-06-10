package com.mengle.lucky.network;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.network.model.Msg;
import com.mengle.lucky.network.model.Notice;
import com.mengle.lucky.utils.Utils;

public class MsgGetRequest extends Request{

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
	
	
	
	public MsgGetRequest(Params params) {
		super();
		this.params = params;
	}
	
	private List<Msg> msgs = new ArrayList<Msg>();

	public void onSuccess(String data) {
		
		if(!TextUtils.isEmpty(data)){
			try {
				JSONArray array = new JSONArray(data);
				String[] ids = new String[array.length()];
				for(int i = 0;i<array.length();i++){
					JSONObject obj = array.getJSONObject(i);
					obj.put("sender", obj.getString("sender"));
					try {
						obj.put("send_time", ""+Utils.parseDate(obj.getString("send_time")).getTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ids[i] = obj.getString("id");
				}
				UserMeLetterReceiptRequest receiptRequest = new UserMeLetterReceiptRequest(
						new UserMeLetterReceiptRequest.Param(params.uid, params.token, ids));
				RequestAsync.request(receiptRequest, null);
				msgs = new Gson().fromJson(array.toString(), new TypeToken<List<Msg>>(){}.getType());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public List<Msg> getMsgList() {
		return msgs;
	}
	
	

	public void onError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	public void onResultError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	public void onServerError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return HOST+"me/letter/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
