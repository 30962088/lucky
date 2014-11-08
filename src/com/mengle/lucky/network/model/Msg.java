package com.mengle.lucky.network.model;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.mengle.lucky.adapter.MsgListAdapter;
import com.mengle.lucky.adapter.MsgListAdapter.Message;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.utils.Utils;


@DatabaseTable(tableName = "msg")
public class Msg {

	public static class Sender{
		protected int uid;
		protected String avatar;
		protected String nickname;
		public String getAvatar() {
			return avatar;
		}
		
	}
	
	@DatabaseField(id = true)
	protected int id;
	
	@DatabaseField
	protected String content;
	
	@DatabaseField
	protected long send_time;
	
	@DatabaseField
	@Expose(deserialize=false,serialize=false)
	protected String sender;
	
	@DatabaseField
	protected boolean checked = true;
	
	@DatabaseField
	protected boolean deleted = false;
	
	public int getId() {
		return id;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public boolean isDeleted() {
		return deleted;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public String getContent() {
		return content;
	}
	public String getSend_time() {
		return Utils.formatDate(new Date(send_time));
	}
	public Sender getSender() {
		return new Gson().fromJson(sender, Sender.class) ;
	}
	
	public static List<Msg> getMsg(Context context) throws SQLException{
		List<Msg> list = new ArrayList<Msg>();
		DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
		QueryBuilder<Msg, Integer> queryBuilder = dataBaseHelper.getMsgDao().queryBuilder();
		queryBuilder.where().eq("deleted", false);
		list.addAll(queryBuilder.orderBy("send_time", false).query());
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
	
	public static long getNewCount(Context context){
		long res = 0;
		DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
		try {
			res = dataBaseHelper.getMsgDao().queryBuilder().where().eq("checked", true).and().eq("deleted", false).countOf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  res;
	}
	
	public Message toModel(){
		Sender sender = getSender();
		Message nofity = new Message(id, content,sender.nickname, checked,sender.uid);
		return nofity;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
