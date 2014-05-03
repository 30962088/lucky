package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public  class ChatLeftItem extends ChatItem{

	public ChatLeftItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ChatLeftItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ChatLeftItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Orientation orientation() {
		// TODO Auto-generated method stub
		return Orientation.LEFT;
	}

	
	
	
}
