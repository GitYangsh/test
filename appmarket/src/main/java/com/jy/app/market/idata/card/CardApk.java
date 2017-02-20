package com.jy.app.market.idata.card;

import com.jy.app.market.idata.data.Apk;
 
/**
 * <b>基本应用</b><br>
 * APK的信息, 简要信息: 如搜索结果第2条以后的记录<br><br>
 * 参考文档:  http://dept.aijiaoyan.com:8888/redmine/projects/market/wiki/%E9%80%9A%E8%AE%AF%E5%8D%8F%E8%AE%AE#1基本应用Apk 
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardApk extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	private Apk apk;

	public CardApk(){
		
	}
	
	public CardApk(Apk apk){
		this.apk=apk;
	}

	public Apk getApk() {
		return apk;
	}

	public void setApk(Apk apk) {
		this.apk = apk;
	}
}
