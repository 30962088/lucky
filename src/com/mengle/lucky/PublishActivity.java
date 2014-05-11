package com.mengle.lucky;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class PublishActivity extends BaseActivity{

	public static void open(Context context){
		context.startActivity(new Intent(context, PublishActivity.class));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_layout);
	}


}
