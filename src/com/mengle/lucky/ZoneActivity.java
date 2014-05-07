package com.mengle.lucky;

import com.mengle.lucky.fragments.OnGoingFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class ZoneActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zone_layout);
		((ViewPager)findViewById(R.id.viewpager)).setAdapter(new ZonePageAdater(getSupportFragmentManager()));
		
	}
	
	
	private static class ZonePageAdater extends FragmentPagerAdapter{

		public ZonePageAdater(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return OnGoingFragment.newInstance();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}
		
		
		
	}
	
}
