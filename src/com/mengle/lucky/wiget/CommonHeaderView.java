package com.mengle.lucky.wiget;


import com.mengle.lucky.R;
import com.mengle.lucky.adapter.CatListAdater;
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
		catList.setOnStateChange(this);
		catdropdown = findViewById(R.id.catdropdown);
		catdropdown.setOnClickListener(this);
	}
	
	public void setAdapter(CatListAdater catListAdater){
		catList.setAdapter(catListAdater);

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
