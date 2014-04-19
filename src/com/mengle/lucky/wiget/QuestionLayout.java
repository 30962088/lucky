package com.mengle.lucky.wiget;

import com.mengle.lucky.adapter.QuestionAdapter.Status;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class QuestionLayout extends RelativeLayout {

	public static final int TYPE_NORMAL = 0;
	
	public static final int TYPE_MOST = 1;

	public static class Status{
		private boolean gameover;
		private boolean passed;
		public Status(boolean gameover, boolean passed) {
			this.gameover = gameover;
			this.passed = passed;
		}
		
	}
	
	@SuppressLint("NewApi")
	public QuestionLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public QuestionLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public QuestionLayout(Context context) {
		super(context);
		init();
	}

	private Paint paint;

	private int type = TYPE_NORMAL;
	
	private Status status;
	
	public void setColor(int color) {
		paint.setColor(color);
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	private void init() {
		setWillNotDraw(false);
		paint = new Paint();
		paint.setAntiAlias(true); 
		paint.setStyle(Paint.Style.FILL);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(status != null && status.gameover){
			if(!status.passed){
				paint.setColor(Color.parseColor("#b9b9b9"));
			}
		}
		if(type == TYPE_NORMAL){
			drawNormal(canvas);
		}else if(type == TYPE_MOST){
			drawMost(canvas);
		}
		
	}
	
	private void drawMost(Canvas canvas){
		float cx = getWidth()/2;
		float cy = getHeight()/2;
		
		for(int i = 0;i<3;i++){
			paint.setAlpha(getOpacity2(i));
			canvas.drawCircle(cx, cy,getRadius2(cx, i) , paint);
		}
	}
	
	private void drawNormal(Canvas canvas){
		float cx = getWidth()/2;
		float cy = getHeight()/2;
		
		for(int i = 0;i<4;i++){
			paint.setAlpha(getOpacity(i));
			canvas.drawCircle(cx, cy,getRadius(cx, i) , paint);
		}
	}
	
	private int getOpacity2(int i){
		double opacity = 0;
		switch (i) {
		case 0:
			opacity = 0.2;
			break;
		case 1:
			opacity = 0.4;
			break;
		case 2:
			opacity = 1;
			break;
		}
		
		return (int) (255 * (opacity));
	}

	
	private float getRadius2(float radius,int i){
		double percent = 0;
		switch (i) {
		case 0:
			percent = 1;
			break;
		case 1:
			percent = 0.9;
			break;
		case 2:
			percent = 0.82;
			break;
		}
		return (float) (radius*percent);
	}
	
	private float getRadius(float radius,int i){
		double percent = 0;
		switch (i) {
		case 0:
			percent = 1;
			break;
		case 1:
			percent = 0.9;
			break;
		case 2:
			percent = 0.72;
			break;
		case 3:
			percent = 0.43;
			break;
		}
		return (float) (radius*percent);
	}
	
	
	
	private int getOpacity(int i){
		double opacity = 0;
		switch (i) {
		case 0:
			opacity = 0.2;
			break;
		case 1:
			opacity = 0.4;
			break;
		case 2:
			opacity = 0.6;
			break;
		case 3:
			opacity = 1;
			break;
		}
		
		return (int) (255 * (opacity));
	}

}
