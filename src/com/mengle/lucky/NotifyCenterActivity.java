package com.mengle.lucky;

import java.sql.SQLException;

import com.mengle.lucky.fragments.MsgFragment;
import com.mengle.lucky.fragments.NofityFragment;
import com.mengle.lucky.utils.WigetUtils;
import com.mengle.lucky.utils.WigetUtils.OnItemClickListener;
import com.mengle.lucky.wiget.CustomViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class NotifyCenterActivity extends BaseActivity implements
		OnItemClickListener, OnPageChangeListener {

	public static void open(Context context) {
		context.startActivity(new Intent(context, NotifyCenterActivity.class));
	}

	private CustomViewPager viewPager;

	private ViewGroup tab;

	private Integer lastTab;
	
	private Fragment[] fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		App.currentActivity = this;
		setContentView(R.layout.nofity_center);
		fragments = new Fragment[]{NofityFragment.newInstance(findViewById(R.id.new_notice)),MsgFragment.newInstance(findViewById(R.id.new_letter))};
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();

			}
		});
		tab = (ViewGroup) findViewById(R.id.tab);
		viewPager = (CustomViewPager) findViewById(R.id.viewpager);
		viewPager.setPagingEnabled(false);
		viewPager.setAdapter(new FragmentPagerAdapter(
				getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 2;
			}

			@Override
			public Fragment getItem(int position) {
				
				return fragments[position];
			}
		});
		WigetUtils.onChildViewClick(tab, this);
		viewPager.setOnPageChangeListener(this);
		tab.getChildAt(0).performClick();
	}

	public void onItemClick(ViewGroup group, View view, int position, long id) {
		viewPager.setCurrentItem(position);
		view.setSelected(true);
		lastTab = position;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ping();
	}
	
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub

	}

	public void onPageSelected(int position) {
		if (lastTab != null) {
			tab.getChildAt(lastTab).setSelected(false);
		}
		Fragment fragment = fragments[position];
		if(fragment instanceof NofityFragment){
			try {
				((NofityFragment)fragment).request();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(fragment instanceof MsgFragment){
			try {
				((MsgFragment)fragment).request();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tab.getChildAt(position).setSelected(true);
		lastTab = position;
	}

	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}

}
