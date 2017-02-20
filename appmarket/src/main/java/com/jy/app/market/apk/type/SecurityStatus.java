package com.jy.app.market.apk.type;

/**
 * 
 * APK安全状态
 *  
 * @author zzg.zhou(11039850@qq.com)
 */
public enum SecurityStatus {
	/**  
	 * 安全 
	 */
	SAFE,
	
	/**
	 * 有安全厂商认为有病毒，有安全厂商认为没有
	 */
	MAYSAFE,
	
	/**
	 * 未知，安全厂商还未返回扫描结果
	 */
	UNKNOWN,
	
	/**
	 * 危险
	 */
	DANGER
  
}
