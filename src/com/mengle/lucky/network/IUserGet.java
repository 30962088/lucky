package com.mengle.lucky.network;

import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.model.User;

public interface IUserGet extends IRequest{

	public User getUser();
	
	public Status getStatus();
	
}
