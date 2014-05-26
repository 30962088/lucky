package com.mengle.lucky.adapter;

import java.util.List;

public class CommentModel {

	private int id;
	
	private String photo;
	
	private String name;
	
	private String time;
	
	private int uid;
	
	public static class Reply{
		private int id;
		private String name;
		private String content;
		public Reply(int id, String name,String content) {
			super();
			this.id = id;
			this.name = name;
			this.content = content;
		}
		
		public int getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		public String getContent() {
			return content;
		}
	}
	
	private List<Reply> reply;
	
	

	public CommentModel(int id,int uid,String photo, String name, String time, List<Reply> reply) {
		this.id = id;
		this.uid = uid;
		this.photo = photo;
		this.name = name;
		this.time = time;
		this.reply = reply;
	}
	
	public int getId() {
		return id;
	}
	
	public int getUid() {
		return uid;
	}

	public String getPhoto() {
		return photo;
	}

	public String getName() {
		return name;
	}

	public String getTime() {
		return time;
	}

	public List<Reply> getReply() {
		return reply;
	}
	
	
	
	
	
	
}
