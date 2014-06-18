package com.mengle.lucky.network.model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mengle.lucky.App;
import com.mengle.lucky.database.DataBaseHelper;

@DatabaseTable(tableName = "Award")
public class Award {

	@DatabaseField(id = true)
	private String date;
	
	@DatabaseField
	private int award;
	
	public Award() {
		// TODO Auto-generated constructor stub
	}

	public Award(String date, int award) {
		super();
		this.date = date;
		this.award = award;
	}
	
	
	public String getDate() {
		return date;
	}
	public int getAward() {
		return award;
	}
	
	private static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	public static void setAward(int award){
		String date = formatDate(new Date());
		DataBaseHelper helper = new DataBaseHelper(App.getInstance());
		Award award2 = new Award(date, award);
		try {
			helper.getAwardDao().createOrUpdate(award2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isAward(){
		String date = formatDate(new Date());
		DataBaseHelper helper = new DataBaseHelper(App.getInstance());
		Award award = null;
		try {
			award = helper.getAwardDao().queryForId(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(award == null){
			return false;
		}
		return true;
	}
	
}
