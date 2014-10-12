package com.mengle.lucky.network.model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mengle.lucky.App;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.network.GameLibraryDayChanceRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.GameLibraryDayChanceRequest.Callback;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.Preferences;


@DatabaseTable(tableName = "chance")
public class Chance {

	@DatabaseField(id = true)
	private String date;
	@DatabaseField
	private int count;

	public Chance(Date date, int count) {
		super();
		this.date = dateToString(date);
		this.count = count;
	}
	
	public Chance() {
		// TODO Auto-generated constructor stub
	}
	
	private static String dateToString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	public void save(){
		DataBaseHelper helper = new DataBaseHelper(App.getInstance());
		try {
			helper.getChanceDao().createOrUpdate(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Integer getChance(Date date){
		Integer chance = null;
		DataBaseHelper helper = new DataBaseHelper(App.getInstance());
		try {
			Chance chance2 = helper.getChanceDao().queryForId(dateToString(date));
			if(chance2 != null){
				chance = chance2.count;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chance;
	}
	
	public static void updateChance(int count){
		new Chance(new Date(),count).save();
	}
	
	
	public static void getChance(Context context,final Callback callback){
		Preferences.User user = new Preferences.User(context);
 		Integer chance = Chance.getChance(new Date());
		if(chance != null){
			callback.onChanceCount(chance);
		}else{
			final GameLibraryDayChanceRequest dayChanceRequest = new GameLibraryDayChanceRequest(context,
					new GameLibraryDayChanceRequest.Params(user.getUid(),user.getToken()));
			RequestAsync.request(dayChanceRequest, new Async() {
				
				@Override
				public void onPostExecute(Request request) {
				 	int chance =  dayChanceRequest.getChange();
				 	new Chance(new Date(), chance).save();
				 	callback.onChanceCount(chance);
					
				}
			});
		}
	}
	
	
}
