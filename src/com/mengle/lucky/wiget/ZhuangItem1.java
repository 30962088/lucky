package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhuangItem1 extends FrameLayout{

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
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.zhuang_list_item3, this);
		photoView = (ImageView) findViewById(R.id.photo);
		titleView = (TextView) findViewById(R.id.title);
		personView = (TextView) findViewById(R.id.person);
		endView = (TextView) findViewById(R.id.endtime);
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
		personView.setText(""+person);
	}

	
	
}
