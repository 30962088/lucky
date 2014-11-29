package com.mengle.lucky;

import com.mengle.lucky.fragments.AwardFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class AwardActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		App.currentActivity = this;
		setContentView(R.layout.fragment_layout);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new AwardFragment()).commit();
	}
	
}
