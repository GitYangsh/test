package com.jy.app.market.idata.data;

import java.io.Serializable;

public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7359998240132317376L;

	private String phoneNum; // 手机号
	private String userId; // 用户id
	private String iconUrl; // 用户头像地址
	private String nickName; // 昵称
	private String sex; // 性别
	private String birthday; // 生日

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
