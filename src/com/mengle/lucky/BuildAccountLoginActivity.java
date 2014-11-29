package com.mengle.lucky;


import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.mengle.lucky.network.Login;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.Login.Params;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.OauthUtils;
import com.mengle.lucky.utils.OauthUtils.Callback;
import com.mengle.lucky.wiget.LoadingPopup;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class BuildAccountLoginActivity extends BaseActivity implements
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
		App.currentActivity = this;
		startPush();
		context = this;
		setContentView(R.layout.bind_account_layout);
		oauthUtils = new OauthUtils(context);
		oauthUtils.setCallback(this);
		findViewById(R.id.qq).setOnClickListener(this);
		findViewById(R.id.weibo).setOnClickListener(this);
		findViewById(R.id.tencent).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);
	}

	
	
	
	private void startPush() {
		
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                com.mengle.lucky.push.Utils.getMetaValue(this, "api_key"));
		
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.cancel:
			finish();
			break;
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
	public void finish() {
		super.finish();
		MainActivity.open(this);
	}





	@Override
	public void onSuccess(Params params) {
		final Login login = new Login(context, params);
		LoadingPopup.show(this);
		RequestAsync.request(login, new Async() {
			
			public void onPostExecute(Request request) {
				if(request.getStatus() == Request.Status.SUCCESS){
					finish();
				}
				
			}
		});
		
	}

	

}
