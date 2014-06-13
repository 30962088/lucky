package com.mengle.lucky.fragments;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.network.Login.Params;
import com.mengle.lucky.network.Login;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserMe;

import com.mengle.lucky.utils.OauthUtils;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.OauthUtils.Callback;
import com.mengle.lucky.wiget.UserHeadView;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class NavFragment extends Fragment implements OnClickListener,Callback {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.sidemenu_nav_layout, null);
	}

	private View notLoginView;

	private UserHeadView headView;
	
	private OauthUtils oauthUtils;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		notLoginView = view.findViewById(R.id.not_login);

		headView = (UserHeadView) view.findViewById(R.id.userhead);

		oauthUtils = new OauthUtils(getActivity());
		
		oauthUtils.setCallback(this);
		
		
		view.findViewById(R.id.cai).setOnClickListener(this);

		view.findViewById(R.id.aizhuang).setOnClickListener(this);

		view.findViewById(R.id.jiyun).setOnClickListener(this);

		view.findViewById(R.id.aicai).setOnClickListener(this);
		
		view.findViewById(R.id.cai).performClick();
		
		view.findViewById(R.id.login_qq).setOnClickListener(this);
		
		view.findViewById(R.id.login_tencent).setOnClickListener(this);
		
		view.findViewById(R.id.login_weibo).setOnClickListener(this);

	}

	private void login() {
		Preferences.User user = new Preferences.User(getActivity());
		final UserMe userMe = new UserMe(new UserMe.Params(user.getUid(),
				user.getToken()));
		RequestAsync.request(userMe, new Async() {

			public void onPostExecute(Request request) {
				if (userMe.getStatus() == Request.Status.SUCCESS) {
					headView.setData(userMe.getUserResult().toUserHeadData());
				}

			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Preferences.User user = new Preferences.User(getActivity());
		if (!user.isLogin()) {
			notLoginView.setVisibility(View.VISIBLE);
			headView.setVisibility(View.GONE);
		} else {
			notLoginView.setVisibility(View.GONE);
			headView.setVisibility(View.VISIBLE);
			login();
		}

	}

	public void onClick(View v) {

		MainActivity mainActivity = MainActivity.getInstance();

		
		
		switch (v.getId()) {
		case R.id.login_qq:
			oauthUtils.qqOauth();
			break;
		case R.id.login_tencent:
			oauthUtils.tencentOauth();
			break;
		case R.id.login_weibo:
			oauthUtils.sinaOauth();
			break;
		case R.id.cai:

			mainActivity.switchContent(new CaiFragment());
			
			mainActivity.switchRightIcon(mainActivity.getRightComment());
			
			mainActivity.getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
			
			break;

		case R.id.aizhuang:

			mainActivity.switchContent(ZhuangFragment.newInstance(0));
			
			mainActivity.switchRightIcon(mainActivity.getRightSearch());

			mainActivity.getSlidingMenu().setMode(SlidingMenu.LEFT);
			
			break;

		case R.id.jiyun:
			mainActivity.switchContent(JiyunIndexFragment.newInstance());
			
			mainActivity.switchRightIcon(new View(getActivity()));

			mainActivity.getSlidingMenu().setMode(SlidingMenu.LEFT);
			break;
		case R.id.aicai:
			mainActivity.switchContent(new AicaiFragment());

//			mainActivity.switchRight(new CommentFragment());
			
			mainActivity.switchRightIcon(mainActivity.getRightEmpty());
			
			mainActivity.getSlidingMenu().setMode(SlidingMenu.LEFT);
			
			break;
		default:
			break;
		}

	}

	@Override
	public void onSuccess(Params params) {
		final Login login = new Login(getActivity(), params);
		RequestAsync.request(login, new Async() {
			
			public void onPostExecute(Request request) {
				if(request.getStatus() == Request.Status.SUCCESS){
					if(request.getStatus() == Request.Status.SUCCESS){
						if(getActivity() instanceof MainActivity){
							((MainActivity)getActivity()).login();
						}else{
							onResume();
						}
					}
				}
				
			}
		});
		
	}

}
