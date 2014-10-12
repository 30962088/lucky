package com.mengle.lucky.network;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;

import android.content.Context;

public class UserMeLetterReceiptRequest extends Request{

	public static class Param{
		protected int uid;
		protected String token;
		protected String ids;
		public Param(int uid, String token, String[] ids) {
			super();
			this.uid = uid;
			this.token = token;
			this.ids = StringUtils.join(ids, ',') ;
		}
	}
	
	private Param param;
	
	
	
	public UserMeLetterReceiptRequest(Context context, Param param) {
		super(context);
		this.param = param;
	}

	@Override
	public void onSuccess(String data) {
		// TODO Auto-generated method stub
		
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
		return HOST+"me/letter/receipt/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}
	
	

}
