package com.mengle.lucky;


import com.mengle.lucky.network.Login;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class BuildAccountLoginActivity extends Activity implements
		OnClickListener {

	public static void open(Context context) {
		context.startActivity(new Intent(context,
				BuildAccountLoginActivity.class));
	}

	

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.bind_account_layout);
		

		findViewById(R.id.qq).setOnClickListener(this);
		findViewById(R.id.weibo).setOnClickListener(this);
		findViewById(R.id.tencent).setOnClickListener(this);
	}

	
	
	
	

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qq:
			Login.onQQOauth(context);
			break;

		case R.id.weibo:
			Login.onWeiboOauth(context);
			
			break;

		case R.id.tencent:
			Login.onTencentOauth(context);
			break;

		default:
			break;
		}

	}

	

}
