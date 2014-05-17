package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.DisplayUtils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;

public class ZhuangItem1 extends FrameLayout implements OnClickListener{

	public ZhuangItem1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ZhuangItem1(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ZhuangItem1(Context context) {
		super(context);
		init();
	}
	
	private ImageView photoView;
	
	private TextView titleView;
	
	private TextView personView;
	
	private TextView endView;
	
	private ViewGroup layout1;
	
	private TextView person1View;
	
	private View arrowView;
	
	private int gameid;
	
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.zhuang_layout_new, this);
		findViewById(R.id.layout).setOnClickListener(this);
		layout1 = (ViewGroup) findViewById(R.id.layout1);
		titleView = (TextView) findViewById(R.id.title);
		arrowView = findViewById(R.id.arrow);
		left = DisplayUtils.Dp2Px(getContext(), 100);
		personView = (TextView) findViewById(R.id.person);
		person1View = (TextView) findViewById(R.id.person1);
		endView = (TextView) findViewById(R.id.endtime);
		photoView = (ImageView) findViewById(R.id.avatar);
		/*photoView = (ImageView) findViewById(R.id.photo);
		titleView = (TextView) findViewById(R.id.title);
		personView = (TextView) findViewById(R.id.person);
		endView = (TextView) findViewById(R.id.endtime);*/
	}
	
	public void setEndTime(String endtime){
		endView.setText(endtime);
	}
	
	public void setPhoto(String photo){
		BitmapLoader.displayImage(getContext(), photo, photoView);
	}
	
	public void setTitle(String title){
		titleView.setText(title);
	}
	
	public void setPerson(int person){
		personView.setText(getResources().getString(R.string.zhuang_item_count, person));
		person1View.setText(getResources().getString(R.string.zhuang_item_count, person));
	}
	
	private boolean expand = false;
	
	private float left;
	
	public void setExpand(boolean expand,long during){
		
		if(during == 0 || this.expand != expand){
			arrowView.setVisibility(View.VISIBLE);
			layout1.setBackgroundResource(R.drawable.bg_zhuang);
			titleView.setTextColor(Color.parseColor("#ffffff"));
			TranslateAnimation translateAnimation = new TranslateAnimation(0, left, 0, 0);
			
			translateAnimation.setDuration(during);
			translateAnimation.setFillAfter(true);
			layout1.startAnimation(translateAnimation);
			this.expand = expand;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout:
			if(expand){
				
			}else{
				setExpand(true, 200);
			}
			
			break;

		default:
			break;
		}
		
	}

	
	
}
