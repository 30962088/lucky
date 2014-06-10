package com.mengle.lucky.network.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mengle.lucky.adapter.MsgListAdapter;
import com.mengle.lucky.adapter.MsgListAdapter.Msg;
import com.mengle.lucky.adapter.MsgListAdapter.Nofity;
import com.mengle.lucky.adapter.PhotoListAdapter.Photo;
import com.mengle.lucky.database.DataBaseHelper;

@DatabaseTable(tableName = "notice")
public class Notice {

	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField
	private String title;
	
	@DatabaseField
	private String content;
	
	@DatabaseField
	private String send_time;
	
	@DatabaseField
	private boolean checked = true;
	
	public Notice() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isChecked() {
		return checked;
	}
	
	public static List<Notice> getNotices(Context context) throws SQLException{
		List<Notice> list = new ArrayList<Notice>();
		DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
		list.addAll(dataBaseHelper.getNoticeDao().queryBuilder().orderBy("send_time", false).query());
		return list;
	}
	
	public static long getNewCount(Context context){
		long res = 0;
		DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
		try {
			res = dataBaseHelper.getNoticeDao().queryBuilder().where().eq("checked", true).countOf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  res;
	}
	
	public static List<MsgListAdapter.Msg> getModelList(Context context){
		List<MsgListAdapter.Msg> list = new ArrayList<MsgListAdapter.Msg>();
		try {
			for(Notice notice : getNotices(context)){
				list.add(notice.toModel());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public Nofity toModel(){
		Nofity nofity = new Nofity(id, title, checked);
		return nofity;
	}
	
	
	
	
}
