package com.mengle.lucky.fragments;

import java.util.Map;

import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.ShitiActivity;
import com.mengle.lucky.network.GameLibraryDayChanceRequest;
import com.mengle.lucky.network.GameLibraryDayChanceRequest.Callback;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.Chance;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.AlertDialog;
import com.mengle.lucky.wiget.JiyunIntroDialog;
import com.mengle.lucky.wiget.LoadingPopup;
import com.mengle.lucky.wiget.ShareDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.media.UMImage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class JiyunIndexFragment extends Fragment implements OnClickListener{
	
	public static JiyunIndexFragment newInstance(){
		return new JiyunIndexFragment();
	}
	
	private Preferences.User user;
	
	private TextView chanceView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		user = new Preferences.User(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.jiyunbao_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.btn_share).setOnClickListener(this);
		chanceView = (TextView) view.findViewById(R.id.chance);
		view.findViewById(R.id.guize).setOnClickListener(this);
		view.findViewById(R.id.btn_play).setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(!user.isLogin()){
			chanceView.setText(""+0);
			AlertDialog.open(getActivity(), "您目前无法进入集运宝\n请登录后重试", null);
			return;
		}
		Chance.getChance(getActivity(), new Callback(){

			@Override
			public void onChanceCount(int count) {
				chanceView.setText(""+count);
				
			}
			
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guize:
			JiyunIntroDialog.open(getActivity());
			break;
		case R.id.btn_share:
			onShare();
			break;
		case R.id.btn_play:
			if(!user.isLogin()){
				AlertDialog.open(getActivity(), "您目前不能进入集运宝答题\n请登录后重试", null);
				return;
			}
			int chance = 0;
			try{
				chance = Integer.parseInt(chanceView.getText().toString());
			}catch (Exception e) {
				// TODO: handle exception
			}
			if(chance == 0){
				Utils.tip(getActivity(), "游戏次数不足");
				return;
			}
			ShitiActivity.open(getActivity());
			break;
		default:
			break;
		}
		
	}

	private void doShare(SHARE_MEDIA media){
		final UMSocialService mController =Utils.getUMSocialService(getActivity());
		View view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
		Bitmap bitmap = Utils.convertViewToBitmap(view);
		mController.setShareContent("谁能帮忙猜出这题？我家房子就是你的");
		mController.setShareImage(new UMImage(getActivity(), bitmap));
		
		mController.directShare(getActivity(),media,new SnsPostListener() {
			
			@Override
			public void onStart() {
				
				
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
				LoadingPopup.hide(getActivity());
				
			}
		});
	}
	
	private void onShare() {
		ShareDialog.open(getActivity(), "集运宝分享", new ShareDialog.Callback() {
			
			@Override
			public void onClick(final SHARE_MEDIA media,final String name) {
				final UMSocialService mController =Utils.getUMSocialService(getActivity());
				if(media == SHARE_MEDIA.WEIXIN || media ==SHARE_MEDIA.WEIXIN_CIRCLE){
					doShare(media);
					return;
				}
				
				LoadingPopup.show(getActivity());
				mController.checkTokenExpired(getActivity(), new SHARE_MEDIA[]{media}, new UMDataListener() {

					@Override
					public void onComplete(int arg0, Map<String, Object> arg1) {
						String key = arg1.keySet().toArray()[0].toString();
						if((Boolean)arg1.get(key)  == false){
							AlertDialog.open(getActivity(), "你的"+name+"账号还没有与本机绑定，请绑定后再分享", null);
							LoadingPopup.hide(getActivity());
						}else{
							doShare(media);
						}
						
						
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						
					}
					
				});
			}
		}, null);
		
	}

}
