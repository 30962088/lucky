package com.mengle.lucky;



import com.mengle.lucky.network.PingRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.PingRequest.Params;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.Preferences.User;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;




public abstract class BaseActivity extends FragmentActivity{


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		User user = new User(this);
		if(user.isLogin()){
			PingRequest request = new PingRequest(this, new Params(user.getUid(), user.getToken()));
			RequestAsync.request(request, new Async() {
				
				@Override
				public void onPostExecute(Request request) {
					if(request.getStatus() == Status.ERROR){
						Intent intent = new Intent(BaseActivity.this, MainActivity.class);
						intent.setAction(MainActivity.ACTION_LOGOUT);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
						BaseActivity.this.startActivity(intent);
					}
					
				}
			});
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	
	
	
	
}
