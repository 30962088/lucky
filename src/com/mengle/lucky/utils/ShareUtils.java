package com.mengle.lucky.utils;

import java.io.ByteArrayOutputStream;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;

import android.os.Bundle;
import android.text.TextUtils;


import com.mengle.lucky.utils.ShareAccount.SINAToken;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import com.sina.weibo.sdk.openapi.legacy.UsersAPI;

public class ShareUtils {

	public static interface OperateListener<T> {
		public void onSuccess(T t);

		public void onError(String msg);
	}

	public static class Weibo {

		private WeiboAuth mWeiboAuth;

		private Context context;

		public Weibo(Context context) {
			this.context = context;
			mWeiboAuth = new WeiboAuth(context, Constant.WEIBO_APP_KEY,
					Constant.WEIBO_REDIRECT_URL, Constant.SCOPE);
		}

		public void getUser(SINAToken token,
				final OperateListener<JSONObject> operateListener) {
			Oauth2AccessToken accessToken = token.toOauth2AccessToken();
			UsersAPI api = new UsersAPI(accessToken);
			api.show(Long.parseLong(accessToken.getUid()),
					new RequestListener() {

						public void onIOException(IOException e) {
							Utils.tip(context, e.getMessage());
							if (operateListener != null) {
								operateListener.onError(e.getMessage());
							}
						}

						public void onError(WeiboException e) {
							Utils.tip(context, e.getMessage());
							if (operateListener != null) {
								operateListener.onError(e.getMessage());
							}

						}

						public void onComplete4binary(
								ByteArrayOutputStream responseOS) {
							// TODO Auto-generated method stub

						}

						public void onComplete(String response) {
							if (operateListener != null) {
								try {
									operateListener.onSuccess(new JSONObject(
											response));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
					});
		}

		public void login(final OperateListener<SINAToken> onLoginListener) {
			mWeiboAuth.anthorize(new WeiboAuthListener() {

				public void onWeiboException(WeiboException arg0) {
					if (onLoginListener != null) {
						onLoginListener.onError(arg0.getMessage());
					}

				}

				public void onComplete(Bundle values) {
					// 从 Bundle 中解析 Token
					Oauth2AccessToken mAccessToken = Oauth2AccessToken
							.parseAccessToken(values);
					if (mAccessToken.isSessionValid()) {
						SINAToken token = new SINAToken();
						token.accessToken = values.getString("access_token");
						token.expires_in = values.getString("expires_in");
						token.remind_in = values.getString("remind_in");
						token.uid = values.getString("uid");
						ShareAccount account = ShareAccount.read();
						if (account == null) {
							account = new ShareAccount();

						}
						account.weibo = token;
						ShareAccount.save(account);
						onLoginListener.onSuccess(token);

					} else {
						// 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
						String code = values.getString("code");
						String message = "签名失败";
						if (!TextUtils.isEmpty(code)) {
							message = message + "\nObtained the code: " + code;
						}
						Utils.tip(context, message);
					}

				}

				public void onCancel() {
					// TODO Auto-generated method stub

				}
			});
		}

	}

}
