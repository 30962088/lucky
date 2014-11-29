package com.mengle.lucky;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.mengle.lucky.utils.Preferences;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LaunchAcitivty extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		startPush();
		MobclickAgent.updateOnlineConfig(this);
		MobclickAgent.setDebugMode( true );
		Preferences.User user = new Preferences.User(this);
		Intent intent = null;
		if(user.isFirstLogin()){
			intent = new Intent(this, GuideActivity.class);
			user.setFirstLogin(false);
		}else{
			intent = new Intent(this, MainActivity.class);
		}
		startActivity(intent);
		finish();
		
	}
	

	/*private void startPush() {
		
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                com.mengle.lucky.push.Utils.getMetaValue(this, "api_key"));
		
	}
*/
}
