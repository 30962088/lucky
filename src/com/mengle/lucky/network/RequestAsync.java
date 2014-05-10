package com.mengle.lucky.network;


import android.os.AsyncTask;

public class RequestAsync {

	public static interface Async{
		public void onPostExecute(Request request);
	}
	
	public static void request(final Request request,final Async async){
		new AsyncTask<Void, Void, Request>() {

			@Override
			protected Request doInBackground(Void... params) {
				request.request();
				return request;
			}
			
			@Override
			protected void onPostExecute(Request result) {
				async.onPostExecute(request);
			}
			
			
		}.execute();
		
	}
	
}
