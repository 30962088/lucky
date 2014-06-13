package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.adapter.StageAdapter.Stage;
import com.mengle.lucky.adapter.ZhuangListAdapter.ZhuangModel;
import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.model.Game;
import com.mengle.lucky.network.model.User;
import com.mengle.lucky.utils.Utils;

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
		public Result() {
			// TODO Auto-generated constructor stub
		}
		public ZhuangModel toZhuangModel(){
			
			ZhuangModel model = new ZhuangModel(id, title, avatar, join_count,Utils.format12Hour(stop_time)+" 结束");
			return model;
		}
		
		public Stage toStage(){
			return new Stage(id,title, image, Utils.format12Hour(stop_time)+" 结束", join_count);
		}
		
		public static List<Stage> toStageList(List<Result> list){
			List<Stage> stages = new ArrayList<Stage>();
			for(Result result:list){
				stages.add(result.toStage());
			}
			return stages;
		}
		
		public static List<ZhuangModel> toZhuangModelList(List<Result> results){
			List<ZhuangModel>  list = new ArrayList<ZhuangModel>();
			for(Result result:results){
				list.add(result.toZhuangModel());
			}
			return list;
		}
		public int getId() {
			return id;
		}
		public String getTitle() {
			return title;
		}
		public String getImage() {
			return image;
		}
		public int getJoin_count() {
			return join_count;
		}
		public String getStop_time() {
			return stop_time;
		}
		public int getUid() {
			return uid;
		}
		public String getNickname() {
			return nickname;
		}
		public String getAvatar() {
			return avatar;
		}
		
		
		
	}
	
}
