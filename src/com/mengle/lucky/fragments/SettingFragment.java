package com.mengle.lucky.fragments;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.CacheManager;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.Network;
import com.mengle.lucky.wiget.AlertDialog;
import com.mengle.lucky.wiget.ConfirmDialog;
import com.mengle.lucky.wiget.ConfirmDialog.OnConfirmClick;
import com.mengle.lucky.wiget.NetworkDialog;
import com.mengle.lucky.wiget.NetworkDialog.OnNetworkClick;
import com.mengle.lucky.wiget.PushDialog;

import android.content.Context;
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
		cacheSizeView = (TextView) view.findViewById(R.id.cache_size);
		view.findViewById(R.id.checkNewVersion).setOnClickListener(this);
		view.findViewById(R.id.pushManager).setOnClickListener(this);
		view.findViewById(R.id.clearCache).setOnClickListener(this);
		view.findViewById(R.id.changeNetwork).setOnClickListener(this);
		networkType = (TextView) view.findViewById(R.id.networkType);
		networkType.setText(new Preferences.Network(getActivity()).getName());
	}
	
	private void pushManager(){
		PushDialog.open(getActivity(), root);
	}
	
	private void checkNewVersion(){
		AlertDialog.open(getActivity(), "当前是最新版本",root);
	}
	
	private void clearCache(){
		ConfirmDialog.open(getActivity(), "会清除所有缓存", new OnConfirmClick() {
			
			public void onConfirm() {
				System.out.println("confirm");
				
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
		getCacheSize();
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
		default:
			break;
		}
		
	}

}
