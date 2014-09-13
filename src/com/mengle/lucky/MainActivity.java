package com.mengle.lucky;


import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mengle.lucky.fragments.CaiFragment;
import com.mengle.lucky.fragments.CommentFragment;
import com.mengle.lucky.fragments.NoLoginFragment;
import com.mengle.lucky.fragments.SidingMenuFragment;
import com.mengle.lucky.fragments.ZhuangFragment;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.TipGameGet;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.WigetUtils;
import com.mengle.lucky.utils.Preferences.Push;
import com.mengle.lucky.wiget.AlertDialog;
import com.umeng.update.UmengUpdateAgent;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {
	
	

	public static void open(Context context,String action) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		if(!TextUtils.isEmpty(action)){
			intent.setAction(action);
		}
		
		context.startActivity(intent);
	}
	
	public static void open(Context context){
		open(context,null);
	}
	
	

	public static MainActivity instance;
	
	private Fragment switchFragment;

	private View rightComment;

	private View rightSearch;

	private View rightEmpty;

	private ViewGroup rightContainer;
	
	private View newGameTip;

	public static MainActivity getInstance() {
		return instance;
	}

	private SidingMenuFragment menuFragment;

	public MainActivity() {
		instance = this;
	}
	
private void startPush() {
		
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                com.mengle.lucky.push.Utils.getMetaValue(this, "api_key"));
		
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.currentActivity = this;
		startPush();
		UmengUpdateAgent.update(this);
		checkFirstLogin();
		setContentView(R.layout.activity_main);
		newGameTip = findViewById(R.id.new_game_tip);
		rightContainer = (ViewGroup) findViewById(R.id.right_container);
		rightComment = findViewById(R.id.right_comment);
		rightComment.setOnClickListener(this);
		rightSearch = findViewById(R.id.right_search);
		rightEmpty = findViewById(R.id.right_empty);
		rightSearch.setOnClickListener(this);
		initSlidingMenu();
		
	}
	
	private void checkNewGame(){
		User user = new User(this);
		if(user.isLogin()){
			final TipGameGet gameGet = new TipGameGet(new TipGameGet.Param(user.getUid(), user.getToken()));
			RequestAsync.request(gameGet, new Async() {
				
				@Override
				public void onPostExecute(Request request) {
					displayNewTipDone(gameGet.getCount()>0?true:false);
					
				}
			});
		}
		
	}
	
	private void displayNewTipDone(boolean val){
		newGameTip.setVisibility(val?View.VISIBLE:View.GONE);
	}
	
	
	
	

	private void doPushLogout() {
		Preferences.Push push = new Push(this);
		if(push.isLogout()){
			Utils.tip(this, "您已被踢出");
			push.setLogout(false);
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		doPushLogout();
		if(switchFragment != null){
			switchContent(switchFragment);
			switchFragment = null;
		}
		checkNewGame();
		
	}


	public View getRightComment() {
		return rightComment;
	}

	public View getRightSearch() {
		return rightSearch;
	}

	public View getRightEmpty() {
		return rightEmpty;
	}

	private void checkFirstLogin() {
		// Preferences.User user = new Preferences.User(this);
		// if(user.isFirstLogin()){
		// BuildAccountLoginActivity.open(this);
		// user.setFirstLogin(false);
		// }

	}

	public void switchRightIcon(View view) {
		WigetUtils.switchVisible(rightContainer, view);
	}

	private void initSlidingMenu() {
		menuFragment = new SidingMenuFragment();
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, menuFragment).commit();

		findViewById(R.id.btn_more).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				toggle();

			}
		});

		sm.setSecondaryMenu(R.layout.menu_frame_two);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);

	}

	private Fragment rightFragment;

	public void switchRight(Fragment fragment) {
		this.rightFragment = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_two, fragment).commit();
	}

	private Fragment mContent;

	public void login() {
		menuFragment.onResume();
		if(rightFragment!=null ){
			rightFragment.onResume();
		}
		
	}

	public void switchContent(Fragment fragment) {
		getSlidingMenu().clearIgnoredViews();
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.right_search:
			SearchActivity.open(this);
			break;
		case R.id.right_comment:
			showSecondaryMenu();
			break;
		default:
			break;
		}

	}

	long waitTime = 2000;
	long touchTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void setSwitchFragment(Fragment switchFragment) {
		this.switchFragment = switchFragment;
	}

}
