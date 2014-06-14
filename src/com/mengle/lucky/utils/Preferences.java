package com.mengle.lucky.utils;

import com.mengle.lucky.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

public class Preferences {

	
	public static class User{
		
		private static final String NAME = "User";
		
		private SharedPreferences preferences;

		private Context context;
		
		public User(Context context) {
			preferences = context.getSharedPreferences(NAME, 0);
			this.context = context;
		}
		
		public void setToken(String val){
			preferences.edit().putString("token", val).commit();
		}
		
		public String getToken(){
			return preferences.getString("token",null);
		}
		
		public boolean isLogin(){
			return getToken() != null && getUid() != null;
		}
		
		public void setUid(int val){
			preferences.edit().putString("uid", ""+val).commit();
		}
		
		public Integer getUid(){
			String val = preferences.getString("uid",null);
			Integer uid = null;
			if(val != null){
				uid = Integer.parseInt(val);
			}
			return uid;
		}
		
		public int getLastShitiId(){
			return preferences.getInt("LastShitiId", 0);
		}
		
		public void setLastShitiId(int LastShitiId){
			preferences.edit().putInt("LastShitiId", LastShitiId).commit();
		}
		
		public boolean isFirstLogin() {
			
			return preferences.getBoolean("isFirstLogin", true);
			
		}
		
		public void setFirstLogin(boolean val){
			
			preferences.edit().putBoolean("isFirstLogin", val).commit();
			
		}
		
		public boolean isBuildProvince(){
			return preferences.getBoolean("isBuildProvince", false);
		}
		
		public void setBuildProvince(boolean val){
			preferences.edit().putBoolean("isBuildProvince", val).commit();
		}
		
		public void logout(){
			preferences.edit().remove("uid").remove("token").commit();
			UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login", RequestType.SOCIAL);
			
			for(SHARE_MEDIA media:new SHARE_MEDIA[]{SHARE_MEDIA.QQ,SHARE_MEDIA.SINA,SHARE_MEDIA.RENREN,SHARE_MEDIA.TENCENT}){
				mController.deleteOauth(context, media, null);
			}
			
			
		}
		
	}
	
	public static class Push {
		private static final String NAME = "Push";
		
		private SharedPreferences preferences;

		public Push(Context context) {
			preferences = context.getSharedPreferences(NAME, 0);
		}
		
		public boolean isLogout() {
			
			return preferences.getBoolean("logout", false);
		}

		public void setLogout(boolean logout) {
			
			preferences.edit().putBoolean("logout", logout).commit();
		}

		public boolean isResult() {
			
			return preferences.getBoolean("result", true);
		}

		public void setResult(boolean result) {
			
			preferences.edit().putBoolean("result", result).commit();
		}

		public boolean isAnnouncement() {
			return preferences.getBoolean("announcement", true);
		}

		public void setAnnouncement(boolean announcement) {
			
			preferences.edit().putBoolean("announcement", announcement).commit();
		}

		public boolean isMsg() {
			return preferences.getBoolean("msg", true);
		}

		public void setMsg(boolean msg) {
			
			preferences.edit().putBoolean("msg", msg).commit();
		}
		
		public void setUserId(String userId){
			preferences.edit().putString("userId", userId).commit();
		}
		
		public String getUserId(){
			return preferences.getString("userId", null);
		}
		
		public void setChannelId(String channelId){
			preferences.edit().putString("channelId", channelId).commit();
		}
		
		public String getChanelId(){
			return preferences.getString("channelId", null);
		}

	}
	
	public static class Network {
		
		private static final String NAME = "network";
		
		public static final int TYPE_MOST = 1;
		
		public static final int TYPE_LEAST = 2;
		
		public static final int TYPE_NONE = 0;
		
		
		private SharedPreferences preferences;
		
		private Context context;

		public Network(Context context) {
			preferences = context.getSharedPreferences(NAME, 0);
			this.context = context;
		}

		public void setType(int type) {
			preferences.edit().putInt("type", type).commit();
			
		}
		
		public int getType(){
			return preferences.getInt("type", TYPE_MOST);
		}
		
		public String getName(){
			String name = "";
			int type = getType();
			Resources resources = context.getResources();
			switch (type) {
			case TYPE_MOST:
				name = resources.getString(R.string.network_most);
				break;
			case TYPE_LEAST:
				name = resources.getString(R.string.network_least);
				break;
			case TYPE_NONE:
				name = resources.getString(R.string.network_none);
				break;
			
			}
			return name;
		}

	}

}
