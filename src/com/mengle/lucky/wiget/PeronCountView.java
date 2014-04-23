package com.mengle.lucky.wiget;


import com.mengle.lucky.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PeronCountView extends FrameLayout {

	public static class Count{
		private int icon;
		private int count;
		public Count(int icon, int count) {
			super();
			this.icon = icon;
			this.count = count;
		}
		
	}
	
	public PeronCountView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PeronCountView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PeronCountView(Context context) {
		super(context);
		init();
	}

	private View iconView;
	
	private TextView countView;

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.person_count_view, this);
		iconView = findViewById(R.id.icon);
		countView = (TextView) findViewById(R.id.count);
		setVisibility(View.VISIBLE);
	}
	
	public void setCount(Count count){
		countView.setText(""+count.count);
		iconView.setBackgroundResource(count.icon);
	}

}
