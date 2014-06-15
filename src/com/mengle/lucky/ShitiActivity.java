package com.mengle.lucky;

import com.mengle.lucky.fragments.ShitiFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class ShitiActivity extends FragmentActivity{
	
	public static void open(Context context){
		Intent intent = new Intent(context, ShitiActivity.class);
		context.startActivity(intent);
	}
	
	public void switchContent(Fragment fragment){
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shiti_activity_layout);
		findViewById(R.id.leftnav).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		
		switchContent( new ShitiFragment());
	}
	
	
}
