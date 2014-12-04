package com.mengle.lucky;

import java.util.Timer;
import java.util.TimerTask;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mengle.lucky.fragments.CaiFragment;
import com.mengle.lucky.fragments.CommentFragment;
import com.mengle.lucky.fragments.NoLoginFragment;
import com.mengle.lucky.fragments.SidingMenuFragment;
import com.mengle.lucky.fragments.ZhuangFragment;
import com.mengle.lucky.network.AppLoginRequest;
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
import com.mengle.lucky.wiget.PushDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
		OnClickListener, LocationListener {

	public static void open(Context context, String action) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		if (!TextUtils.isEmpty(action)) {
			intent.setAction(action);
		}

		context.startActivity(intent);
	}

	public static void open(Context context) {
		open(context, null);
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
		loginRequest();
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

	private void loginRequest() {
		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				loginrequest(null);
				
			}
		}, 2000);
	}

	private void checkNewGame() {
		User user = new User(this);
		if (user.isLogin()) {
			final TipGameGet gameGet = new TipGameGet(this,
					new TipGameGet.Param(user.getUid(), user.getToken()));
			RequestAsync.request(gameGet, new Async() {

				@Override
				public void onPostExecute(Request request) {
					displayNewTipDone(gameGet.getCount() > 0 ? true : false);

				}
			});
		}

	}

	private void displayNewTipDone(boolean val) {
		newGameTip.setVisibility(val ? View.VISIBLE : View.GONE);
	}

	private void doPushLogout() {
		Preferences.Push push = new Push(this);
		if (push.isLogout()) {
			Utils.tip(this, "您已被踢出");
			push.setLogout(false);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		doPushLogout();
		if (switchFragment != null) {
			switchContent(switchFragment);
			switchFragment = null;
		}
		checkNewGame();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
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
		if (rightFragment != null) {
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

	public static final String ACTION_REFRESH_ZHUANG = "refresh_zhuang";

	public static final String ACTION_LOGOUT = "logout";

	private boolean flag9 = false;

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (TextUtils.equals(intent.getAction(), ACTION_REFRESH_ZHUANG)) {
			if (mContent != null && mContent instanceof ZhuangFragment) {
				((ZhuangFragment) mContent).refresh();
			}

		} else if (TextUtils.equals(intent.getAction(), ACTION_LOGOUT)) {
			Preferences.User user = new Preferences.User(App.getInstance());
			user.logout();
			PushDialog.sletter = null;
			PushDialog.smsg = null;
			if (!flag9) {
				flag9 = true;
				System.out.println("zzmzzm");
				AlertDialog.open(this, "该账号已在其他设备上登录", null);
				new Handler(getMainLooper()).postDelayed(new Runnable() {

					@Override
					public void run() {
						flag9 = false;

					}
				}, 30 * 1000);
			}

		}

	}

	private boolean islogin = false;

	private LocationManager mlocManager;

	private void loginrequest(Location location) {
		String lng = null;
		String lat = null;
		if(location != null){
			lng = ""+location.getLongitude();
			lat = ""+location.getLatitude();
		}
		try {
			if (!islogin) {
				islogin = true;
				User user = new User(this);
				int newUser = user.getNewuser();
				user.setNewuser(0);
				ApplicationInfo appInfo = this.getPackageManager()
						.getApplicationInfo(getPackageName(),
								PackageManager.GET_META_DATA);
				String channel = appInfo.metaData.getString("UMENG_CHANNEL");

				PackageInfo pinfo = getPackageManager().getPackageInfo(
						getPackageName(), 0);
				AppLoginRequest request = new AppLoginRequest(this,
						new AppLoginRequest.Params(user.getUid(), channel, lng, lat, pinfo.versionName,
								newUser));

				RequestAsync.request(request, null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location.getLatitude() != 0 && location.getLongitude() != 0) {
			loginrequest(location);
			mlocManager.removeUpdates(this);
			
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		System.out.println("zzm:enable");

	}

	@Override
	public void onProviderDisabled(String provider) {
		loginrequest(null);

	}

}
