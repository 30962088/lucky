package com.mengle.lucky.wiget;

import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.DisplayUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class RadioGroupLayout extends FrameLayout implements OnClickListener{

	public static class RadioItem{
		private String value;
		private String name;
		public RadioItem(String value, String name) {
			super();
			this.value = value;
			this.name = name;
		}
	}
	
	private List<RadioItem> list;
	
	
	public RadioGroupLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RadioGroupLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RadioGroupLayout(Context context) {
		super(context);
		init();
	}
	
	private ViewGroup container;

	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.radio_group_layout, this);
		container = (ViewGroup) view.findViewById(R.id.container);
	}
	
	public void setList(List<RadioItem> list) {
		this.list = list;
		build();
	}
	
	private void build(View view,int k,RadioItem item){
		int res = 0;
		switch (k) {
		case 0:
			res = R.id.left;
			break;
		case 1:
			res = R.id.center;
			break;
		case 2:
			res = R.id.right;
			break;
		}
		View v = LayoutInflater.from(getContext()).inflate(R.layout.radio_layout, null);
		RadioButton button = (RadioButton) v.findViewById(R.id.radio);
		button.setOnClickListener(this);
		button.setTag(item.value);
		button.setText(item.name);
		((ViewGroup)view.findViewById(res)).addView(v);
	}
	
	private void build(){
		View view = null ;
		int j = 0;
		for(int i = 0;i<list.size();i++){
			
			if(i%3 == 0){
				j=0;
				view = LayoutInflater.from(getContext()).inflate(R.layout.radio_group_row_layout, null);
				
				container.addView(view);
				if(i/3>0){
					LinearLayout.LayoutParams params =  (LinearLayout.LayoutParams) view.getLayoutParams();
					params.topMargin = DisplayUtils.Dp2Px(getContext(), 10);
					view.setLayoutParams(params);
				}
			}
			build(view, j, list.get(i));
			j++;
		}
	}
	
	private RadioButton selectedView;
	
	

	@Override
	public void onClick(View v) {
		RadioButton button =(RadioButton) v;
		if(selectedView != null){
			selectedView.setChecked(false);
		}
		button.setChecked(true);
		selectedView = button;
		
	}
	
	public String getValue(){
		String value = null;
		if(selectedView != null && selectedView.getTag() != null ){
			value = selectedView.getTag().toString();
		}
		return value;
	}

}
