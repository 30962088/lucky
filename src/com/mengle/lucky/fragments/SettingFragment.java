package com.mengle.lucky.fragments;

import com.mengle.lucky.R;
import com.mengle.lucky.network.IUserGet.UserResult;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.UserMe;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.CacheManager;
import com.mengle.lucky.utils.CacheManager.OnClearCacheListner;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.Network;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.AlertDialog;
import com.mengle.lucky.wiget.ConfirmDialog;
import com.mengle.lucky.wiget.IntroDialog;
import com.mengle.lucky.wiget.ConfirmDialog.OnConfirmClick;
import com.mengle.lucky.wiget.NetworkDialog;
import com.mengle.lucky.wiget.NetworkDialog.OnNetworkClick;
import com.mengle.lucky.wiget.PushDialog;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingFragment extends Fragment implements OnClickListener{
	
	private View root;
	
	private TextView networkType;
	
	private TextView cacheSizeView;
	
	private TextView versionView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.sidemenu_setting_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		root = view;
		versionView = (TextView) view.findViewById(R.id.version);
		cacheSizeView = (TextView) view.findViewById(R.id.cache_size);
		view.findViewById(R.id.checkNewVersion).setOnClickListener(this);
		view.findViewById(R.id.pushManager).setOnClickListener(this);
		view.findViewById(R.id.clearCache).setOnClickListener(this);
		view.findViewById(R.id.btn_intro).setOnClickListener(this);
		view.findViewById(R.id.changeNetwork).setOnClickListener(this);
		networkType = (TextView) view.findViewById(R.id.networkType);
		networkType.setText(new Preferences.Network(getActivity()).getName());
		
		
		
		try {
			PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
			versionView.setText(pInfo.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void pushManager(){
		if(!new Preferences.User(getActivity()).isLogin()){
			AlertDialog.open(getActivity(), "推送通知登录后才能设置", root);
			return;
		}
		boolean letter = true;
		boolean notice = true;
		if(user != null){
			letter = user.getPush_letter()==1?true:false;
			notice = user.getPush_notice()==1?true:false;
		}
		PushDialog.open(getActivity(), root,letter,notice);
	}
	
	private void checkNewVersion(){
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			
			@Override
			public void onUpdateReturned(int updateStatus, UpdateResponse response) {
				if(updateStatus == UpdateStatus.No){
					AlertDialog.open(getActivity(), "当前是最新版本",root);
				}else if(updateStatus == UpdateStatus.Yes){
					UmengUpdateAgent.showUpdateDialog(getActivity(), response);
				}
				
			}
		});
		UmengUpdateAgent.update(getActivity());		
	}
	
	private void clearCache(){
		ConfirmDialog.open(getActivity(), "会清除所有缓存", new OnConfirmClick() {
			
			public void onConfirm() {
				CacheManager.clearCache(getActivity(), new OnClearCacheListner() {
					
					@Override
					public void onclearSuccess() {
						getCacheSize();
						
					}
				});
				
			}
			
			public void onCancel() {
				System.out.println("cancel");
				
			}
		}, root);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		login();
		getCacheSize();
	}
	
	private UserResult user;
	
	private void login(){
		Preferences.User user = new Preferences.User(getActivity());
		if(user.isLogin()){
			final UserMe userMe = new UserMe(getActivity(), new UserMe.Params(user.getUid(),
					user.getToken()));
			RequestAsync.request(userMe, new Async() {

				public void onPostExecute(Request request) {
					if (userMe.getStatus() == Request.Status.SUCCESS) {
						SettingFragment.this.user = userMe.getUserResult();
					}

				}
			});
		}
		
	}
	
	private void getCacheSize() {
		
		new AsyncTask<Context, Void, Long>() {

			@Override
			protected Long doInBackground(Context... params) {
				// TODO Auto-generated method stub
				return CacheManager.folderSize(params[0].getCacheDir())
						+ CacheManager.folderSize(params[0]
								.getExternalCacheDir());
			}

			@Override
			protected void onPostExecute(Long result) {
				cacheSizeView.setText(Math.round(result / 1024 / 1024 * 100)
						/ 100.0 + "M");
			}

		}.execute(getActivity());
	}
	
	private void changeNetwork(){
		NetworkDialog.open(getActivity(), new OnNetworkClick() {
			
			public void onResult(int type) {
				Network network = new Preferences.Network(getActivity());
				network.setType(type);
				networkType.setText(network.getName());
				
			}
		}, root);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.checkNewVersion:
			checkNewVersion();
			break;
		case R.id.pushManager:
			pushManager();
			break;
		case R.id.clearCache:
			clearCache();
			break;
		case R.id.changeNetwork:
			changeNetwork();
			break;
		case R.id.btn_intro:
			IntroDialog.open(getActivity(), "使用帮助",getResources().getString(R.string.app_intro), root);
			break;
		default:
			break;
		}
		
	}

}
