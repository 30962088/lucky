package com.mengle.lucky.network.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.mengle.lucky.adapter.QuestionAdapter;
import com.mengle.lucky.adapter.QuestionAdapter.QuestionItem;
import com.mengle.lucky.wiget.QuestionLayout;

public class Game {

	/**
	 * 	id	Int	是	游戏的Id
		is_day	int	是	是否今日猜,小编出题
		cid	string	是	类别Id
		title	String	是	游戏的标题
		image	String	否	游戏的背景图的URL
		stop_time	String	是	结束投注时间
		create_time	String	是	创建时间
		now	String	是	当前时间
		gold_coin	Int	是	金币基数
		odds	String	否	倍数
		win_option	Int	是	正确选项;0是未指定 >0 为答案的ID
		join_count	String	否	参与人数
		is_publish_answer	Int	是	是否发布答案 1是 0 否
		creator	String	是	创建者信息[{"uid":"1","nickname":"guiwh","avatar":"http://"}]
		请自行反解析
		opts	String	是	答案信息
		[{"id":"10","content":"会","select_count":"0","is_lock":"0","is_answer":"0"},{"id":"20","content":"不会","select_count":"0","is_lock":"0","is_answer":"0"}]
		content:内容
		id,答案选项的编号
		select_count,选择这个答案的人数
		is_lock:不允许用户选择,现在都是0,允许用户选择,为以后需求准备
		is_answer:是不是答案

	 */
	
	public static class Creator{
		private String uid;
		private String nickname;
		private String avatar;
		public String getUid() {
			return uid;
		}
		public String getNickname() {
			return nickname;
		}
		public String getAvatar() {
			return avatar;
		}
		
	}
	
	public static class Option{
		private int id;
		private String content;
		private int select_count;
		private int is_lock;
		private int is_answer;
		public int getId() {
			return id;
		}
		public String getContent() {
			return content;
		}
		public int getSelect_count() {
			return select_count;
		}
		public int getIs_lock() {
			return is_lock;
		}
		public int getIs_answer() {
			return is_answer;
		}
		
		public QuestionItem toQuestionItem(int i,int total){
			String no = new String[]{"A","B","C","D","E","F","G"}[i];
			String color = new String[]{"#a0d468","#4fc0e8","#e47134","#e47134","#e47134","#e47134","#e47134","#e47134","#e47134","#e47134"}[i];
			QuestionItem item = new QuestionItem(id, no, content, (1.0*select_count)/(1.0*total) , Color.parseColor(color), QuestionLayout.TYPE_NORMAL);
			return item;
		}
		
	}
	
	protected int id;
	
	protected int is_day;
	
	protected String cid;
	
	protected String title;
	
	protected String image;
	
	protected String stop_time;
	
	protected String create_time;
	
	protected String now;
	
	protected int gold_coin;
	
	protected String odds;
	
	protected int win_option;
	
	protected int join_count;
	
	protected int is_publish_answer;
	
	protected List<Creator> creator;
	
	protected List<Option> opts;

	public int getId() {
		return id;
	}

	public int getIs_day() {
		return is_day;
	}

	public String getCid() {
		return cid;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
	}

	public String getStop_time() {
		return stop_time;
	}

	public String getCreate_time() {
		return create_time;
	}

	public String getNow() {
		return now;
	}

	public int getGold_coin() {
		return gold_coin;
	}

	public String getOdds() {
		return odds;
	}

	public int getWin_option() {
		return win_option;
	}

	public int getJoin_count() {
		return join_count;
	}

	public int getIs_publish_answer() {
		return is_publish_answer;
	}

	public List<Creator> getCreator() {
		return creator;
	}

	public List<Option> getOptions() {
		return opts;
	}
	
	public List<QuestionItem> toQuestionList(){
		List<QuestionItem> list = new ArrayList<QuestionAdapter.QuestionItem>();
		for(int i = 0;i<getOptions().size();i++){
			list.add(getOptions().get(i).toQuestionItem(i, join_count));
		}
		return list;
		
	}
	
	
	
}
