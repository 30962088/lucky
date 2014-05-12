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
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity extends SlidingFragmentActivity implements OnClickListener{

	public static MainActivity instance;
	
	private View rightComment;
	
	private View rightSearch;
	
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
		rightSearch.setOnClickListener(this);
		initSlidingMenu();
		
	}
	
	public View getRightComment() {
		return rightComment;
	}
	
	public View getRightSearch() {
		return rightSearch;
	}

	private void checkFirstLogin() {
		Preferences.User user = new Preferences.User(this);
//		if(user.isFirstLogin()){
			BuildAccountLoginActivity.open(this);
//			user.setFirstLogin(false);
//		}
		
	}
	
	public void switchRightIcon(View view){
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
	
	public void switchRight(Fragment fragment){
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.menu_frame_two, fragment).commit();
	}

	

	
	
	private Fragment mContent;
	
	public void switchContent(Fragment fragment) {

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

		default:
			break;
		}
		
	}

}
