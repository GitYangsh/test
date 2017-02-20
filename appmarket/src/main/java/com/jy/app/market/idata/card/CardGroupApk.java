package com.jy.app.market.idata.card;

import java.util.List;

import com.jy.app.market.idata.data.Apk;

/**
 * <b>分组榜单</b><br>
 * 这种形式的榜单主要表现在一个榜单中的App可以分为不同的组别
 *  
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardGroupApk extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	/**
	 * 榜单ID
	 */
	private int topId;
	
	/**
	 * 榜单名称
	 */
	private String title; 
	
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

	public int getTopId() {
		return topId;
	}

	public void setTopId(int topId) {
		this.topId = topId;
	}
}
