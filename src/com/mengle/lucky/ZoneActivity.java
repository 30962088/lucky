package com.mengle.lucky;

import com.mengle.lucky.fragments.UserGameCreatorFragment;
import com.mengle.lucky.fragments.UserGameEndFragment;
import com.mengle.lucky.fragments.UserGamingFragment;
import com.mengle.lucky.network.IUserGet;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.UserGet;
import com.mengle.lucky.network.UserMe;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.WigetUtils;
import com.mengle.lucky.utils.WigetUtils.OnItemClickListener;
import com.mengle.lucky.wiget.UserHeadView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ZoneActivity extends FragmentActivity implements
		OnItemClickListener, OnClickListener,OnPageChangeListener {
	
	public static void open(Context context, String uid){
		Intent intent = new Intent(context, ZoneActivity.class);
		intent.putExtra("uid", uid);
		context.startActivity(intent);
	}
	
	private int uid;

	private UserHeadView userHeadView;
	
	private ViewPager viewPager;
	
	private ViewGroup tab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zone_layout);
//		uid = getIntent().getIntExtra("uid",-1);
		uid = 11;
		findViewById(R.id.leftnav).setOnClickListener(this);
		
		userHeadView = (UserHeadView) findViewById(R.id.userhead);

		View rightNav = findViewById(R.id.rightNav);
		rightNav.setOnClickListener(this);
		if(new Preferences.User(this).getUid() == uid){
			rightNav.setVisibility(View.VISIBLE);
		}else{
			rightNav.setVisibility(View.GONE);
		}

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		
		viewPager.setOnPageChangeListener(this);
		
		tab = (ViewGroup) findViewById(R.id.tab);
		
		WigetUtils.onChildViewClick(tab, this);
		
		tab.getChildAt(0).performClick();
		
		viewPager.setAdapter(new ZonePageAdater(getSupportFragmentManager()));

	}

	private void login() {
		Preferences.User user = new Preferences.User(this);
		int uid = user.getUid();
		String token = user.getToken();
		IUserGet userGet;
		if(uid == this.uid){
			userGet = new UserMe(new UserMe.Params(uid, token));
		}else{
			userGet = new UserGet(new UserGet.Params(uid,token,this.uid));
		}
		
		final IUserGet finalUserGet = userGet;
		
		RequestAsync.request(userGet, new Async() {

			public void onPostExecute(Request request) {
				if (finalUserGet.getStatus() == Request.Status.SUCCESS) {
					userHeadView.setData(finalUserGet.getUser().toUserHeadData());
				}

			}
		});
	}
	
	private class ZonePageAdater extends FragmentPagerAdapter{

		private Fragment[] fragments;
		
		public ZonePageAdater(FragmentManager fm) {
			super(fm);
			fragments = new Fragment[]{UserGamingFragment.newInstance(uid),UserGameCreatorFragment.newInstance(uid),UserGameEndFragment.newInstance(uid)};
		}

		@Override
		public Fragment getItem(int position) {
			
			return fragments[position];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.length;
		}
		
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		login();

	}

	private View lastView;

	

	public void onItemClick(ViewGroup group, View view, int position, long id) {
		if (lastView != null) {
			lastView.setSelected(false);
		}

		viewPager.setCurrentItem(position);

		view.setSelected(true);

		lastView = view;

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rightNav:
			UserSettingActivity.open(this);
			break;
		case R.id.leftnav:
			finish();
			break;
		default:
			break;
		}

	}

	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub
		
	}

	public void onPageSelected(int position) {
		tab.getChildAt(position).performClick();
		
	}

	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}
	
	

}