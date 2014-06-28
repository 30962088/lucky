package com.mengle.lucky.network.model;

import android.graphics.Color;

import com.mengle.lucky.adapter.Row2ListAdapter.Row2;
import com.mengle.lucky.utils.Utils;

public class GameLite {

	protected int id;
	protected String title;
	protected String stop_time;
	protected int state;
	protected int published;
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getStop_time() {
		return stop_time;
	}
	
	public Row2 toRow2(){
		return new Row2(id, Utils.formatDay12Hour(stop_time), title);
	}
	
	public Row2 toRow222(){
		String str = "";
		int color = 0;
		if(state == 0){
			str = "未审核";
			color = Color.parseColor("#5dc9e6");
		}else{
			if(published == 1){
				color = Color.parseColor("#768082");
				str=Utils.formatDay12Hour(stop_time)+"\n"+"答案已填写";
			}else{
				color = Color.parseColor("#e47033");
				str = Utils.formatDay12Hour(stop_time)+"\n"+"答案未填写";
			}
		}
		
		return new Row2(id, str, title,color);
	}
	
	
}
