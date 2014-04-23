package com.mengle.lucky.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BubbleAnswerView extends RelativeLayout{

	public BubbleAnswerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public BubbleAnswerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BubbleAnswerView(Context context) {
		super(context);
		init();
	}
	
	private Paint paint;
	
	private void init(){
		setWillNotDraw(false);
		paint = new Paint();
		paint.setAntiAlias(true); 
		paint.setStyle(Paint.Style.FILL);
		
	}
	
	public void setColor(int color){
		paint.setColor(color);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawCircle(getWidth()/2, getWidth()/2, getWidth()/2, paint);
	}
	
}
