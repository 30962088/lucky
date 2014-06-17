package com.mengle.lucky;

import com.mengle.lucky.utils.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LaunchAcitivty extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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

}
