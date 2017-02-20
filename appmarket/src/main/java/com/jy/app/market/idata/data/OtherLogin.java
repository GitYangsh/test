package com.jy.app.market.idata.data;

import java.io.Serializable;

public class OtherLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6287773457967316588L;
	private String uid; // 第三方返回的uid
	private String userId; // 用户ID
	private String iconUrl; // 用户头像url
	private String nickName; // 昵称
	private String sex; // 性别
	private String birthday; // 生日
	private String type; // 第三方登录类型 :qq,SinaWeiBo,WeiXin
	private String phoneNum; // 手机号，如果绑定了手机，返回手机号

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}
