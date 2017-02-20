package com.jy.app.market.idata.data;

import java.io.Serializable;

public class Upgrade implements Serializable {
	private static final long serialVersionUID = 548806411310064004L;
	/**
	 * 最新版本号
	 */
	private int versionCode;

	/**
	 * 图标URL
	 */
	private String icon;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 详细描述
	 */
	private String detail;

	/**
	 * 下载URL
	 */
	private String url;

	/**
	 * 卡片显示位置
	 */
	private int index;
	/**
	 * md5
	 */
	private String md5;

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
}
