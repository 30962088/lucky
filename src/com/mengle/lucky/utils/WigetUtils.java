package com.mengle.lucky.utils;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


public class WigetUtils {

	public static interface OnItemClickListener{
		public void onItemClick(ViewGroup group,View view,int position,long id);
	}
	
	public static void onChildViewClick(final ViewGroup viewGroup,final OnItemClickListener itemClickListener){
		for(int i = 0;i<viewGroup.getChildCount();i++){
			final int k = i;
			viewGroup.getChildAt(i).setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					itemClickListener.onItemClick( viewGroup, v, k, v.getId());
					
				}
			});
		}
	}
	
	public static void setSelected(ViewGroup viewGroup,boolean selected){
		for(int i = 0;i<viewGroup.getChildCount();i++){
			viewGroup.getChildAt(i).setSelected(selected);
		}
	}
	
}
