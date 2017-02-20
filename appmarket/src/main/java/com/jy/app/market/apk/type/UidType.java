package com.jy.app.market.apk.type;

/**
 * 用户系统类型
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public enum UidType {
	JY("椒盐"),
	QQ("QQ"),
	WEI_XIN("微信"), 
	SINA_WB("新浪微博");	
	
	private String title;
	
	private UidType(String title){
		this.title=title;
	}
	
	public String getTitle(){
		return title;
	}
}
