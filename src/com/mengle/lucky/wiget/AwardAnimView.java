package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.DisplayUtils;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class AwardAnimView extends RelativeLayout{

	public AwardAnimView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public AwardAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public AwardAnimView(Context context) {
		super(context);
		
	}
	
	public static interface Callback{
		public void onsuccess();
	}
	
	
	private View[] countViews;
	
	private int current = 0;
	
	private Callback callback;
	
	public void setAward(int award){
		countViews = new View[award];
		for(int i = 1;i<=award;i++){
			ImageView countView = new ImageView(getContext());
			countView.setImageBitmap(textAsBitmap(""+i));
			countView.setScaleType(ScaleType.FIT_CENTER);
			countView.setVisibility(View.INVISIBLE);
			addView(countView, new LayoutParams(DisplayUtils.Dp2Px(getContext(), 80), DisplayUtils.Dp2Px(getContext(), 110)));
			countViews[i-1] = countView;
		}
	}
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public Bitmap textAsBitmap(String text) {
		int pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                150, getResources().getDisplayMetrics());
	    Paint paint = new Paint();
	    paint.setTextSize(pixel);
	    paint.setColor(Color.WHITE);
	    paint.setTextAlign(Paint.Align.LEFT);
	    int width = (int) (paint.measureText(text) + 0.5f); // round
	    float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
	    int height = (int) (baseline + paint.descent() + 0.5f);
	    Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(image);
	    canvas.drawText(text, 0, baseline, paint);
	    return image;
	}
	
	
	public void start(){
		if(current == countViews.length){
			return;
		}
		final View view = countViews[current];
		Animation animation;
		
		if(current == countViews.length-1){
			animation= AnimationUtils.loadAnimation(getContext(), R.anim.award_end);
		}else{
			animation= AnimationUtils.loadAnimation(getContext(), R.anim.award);
		}
		
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				if(current < countViews.length-1){
					view.setVisibility(View.INVISIBLE);
					current++;
					start();
				}else{
					if(callback != null){
						callback.onsuccess();
					}
				}
				
				
			}
		});
		
		view.setVisibility(View.VISIBLE);
		view.startAnimation(animation);
		
		
	}
	
	public static class CountView extends View{

		private Paint textPaint = new Paint();
		
		public CountView(Context context) {
			super(context);
			init();
		}
		
		
		public CountView(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			init();
		}


		public CountView(Context context, AttributeSet attrs) {
			super(context, attrs);
			init();
		}


		private void init() {
			textPaint.setColor(Color.WHITE);
		    textPaint.setTextAlign(Align.CENTER);
		    textPaint.setAntiAlias(true);  
		    int pixel= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		                                  80, getResources().getDisplayMetrics());
		    textPaint.setTextSize(pixel);
		}
		
		private String text = "1";
		
		private void setText(int text){
			this.text = ""+text;
		}

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.draw(canvas);
			
			
			Rect areaRect = new Rect(0, 0, getWidth(), getHeight());


			RectF bounds = new RectF(areaRect);
			// measure text width
			bounds.right = textPaint.measureText(text, 0, text.length());
			// measure text height
			bounds.bottom = textPaint.descent() - textPaint.ascent();

			bounds.left += (areaRect.width() ) / 2.0f;
			bounds.top += (areaRect.height() - bounds.bottom) / 2.0f;

			canvas.drawText(text, bounds.left, bounds.top - textPaint.ascent(), textPaint);
		}
		
		
	}

}
