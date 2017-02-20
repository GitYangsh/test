package com.jy.app.market.idata.data;

import java.io.Serializable;

public class Top implements Serializable{ 
	private static final long serialVersionUID = 548806411310064004L;

	/**
	 * 榜单图的URL
	 */
	private String imageUrl;
	
    /**
     * 榜单名称
     */
    private String title;
    
    /**
     * 榜单数据地址
     */
    private Link link;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

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
}
