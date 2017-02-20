package com.jy.app.market.idata.card;

import java.util.List;

import com.jy.app.market.idata.data.Apk;

/**
 * <b>APK推荐</b><br>
 *  
 *  相关的APK推荐, 一些文字说明+若干个APK(一般为3个): 如APK详情的"相关推荐"; 下载管理里面的"大家还下载了"
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardApkRecommend extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	/**
	 *  推荐的标题
	 */
	private String title;
	
	/**
	 * 0~3个APK
	 */
	private List<Apk> apks;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Apk> getApks() {
		return apks;
	}

	public void setApks(List<Apk> apks) {
		this.apks = apks;
	}
}
