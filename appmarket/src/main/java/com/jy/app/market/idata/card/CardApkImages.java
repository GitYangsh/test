package com.jy.app.market.idata.card;

import com.jy.app.market.idata.data.Apk;

/**
 * <b>截图应用</b><br>
 * APK的信息, 屏幕截图: 如搜索结果的第1条
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardApkImages extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	private Apk apk;

	public Apk getApk() {
		return apk;
	}

	public void setApk(Apk apk) {
		this.apk = apk;
	}
}
