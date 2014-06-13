package com.mengle.lucky;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mengle.lucky.fragments.CaiFragment;
import com.mengle.lucky.fragments.CommentFragment;
import com.mengle.lucky.fragments.NoLoginFragment;
import com.mengle.lucky.fragments.SidingMenuFragment;
import com.mengle.lucky.fragments.ZhuangFragment;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.WigetUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {

	public static void open(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	public static MainActivity instance;

	private View rightComment;

	private View rightSearch;

	private View rightEmpty;

	private ViewGroup rightContainer;

	public static MainActivity getInstance() {
		return instance;
	}

	private SidingMenuFragment menuFragment;

	public MainActivity() {
		instance = this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkFirstLogin();
		setContentView(R.layout.activity_main);
		rightContainer = (ViewGroup) findViewById(R.id.right_container);
		rightComment = findViewById(R.id.right_comment);
		rightComment.setOnClickListener(this);
		rightSearch = findViewById(R.id.right_search);
		rightEmpty = findViewById(R.id.right_empty);
		rightSearch.setOnClickListener(this);
		initSlidingMenu();

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
		rightFragment.onResume();
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

}
