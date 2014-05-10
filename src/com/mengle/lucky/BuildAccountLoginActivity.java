package com.mengle.lucky;

import org.json.JSONException;
import org.json.JSONObject;

import com.mengle.lucky.network.Login;
import com.mengle.lucky.network.Login.OnLoginListener;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.Login.Params;
import com.mengle.lucky.network.Login.Result;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.ShareAccount;
import com.mengle.lucky.utils.ShareUtils;
import com.mengle.lucky.utils.ShareAccount.SINAToken;
import com.mengle.lucky.utils.ShareUtils.OperateListener;
import com.tencent.connect.auth.QQAuth;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

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

	private Tencent mTencent;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.bind_account_layout);
		mTencent = Tencent.createInstance("101084337", this);

		findViewById(R.id.qq).setOnClickListener(this);
		findViewById(R.id.weibo).setOnClickListener(this);
		findViewById(R.id.tencent).setOnClickListener(this);
	}

	public static QQAuth mQQAuth;

	private void onQQLogin() {

		mTencent.login(this, "get_simple_userinfo", new IUiListener() {

			public void onError(UiError arg0) {
				// TODO Auto-generated method stub

			}

			public void onComplete(Object arg0) {
				// TODO Auto-generated method stub

			}

			public void onCancel() {
				// TODO Auto-generated method stub

			}
		});

	}

	private void onTencentLogin() {
		// TODO Auto-generated method stub

	}
	
	
	

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qq:
			onQQLogin();
			break;

		case R.id.weibo:
			Login.onWeiboLogin(context,new OnLoginListener() {
				
				public void onLoginSuccess(Result result) {
					finish();
					
				}
				
				public void onLoginError(String msg) {
					// TODO Auto-generated method stub
					
				}
			});
			break;

		case R.id.tencent:
			onTencentLogin();
			break;

		default:
			break;
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mTencent.onActivityResult(requestCode, resultCode, data);
	}

}
