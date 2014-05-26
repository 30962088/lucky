package com.mengle.lucky.network;

import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.model.User;
import com.mengle.lucky.wiget.UserHeadView.UserHeadData;

public interface IUserGet extends IRequest{

	public static class UserResult extends User{
		protected int is_following;
		public int getIs_following() {
			return is_following;
		}
		@Override
		public UserHeadData toUserHeadData() {
			UserHeadData data = super.toUserHeadData();
			data.setFallow(is_following==1?true:false);
			return data;
		}
	}
	
	public UserResult getUserResult();
	
	public Status getStatus();
	
}
