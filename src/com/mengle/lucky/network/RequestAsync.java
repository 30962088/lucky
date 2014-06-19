package com.mengle.lucky.network;


import com.mengle.lucky.App;

import android.os.Handler;

public class RequestAsync {

	public static interface Async{
		public void onPostExecute(Request request);
	}
	
	public static void request(final IRequest request,final Async async){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				request.request();
				if(async != null){
					new Handler(App.getInstance().getMainLooper()).post(new Runnable() {
						public void run() {
							async.onPostExecute((Request) request);
						}
					});
				}
			}
		}).start();
		/*new AsyncTask<Void, Void, Request>() {

			@Override
			protected Request doInBackground(Void... params) {
				request.request();
				return (Request) request;
			}
			
			@Override
			protected void onPostExecute(Request result) {
				if(async != null){
					async.onPostExecute((Request) request);
				}
				
			}
			
			
		}.execute();
*/		
	}
	
}
