package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public  class ChatTitleItem extends FrameLayout{
	
	public ChatTitleItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public ChatTitleItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public ChatTitleItem(Context context) {
		super(context);
		
	}
	
	private String title;

	
	public void setTitle(String title) {
		this.title = title;
		init();
	}
	
	private void init(){
		
		LayoutInflater.from(getContext()).inflate(R.layout.chat_title_item, this);
		
		((TextView)findViewById(R.id.title)).setText(title);
	}

	
	
}
