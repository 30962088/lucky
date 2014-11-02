package com.mengle.lucky.fragments;

import java.util.Timer;
import java.util.TimerTask;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.adapter.StageAdapter;
import com.mengle.lucky.wiget.TabPageIndicator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StageFragment extends Fragment {

	private ViewPager viewPager;

	public void setAdapter(StageAdapter adapter) {
		viewPager.setAdapter(adapter);
		TabPageIndicator indicator = (TabPageIndicator) getView().findViewById(
				R.id.indicator);
		indicator.setViewPager(viewPager);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.stage_layout, null);
	}

	private TimerTask timer = new TimerTask() {

		@Override
		public void run() {
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					int i = viewPager.getCurrentItem();
					i++;
					int total = viewPager.getAdapter().getCount();
					if (i >= total) {
						i = 0;
					}
					viewPager.setCurrentItem(i, true);
//					doStart();
				}
			});

		}
	};
	private Timer t;

	private void doStart() {
		t = new Timer();
		t.schedule(timer, 5000, 5000);

	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				

			}
		});
		doStart();
		if (getActivity() instanceof MainActivity) {
			((MainActivity) getActivity()).getSlidingMenu().addIgnoredView(
					viewPager);
		}
	}

}
