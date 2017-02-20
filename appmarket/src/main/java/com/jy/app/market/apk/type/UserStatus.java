package com.jy.app.market.apk.type;

/**
 * 用户状态
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
public enum UserStatus {
	DISABLED("禁用"),
	OK      ("正常"),
	LOCKED  ("锁定");
	
	private String title;
	
	private UserStatus(String title){
		this.title=title;
	}
	
	public String getTitle(){
		return title;
	}
}
