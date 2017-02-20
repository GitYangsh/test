package com.jy.app.market.idata.data;

import java.io.Serializable;

public class Drift implements Serializable {
	private static final long serialVersionUID = -2380797330199650748L;

	private String title;
	/** 图片地址 */
	private String iconUrl;
	/** 开始显示时间 */
	private long startTime;
	/** 停止显示时间 */
	private long endTime;
	private Link link;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

}