package com.mengle.lucky.network.model;


import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mengle.lucky.utils.Utils;

@DatabaseTable(tableName = "msgMe")
public class MsgMe {
	
	@DatabaseField(id = true)
	private String id;
	
	@DatabaseField
	private int toUid;
	
	
	@DatabaseField
	private String content;
	
	
	@DatabaseField
	private long send_time;


	@SuppressWarnings("deprecation")
	public MsgMe(int toUid, String content, Date send_time) {
		super();
		this.id = ""+new Date().getTime();
		this.toUid = toUid;
		this.content = content;
		this.send_time = send_time.getTime();
	}
	
	public MsgMe() {
		// TODO Auto-generated constructor stub
	}
	
	public String getContent() {
		return content;
	}
	public String getId() {
		return id;
	}
	public long getSend_time() {
		return send_time;
	}
	public int getToUid() {
		return toUid;
	}
	
	
	
}
