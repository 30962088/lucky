package com.mengle.lucky.network.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mengle.lucky.adapter.MsgListAdapter;
import com.mengle.lucky.adapter.MsgListAdapter.Message;
import com.mengle.lucky.adapter.MsgListAdapter.Nofity;
import com.mengle.lucky.database.DataBaseHelper;


@DatabaseTable(tableName = "msg")
public class Msg {

	public static class Sender{
		protected int uid;
		protected String avatar;
		protected String nickname;
		
	}
	
	@DatabaseField(id = true)
	protected int id;
	
	@DatabaseField
	protected String content;
	
	@DatabaseField
	protected String send_time;
	
	@DatabaseField
	protected String sender;
	
	@DatabaseField
	protected boolean checked = true;
	
	public int getId() {
		return id;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public String getContent() {
		return content;
	}
	public String getSend_time() {
		return send_time;
	}
	public Sender getSender() {
		return new Gson().fromJson(sender, Sender.class) ;
	}
	
	public static List<Msg> getMsg(Context context) throws SQLException{
		List<Msg> list = new ArrayList<Msg>();
		DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
		list.addAll(dataBaseHelper.getMsgDao().queryForAll());
		return list;
	}
	
	public static List<MsgListAdapter.Msg> getModelList(Context context){
		List<MsgListAdapter.Msg> list = new ArrayList<MsgListAdapter.Msg>();
		try {
			for(Msg msg : getMsg(context)){
				list.add(msg.toModel());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public Message toModel(){
		Message nofity = new Message(id, content,getSender().nickname, checked);
		return nofity;
	}
	
}
