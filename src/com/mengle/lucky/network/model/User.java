package com.mengle.lucky.network.model;

import java.util.List;

import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.wiget.UserHeadView.Sex;
import com.mengle.lucky.wiget.UserHeadView.UserHeadData;

/**
 * 
 uid Int 是 用户的Id nickname string 是 用户的昵称 title String 是 用户头衔 avatar String 否
 * 头像地址 head String 否 头图地址(如果为空,请选择默认头图) gender Int 是 性别 1:男;0:女;-1未知/保密 qq
 * String 否 用户的QQ号码 mobile String 否 用户的手机号码 email String 否 用户的email province Int
 * 是 城市Id,0是未设置 city Int 是 城市Id,0是未设置 gold_coin Int 是 金币数量 gold_ingot Int 是
 * 金元宝数量 create_count Int 是 创建游戏的数量 join_count Int 是 参加的游戏数量 win_count Int 是
 * 参加游戏并且胜利的数量 fail_count Int 是 参加游戏并且失败的数量 lost_count Int 是 创建游戏的流局数量
 * followings Int 是 关注了多少人 followers Int 是 被关注多少人(粉丝数量) create_time String 是
 * 创建时间 last_login_time String 是 最后一次登录时间 sns Array 是
 * 用户的SNS信息,实际上一个json字符串([{"via"
 * :"weibo","openid":"v1"},{"via":"tqq","openid":"v2"}]);但是反解析之后是一个数组如下:
 * array(array('uid':'weibo','openid':'v1'),array('uid':'tqq','openid':'v2'))
 * 
 */

public class User {

	public static class SNS {
		private String openid;
		private String via;

		public String getOpenid() {
			return openid;
		}

		public String getVia() {
			return via;
		}
	}

	protected int uid; // 用户的Id

	protected String nickname; // 用户的昵称

	protected String title;// 用户头衔

	protected String avatar;// 头像地址

	protected String head;// 头图地址(如果为空,请选择默认头图)

	protected int gender; // 头图地址(如果为空,请选择默认头图)

	protected String qq;// 头图地址(如果为空,请选择默认头图)

	protected String mobile;// 用户的手机号码

	protected String email;// 用户的email

	protected int province;// 城市Id,0是未设置

	protected int city;// 城市Id,0是未设置

	protected int gold_coin;// 金币数量

	protected int gold_ingot;// 金元宝数量

	protected int create_count;// 创建游戏的数量

	protected int join_count;// 参加的游戏数量

	protected int win_count;// 参加游戏并且胜利的数量

	protected int fail_count;// 参加游戏并且失败的数量

	protected int lost_count;// 创建游戏的流局数量

	protected int followings;// 关注了多少人
	protected int followers;// 被关注多少人(粉丝数量)
	protected String create_time;// 创建时间
	protected String last_login_time;// 最后一次登录时间
	protected List<SNS> sns;// 用户的SNS信息,实际上一个json字符串

	public int getUid() {
		return uid;
	}

	public String getNickname() {
		return nickname;
	}

	public String getTitle() {
		return title;
	}

	public String getAvatar() {
		return BitmapLoader.getPhoto(avatar);
	}

	public String getHead() {
		return BitmapLoader.getHead(head);
	}

	public int getGender() {
		return gender;
	}

	public String getQq() {
		return qq;
	}

	public String getMobile() {
		return mobile;
	}

	public String getEmail() {
		return email;
	}

	public int getProvince() {
		return province;
	}

	public int getCity() {
		return city;
	}

	public int getGold_coin() {
		return gold_coin;
	}

	public int getGold_ingot() {
		return gold_ingot;
	}

	public int getCreate_count() {
		return create_count;
	}

	public int getJoin_count() {
		return join_count;
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

	public int getFollowings() {
		return followings;
	}

	public int getFollowers() {
		return followers;
	}

	public String getCreate_time() {
		return create_time;
	}

	public String getLast_login_time() {
		return last_login_time;
	}
	
	public List<SNS> getSns() {
		return sns;
	}
	
	
	public UserHeadData toUserHeadData(){
		Sex sex;
		switch (gender) {
		case 0:
			sex = Sex.FEMALE;
			break;
		case 1:
			sex = Sex.MALE;
			break;
		default:
			sex = Sex.FEMALE;
			break;
		}
		UserHeadData data = new UserHeadData(uid,false, getAvatar(),getHead(), nickname,sex , gold_coin, win_count, lost_count, fail_count, title, followings, followers, 0);
		return data;
	}

}
