package com.mengle.lucky.network.model;

import com.mengle.lucky.adapter.Row2ListAdapter.Row2;

public class GameLite {

	protected int id;
	protected String title;
	protected String stop_time;
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
		return new Row2(id, stop_time, title);
	}
	
	
}
