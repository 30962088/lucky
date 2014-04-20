package com.mengle.lucky.adapter;

import java.util.List;

public class CommentModel {

	private String photo;
	
	private String name;
	
	private String time;
	
	public static class Reply{
		private String id;
		private String name;
		private String content;
		public Reply(String id, String name,String content) {
			super();
			this.id = id;
			this.name = name;
			this.content = content;
		}
		
		public String getId() {
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
	
	

	public CommentModel(String photo, String name, String time, List<Reply> reply) {
		this.photo = photo;
		this.name = name;
		this.time = time;
		this.reply = reply;
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
