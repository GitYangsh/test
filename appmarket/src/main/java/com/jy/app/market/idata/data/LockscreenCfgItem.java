package com.jy.app.market.idata.data;

import java.io.Serializable;

public class LockscreenCfgItem implements Serializable {
	private static final long serialVersionUID = 5045505281818080227L;
	private String title;
	private long startTime;
	private long endTime;
	private Link link;
	private String linkIconNormal;
	private String linkIconPressed;
	private String linkIconSelected;
	private String image;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	
	public String getLinkIconNormal() {
		return linkIconNormal;
	}

	public void setLinkIconNormal(String linkIconNormal) {
		this.linkIconNormal = linkIconNormal;
	}

	public String getLinkIconPressed() {
		return linkIconPressed;
	}

	public void setLinkIconPressed(String linkIconPressed) {
		this.linkIconPressed = linkIconPressed;
	}

	public String getLinkIconSelected() {
		return linkIconSelected;
	}

	public void setLinkIconSelected(String linkIconSelected) {
		this.linkIconSelected = linkIconSelected;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}