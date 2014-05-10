package com.mengle.lucky.network;

public interface Response {


	
	public void onSuccess(String data);
	
	public void onError(int code,String msg);
	
	public void onResultError(int code,String msg);
	
	public void onServerError(int code,String msg);
	
}
