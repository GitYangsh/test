package com.jy.app.market.idata.card;

import java.util.List;

import com.jy.app.market.idata.data.Apk;
import com.jy.app.market.idata.data.Link;

/**
 * <b>榜单排行</b><br>
 * 
 * 排行卡片：用于展示某个排行榜, 排行榜名称以及若干的APK(带排序号)
 *  
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardGroupApkTop extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	/**
	 * 榜单ID
	 */
	private int topId;
	
	/**
	 * 排行榜名称
	 */
	private String title;
	
	/**
	 * 数据链接地址
	 */
	private Link link;
	
	/**
	 * APK列表
	 */
	private List<Apk> apks;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public List<Apk> getApks() {
		return apks;
	}

	public void setApks(List<Apk> apks) {
		this.apks = apks;
	}

	public int getTopId() {
		return topId;
	}

	public void setTopId(int topid) {
		this.topId = topid;
	}
}
