package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.adapter.ZhuangListAdapter.ZhuangModel;
import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.model.Game;
import com.mengle.lucky.network.model.User;

public interface IGameGet extends IRequest{
	public List<Result> getResult();
	
	public Status getStatus();
	
	public void request(int offset,int limit);
	
	public static class Result{
		private int id;
		private String title;
		private String image;
		private int join_count;
		private String stop_time;
		private int uid;
		private String nickname;
		private String avatar;
		public Result(int id, String title, String image, int join_count,
				String stop_time, int uid, String nickname, String avatar) {
			super();
			this.id = id;
			this.title = title;
			this.image = image;
			this.join_count = join_count;
			this.stop_time = stop_time;
			this.uid = uid;
			this.nickname = nickname;
			this.avatar = avatar;
		}
		public ZhuangModel toZhuangModel(){
			ZhuangModel model = new ZhuangModel(uid, title, avatar, join_count, stop_time);
			return model;
		}
		public static List<ZhuangModel> toZhuangModelList(List<Result> results){
			List<ZhuangModel>  list = new ArrayList<ZhuangModel>();
			for(Result result:results){
				list.add(result.toZhuangModel());
			}
			return list;
		}
		
	}
	
}
