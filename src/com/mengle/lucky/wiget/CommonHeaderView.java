package com.mengle.lucky.wiget;


import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.CatListAdater;
import com.mengle.lucky.network.CampaignsGetRequest.Result;
import com.mengle.lucky.wiget.CatDropList.OnAdCallback;
import com.mengle.lucky.wiget.CatDropList.OnStateChange;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class CommonHeaderView extends RelativeLayout implements OnClickListener,OnStateChange{

	
	public CommonHeaderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CommonHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CommonHeaderView(Context context) {
		super(context);
		init();
	}
	
	private CatDropList catList;
	
	private View catdropdown;
	
	public GridView getGridView() {
		return catList.getGridView();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.common_header, this);
		catList = (CatDropList) findViewById(R.id.catList);
		catList.setOnAdCallback(new OnAdCallback() {
			
			@Override
			public void onAdCallback(List<Result> results) {
				if((results == null || results.size() == 0) && !dd){
					catdropdown.setVisibility(View.GONE);
				}else{
					catdropdown.setVisibility(View.VISIBLE);
				}
				
			}
		});
		catList.setOnStateChange(this);
		catdropdown = findViewById(R.id.catdropdown);
		catdropdown.setOnClickListener(this);
	}
	
	private boolean dd = false;
	
	public void setAdapter(CatListAdater catListAdater){
		dd = true;
		catList.setAdapter(catListAdater);
		catdropdown.setVisibility(View.VISIBLE);

	}
	
	

	private void showDropDown(){
		catList.show();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.catdropdown:
			showDropDown();
			break;

		default:
			break;
		}
		
	}

	public void onShow() {
		catdropdown.setVisibility(View.GONE);
		
	}

	public void onDismiss() {
		catdropdown.setVisibility(View.VISIBLE);
		
	}
	
	

}
