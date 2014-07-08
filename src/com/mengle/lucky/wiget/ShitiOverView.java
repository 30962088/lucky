package com.mengle.lucky.wiget;

import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.ShitiActivity;
import com.mengle.lucky.fragments.ShitiFragment;
import com.mengle.lucky.network.GameLibraryDayChanceRequest.Callback;
import com.mengle.lucky.network.model.Chance;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ShitiOverView extends FrameLayout implements OnClickListener{

	public static class Model{

		private int addCoin;
		private int currentCoin;
		private int star;
		private int totalCoin;
		public Model(int addCoin, int currentCoin,int totalCoin,int star) {
			super();
			this.addCoin = addCoin;
			this.currentCoin = currentCoin;
			this.totalCoin = totalCoin;
			this.star = star;
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
		btnOver.setOnClickListener(this);
		btnAgain = (TextView) findViewById(R.id.again);
		btnAgain.setOnClickListener(this);
	}
	
	private Model model;
	
	public void setModel(Model model){
		this.model = model;
		setVisibility(View.VISIBLE);
		starView.setBackgroundResource(STARTS[model.star]);
		currentView.setText(""+model.currentCoin);
		addcount.setText("+"+model.addCoin);
		totalView.setText("总金额："+(model.totalCoin));
		Chance.getChance(getContext(), new Callback() {
			
			@Override
			public void onChanceCount(int count) {
				count--;
				if(count <= 0){
					btnOver.setVisibility(View.VISIBLE);
					btnAgain.setVisibility(View.GONE);
				}else{
					btnOver.setVisibility(View.GONE);
					btnAgain.setVisibility(View.VISIBLE);
					btnAgain.setText("再来一次("+count+")");
				}
				Chance.updateChance(count);
				
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.over:
			
			break;
			
		case R.id.again:
			
			((ShitiActivity)getContext()).switchContent(ShitiFragment.newInstance());
			
			break;

		default:
			break;
		}
		
	}
	
	

}
