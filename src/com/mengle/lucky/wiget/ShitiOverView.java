package com.mengle.lucky.wiget;

import com.mengle.lucky.R;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ShitiOverView extends FrameLayout{

	public static class Model{
		private int count;
		private int addCoin;
		private int currentCoin;
		public Model(int count, int addCoin, int currentCoin) {
			super();
			this.count = count;
			this.addCoin = addCoin;
			this.currentCoin = currentCoin;
		}
		
	}
	
	public ShitiOverView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ShitiOverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ShitiOverView(Context context) {
		super(context);
		init();
	}
	
	private static final int[] STARTS = new int[]{R.drawable.star_0,R.drawable.star_1,R.drawable.star_2,R.drawable.star_3}; 
	
	private View starView;
	
	private TextView currentView;
	
	private TextView addcount;
	
	private TextView totalView;
	
	private View btnOver;
	
	private TextView btnAgain;
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.shiti_over, this);
		Typeface face = Typeface.createFromAsset(getContext().getAssets(), "fonts/SHOWG.TTF");
		starView = findViewById(R.id.star);
		currentView = (TextView) findViewById(R.id.current);
		currentView.setTypeface(face);
		addcount = (TextView) findViewById(R.id.addcount);
		addcount.setTypeface(face);
		totalView = (TextView) findViewById(R.id.total);
		btnOver = findViewById(R.id.over);
		btnAgain = (TextView) findViewById(R.id.again);
	}
	
	public void setModel(Model model){
		setVisibility(View.VISIBLE);
		starView.setBackgroundResource(STARTS[model.addCoin]);
		currentView.setText(""+model.currentCoin);
		addcount.setText("+"+model.addCoin);
		totalView.setText("总金额："+(model.addCoin+model.currentCoin));
		if(model.count <= 0){
			btnOver.setVisibility(View.VISIBLE);
			btnAgain.setVisibility(View.GONE);
		}else{
			btnOver.setVisibility(View.GONE);
			btnAgain.setVisibility(View.VISIBLE);
			btnAgain.setText("再来一次("+model.count+")");
		}
	}
	
	

}
