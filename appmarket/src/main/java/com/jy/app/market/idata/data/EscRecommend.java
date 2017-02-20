package com.jy.app.market.idata.data;

import java.io.Serializable;

public class EscRecommend implements Serializable {
	private static final long serialVersionUID = -996866930626846660L;
	/** 显示几率:<=0.永远不显示;>=1显示几率为1/chance */
	private int chance;
	private long startTime;
	private long endTime;
	/** 显示在屏幕上的banner图URL */
	private String bannerUrl;

	private Download download;
	private Link link;

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
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

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public Download getDownload() {
		return download;
	}

	public void setDownload(Download download) {
		this.download = download;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

}