package com.mengle.lucky.fragments;

import com.mengle.lucky.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class SidingMenuFragment extends Fragment implements OnClickListener,OnPageChangeListener {

	private ViewPager viewPager;

	private View tab1;

	private View tab2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.sidemenu_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		viewPager.setAdapter(new ScreenSlidePagerAdapter(getFragmentManager()));
		viewPager.setOnPageChangeListener(this);
		tab1 = view.findViewById(R.id.tab1);
		tab2 = view.findViewById(R.id.tab2);
		view.findViewById(R.id.tab1nav).setOnClickListener(this);
		view.findViewById(R.id.tab2nav).setOnClickListener(this);
		view.findViewById(R.id.tab1setting).setOnClickListener(this);
		view.findViewById(R.id.tab2setting).setOnClickListener(this);
		switchNav();
	}
	
	
	
	private void switchNav(){
		if(tab1.getVisibility() != View.VISIBLE){
			tab1.setVisibility(View.VISIBLE);
			tab2.setVisibility(View.GONE);
			viewPager.setCurrentItem(0);
		}
		
		
	}
	
	private void switchSetting(){
		if(tab2.getVisibility() != View.VISIBLE){
			tab1.setVisibility(View.GONE);
			tab2.setVisibility(View.VISIBLE);
			viewPager.setCurrentItem(1);
		}
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab1nav:
			switchNav();
			break;

		case R.id.tab2nav:
			switchNav();
			break;
		case R.id.tab1setting:
			switchSetting();
			break;

		case R.id.tab2setting:
			switchSetting();
			break;

		}

	}
	
	
    private static class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    	
    	
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
            	return new NavFragment();
            }else if(position == 1){
            	return new SettingFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	public void onPageSelected(int index) {
		switch (index) {
		case 0:
			switchNav();
			break;

		case 1:
			switchSetting();
			break;
		}
		
	}


	

}
