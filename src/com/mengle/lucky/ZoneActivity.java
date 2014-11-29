package com.mengle.lucky;

import java.io.InputStream;

import com.mengle.lucky.fragments.UserGameCreatorFragment;
import com.mengle.lucky.fragments.UserGameEndFragment;
import com.mengle.lucky.fragments.UserGamingFragment;
import com.mengle.lucky.network.IUserGet;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.TipGameRead;
import com.mengle.lucky.network.UserGet;
import com.mengle.lucky.network.UserMe;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.utils.WigetUtils;
import com.mengle.lucky.utils.WigetUtils.OnItemClickListener;
import com.mengle.lucky.wiget.UserHeadView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.TextView;

public class ZoneActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener,OnPageChangeListener {
	
	public static void open(Context context, int uid){
		Intent intent = new Intent(context, ZoneActivity.class);
		intent.putExtra("uid", uid);
		context.startActivity(intent);
	}
	
	private int uid;

	private UserHeadView userHeadView;
	
	private ViewPager viewPager;
	
	private ViewGroup tab;
	
	private TextView titleView;
	
	private TextView tabCreatorView;
	
	private TextView tabTitleView;
	
	private View containerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		App.currentActivity = this;
		setContentView(R.layout.zone_layout);
		containerView = findViewById(R.id.container);
		containerView.setVisibility(View.GONE);
		uid = getIntent().getIntExtra("uid",-1);
//		uid = 11;
		findViewById(R.id.leftnav).setOnClickListener(this);
		titleView = (TextView) findViewById(R.id.header_title);
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
		viewPager.setOffscreenPageLimit(3);
		
		if(uid==new Preferences.User(this).getUid()){
			WigetUtils.switchVisible((ViewGroup)findViewById(R.id.right_container),R.id.rightNav );
		}else{
			WigetUtils.switchVisible((ViewGroup)findViewById(R.id.right_container),R.id.right_comment );
		}
		findViewById(R.id.right_comment).setOnClickListener(this);
		
		tabCreatorView  = (TextView) findViewById(R.id.tab_creator);
		
		tabTitleView = (TextView) findViewById(R.id.tab_title);
		
		readGame();
	}

	private void readGame() {
		User user = new User(this);
		TipGameRead read = new TipGameRead(this,new TipGameRead.Param(user.getUid(), user.getToken(), uid));
		RequestAsync.request(read, null);
		
	}

	private void login() {
		Preferences.User user = new Preferences.User(this);
		final int uid = user.getUid();
		String token = user.getToken();
		IUserGet userGet;
		if(uid == this.uid){
			userGet = new UserMe(this,new UserMe.Params(uid, token));
		}else{
			userGet = new UserGet(this,new UserGet.Params(uid,token,this.uid));
		}
		
		final IUserGet finalUserGet = userGet;
		
		RequestAsync.request(userGet, new Async() {

			public void onPostExecute(Request request) {
				if (finalUserGet.getStatus() == Request.Status.SUCCESS) {
					containerView.setVisibility(View.VISIBLE);
					userHeadView.setData(finalUserGet.getUserResult().toUserHeadData());
					if(uid == ZoneActivity.this.uid){
						userHeadView.checkNewMsg();
					}
					
					
					titleView.setText(finalUserGet.getUserResult().getNickname());
					
					
					String s="";
					if(ZoneActivity.this.uid==uid){
						s = "我";
						
					}else{
						if(finalUserGet.getUserResult().getGender() == 0){
							s = "她";
						}else{
							s = "他";
						}
					}
					tabCreatorView.setText(s+"发起的");
					tabTitleView.setText(s+"的竞猜详情");
					
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
	public void onResume() {
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
		case R.id.right_comment:
			ChatActivity.open(this, uid);
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
	
	public static final int SELECT_PHOTO = 1;
	
	public static interface OnPickListener{
		public void onPick(Uri uri);
	}
	
	private OnPickListener onPickListener;
	
	public void pick(OnPickListener onPickListener){
		this.onPickListener = onPickListener;
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);    
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = imageReturnedIntent.getData();
	            onPickListener.onPick(selectedImage);
	        }
	    }
	}
	
	

}
