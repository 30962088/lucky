package com.mengle.lucky.network.model;

import java.sql.SQLException;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mengle.lucky.App;
import com.mengle.lucky.database.DataBaseHelper;

@DatabaseTable(tableName = "Tousu")
public class Tousu {
	@DatabaseField(id=true)
	private String id;
	@DatabaseField
	private Integer uid;
	@DatabaseField
	private int game;
	@DatabaseField
	private boolean tousu;
	public Tousu(Integer uid, int game, boolean tousu) {
		if(uid == null){
			uid = 0;
		}
		id = ""+new Date().getTime();
		this.uid = uid;
		this.game = game;
		this.tousu = tousu;
	}
	public Tousu() {
		// TODO Auto-generated constructor stub
	}
	
	public void save(){
		DataBaseHelper helper = new DataBaseHelper(App.getInstance());
		try {
			helper.getTousuDao().createOrUpdate(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean getTousu(Integer uid,int game){
		if(uid == null){
			uid = 0;
		}
		boolean tousu = false;
		DataBaseHelper helper = new DataBaseHelper(App.getInstance());
		try {
			Tousu t = helper.getTousuDao().queryBuilder().where().eq("uid", uid).and().eq("game",game).queryForFirst();
			if(t != null){
				tousu = t.tousu;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tousu;
	}
	
}
