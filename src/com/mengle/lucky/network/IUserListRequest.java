package com.mengle.lucky.network;


import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.wiget.UserItemView.Model;

public interface IUserListRequest extends IRequest{

	public static class Result{
		private int uid;
		private int followed;
		private String avatar;
		private String nickname;
		private String title;
		private int gold_coin;
		private int gold_ingot;
		private int win_count;
		private int fail_count;
		private int lost_count;
		public int getUid() {
			return uid;
		}
		public String getNickname() {
			return nickname;
		}
		public String getTitle() {
			return title;
		}
		public int getGold_coin() {
			return gold_coin;
		}
		public int getGold_ingot() {
			return gold_ingot;
		}
		public int getWin_count() {
			return win_count;
		}
		public int getFail_count() {
			return fail_count;
		}
		public int getLost_count() {
			return lost_count;
		}
		public String getAvatar() {
			return avatar;
		}
		public Model toModel(){
			Model model = new Model(uid, nickname, avatar, title, gold_coin, win_count, lost_count, fail_count, followed==1?true:false);
			return model;
		}
		
		public static List<Model> toModels(List<Result> results){
			List<Model> list = new ArrayList<Model>();
			for(Result result:results){
				list.add(result.toModel());
			}
			return list;
		}
		
	}
	
	public List<Result> getResults();
	
	public Status getStatus();
	
}
