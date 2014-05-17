package com.mengle.lucky;


import com.mengle.lucky.network.Login;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.Login.Params;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.OauthUtils;
import com.mengle.lucky.utils.OauthUtils.Callback;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class BuildAccountLoginActivity extends Activity implements
		OnClickListener,Callback {

	public static void open(Context context) {
		context.startActivity(new Intent(context,
				BuildAccountLoginActivity.class));
	}

	

	private Context context;
	
	private OauthUtils oauthUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.bind_account_layout);
		oauthUtils = new OauthUtils(context);
		oauthUtils.setCallback(this);
		findViewById(R.id.qq).setOnClickListener(this);
		findViewById(R.id.weibo).setOnClickListener(this);
		findViewById(R.id.tencent).setOnClickListener(this);
	}

	
	
	
	

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.qq:
			oauthUtils.qqOauth();
			break;

		case R.id.weibo:
			
			oauthUtils.sinaOauth();
			
			break;

		case R.id.tencent:
			oauthUtils.tencentOauth();
//			Login.onTencentOauth(context);
			break;

		default:
			break;
		}

	}






	@Override
	public void onSuccess(Params params) {
		final Login login = new Login(context, params);
		RequestAsync.request(login, new Async() {
			
			public void onPostExecute(Request request) {
				if(request.getStatus() == Request.Status.SUCCESS){
					finish();
				}
				
			}
		});
		
	}

	

}
