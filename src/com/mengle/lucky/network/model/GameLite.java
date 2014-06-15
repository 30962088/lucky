package com.mengle.lucky.network.model;

import com.mengle.lucky.adapter.Row2ListAdapter.Row2;
import com.mengle.lucky.utils.Utils;

public class GameLite {

	protected int id;
	protected String title;
	protected String stop_time;
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
		if(published == 1){
			str="答案已填写";
		}else{
			str = "答案未填写";
		}
		return new Row2(id, Utils.formatDay12Hour(stop_time)+"\n"+str, title);
	}
	
	
}
