package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.adapter.CommentModel;
import com.mengle.lucky.adapter.CommentModel.Reply;
import com.mengle.lucky.network.UserRank.Result;

import android.content.Context;
import android.text.TextUtils;

public class GameCommentRequest extends Request{

	public static class Params{
		protected int uid;
		protected String token;
		protected int game_id;
		protected int start;
		protected int limit;
		public Params(int uid, String token, int game_id, int start, int limit) {
			super();
			this.uid = uid;
			this.token = token;
			this.game_id = game_id;
			this.start = start;
			this.limit = limit;
		}
		
	}
	
	public static class Result{
		private int comment_id;
		private int uid;
		private String nickname;
		private String avatar;
		private int replay_uid;
		private String reply_nickname;
		private String reply_avatar;
		private String content;
		private String create_time;
		public int getComment_id() {
			return comment_id;
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
		public int getReplay_uid() {
			return replay_uid;
		}
		public String getReply_nickname() {
			return reply_nickname;
		}
		public String getReply_avatar() {
			return reply_avatar;
		}
		public String getContent() {
			return content;
		}
		public String getCreate_time() {
			return create_time;
		}
		public CommentModel toCommentModel(){
			final Reply reply = new Reply(comment_id, reply_nickname, content);
			CommentModel commentModel = new CommentModel(comment_id,uid, avatar, nickname, create_time, new ArrayList<CommentModel.Reply>(){{
				add(reply);
			}});
			return commentModel;
		}
		public static List<CommentModel> toCommentModelList(List<Result> results){
			List<CommentModel> list = new ArrayList<CommentModel>();
			for(Result result:results){
				list.add(result.toCommentModel());
			}
			return list;
		}
	}
	
	private Params params;
	
	private List<Result> results = new ArrayList<GameCommentRequest.Result>();
	
	public GameCommentRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}
	
	public List<Result> getResults() {
		return results;
	}

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			results = new Gson().fromJson(data, new TypeToken<List<Result>>(){}.getType());
		}
		
	}

	@Override
	public void onError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResultError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return HOST+"game/comment/get/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
