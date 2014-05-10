package com.mengle.lucky.utils;

import java.io.Serializable;

/**
 * 令牌
 * 
 * @author isaacxie
 * 
 */
public class WeiboToken implements Serializable{

	/** AccessToken */
	public String accessToken;

	/** AccessToken过期时间 */
	public long expiresIn;

	/** 用来换新的AccessToken */
	public String refreshToken;

	/** 兼容OpenID协议 */
	public String openID;

	/** OmasToken */
	public String omasToken;

	/** omasKey */
	public String omasKey;

}
