package com.mengle.lucky;

import com.mengle.lucky.fragments.MsgFragment;
import com.mengle.lucky.fragments.NofityFragment;
import com.mengle.lucky.utils.WigetUtils;
import com.mengle.lucky.utils.WigetUtils.OnItemClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

public class NotifyCenterActivity extends FragmentActivity implements OnItemClickListener,OnPageChangeListener{
	
	private ViewPager viewPager;
	
	private ViewGroup tab;
	
	private Integer lastTab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nofity_center);
		tab = (ViewGroup) findViewById(R.id.tab);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 2;
			}
			
			@Override
			public Fragment getItem(int position) {
				Fragment fragment;
				if(position == 0){
					fragment = NofityFragment.newInstance();
				}else{
					fragment = MsgFragment.newInstance();
				}
				return fragment;
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

	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub
		
	}

	public void onPageSelected(int position) {
		if(lastTab != null){
			tab.getChildAt(lastTab).setSelected(false);
		}
		tab.getChildAt(position).setSelected(true);
		lastTab = position;
	}

	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}

}
