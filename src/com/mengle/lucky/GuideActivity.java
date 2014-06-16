package com.mengle.lucky;

import com.mengle.lucky.fragments.BlurFragment;
import com.mengle.lucky.fragments.LaunchItemEndFragment;
import com.mengle.lucky.fragments.LaunchItemFragment;
import com.mengle.lucky.fragments.LaunchItemFragment.Item;
import com.mengle.lucky.wiget.CustomViewPager;
import com.mengle.lucky.wiget.TabPageIndicator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

public class GuideActivity extends FragmentActivity implements OnPageChangeListener{
	
	
	private CustomViewPager viewPager;
	
	private ImageView indexView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_layout);
		viewPager = (CustomViewPager) findViewById(R.id.viewpager);
		indexView = (ImageView) findViewById(R.id.index_view);
		indexView.setVisibility(View.GONE);
		viewPager.setAdapter(new MyPageAdapter());
		viewPager.setOffscreenPageLimit(5);
		viewPager.setOnPageChangeListener(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Handler(getMainLooper()).postDelayed(new Runnable() {
			
			@Override
			public void run() {
				((LaunchItemEndFragment)fragments[0]).startAnim();
				
			}
		}, 2000);
		
	}
	
	
	private Item[] items = new Item[]{
			new Item(R.drawable.launch_01, R.drawable.launch_top_01),
			new Item(R.drawable.launch_02, R.drawable.launch_top_02),
			new Item(R.drawable.launch_03, R.drawable.launch_top_03),
			new Item(R.drawable.launch_04, R.drawable.launch_top_04)
	};
	
	private  BlurFragment[] fragments = new BlurFragment[]{LaunchItemEndFragment.newInstance(items[3]),LaunchItemFragment.newInstance(items[0]),LaunchItemFragment.newInstance(items[1]),LaunchItemFragment.newInstance(items[2])};
	
	private class MyPageAdapter extends FragmentPagerAdapter{

		
		
		public MyPageAdapter() {
			super(getSupportFragmentManager());
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments[arg0];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 1;
		}
	}
	
	private void setBlur(boolean blur){
		for(BlurFragment fragment : fragments){
			fragment.setBlur(blur);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		switch (arg0) {
		/*case 1:
			setBlur(true);
			break;
		case 0:
			setBlur(false);
			break;
		default:
			break;*/
		}
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		
	}

	private int[] indexs = new int[]{
			R.drawable.launch_a,
			R.drawable.launch_b,
			R.drawable.launch_c,
	};
	
	@Override
	public void onPageSelected(int arg0) {
		if(arg0<=2){
			indexView.setImageResource(indexs[arg0]);
		}else{
			viewPager.setPagingEnabled(false);
			indexView.setVisibility(View.GONE);
			((LaunchItemEndFragment)fragments[arg0]).startAnim();
		}
		
	}
	

}
