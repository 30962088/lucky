package com.mengle.lucky.wiget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.makeramen.RoundedImageView;
import com.mengle.lucky.R;


public class PhotoView extends RoundedImageView {

	
	
	public PhotoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PhotoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PhotoView(Context context) {
		super(context);
		init();
	}
	
	

	private void init() {
		setScaleType(ScaleType.FIT_XY);
		setOval(true);
		setBackgroundColor(Color.WHITE);
		setRoundBackground(true);
//		setBorderColor(getResources().getColor(R.color.photo_border));
//		setBorderWidth((int)getResources().getDimension(R.dimen.border_size));
	}
	
	
}
