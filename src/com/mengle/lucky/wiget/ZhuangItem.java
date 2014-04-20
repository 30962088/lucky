package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhuangItem extends FrameLayout{

	public ZhuangItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ZhuangItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ZhuangItem(Context context) {
		super(context);
		init();
	}
	
	private ImageView photoView;
	
	private TextView titleView;
	
	private TextView personView;
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.zhuang_list_item2, this);
		photoView = (ImageView) findViewById(R.id.photo);
		titleView = (TextView) findViewById(R.id.title);
		personView = (TextView) findViewById(R.id.person);
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
