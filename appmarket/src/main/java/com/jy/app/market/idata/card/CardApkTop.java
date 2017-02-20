package com.jy.app.market.idata.card;

import com.jy.app.market.idata.data.Apk;

/**
 * <b>排行应用</b><br>
 * APK的信息, 带排序号: 如排行榜下的APK
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardApkTop extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	/**
	 * 该APK在榜单的排序号: 1,2,3 ...
	 */
	private int     top;
	private Apk apk;

	public Apk getApk() {
		return apk;
	}

	public void setApk(Apk apk) {
		this.apk = apk;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}
}
