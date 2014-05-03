package com.mengle.lucky.wiget;


import android.content.Context;
import android.util.AttributeSet;

public  class ChatRightItem extends ChatItem{

	public ChatRightItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ChatRightItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ChatRightItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Orientation orientation() {
		// TODO Auto-generated method stub
		return Orientation.RIGHT;
	}

	
	
	
}
