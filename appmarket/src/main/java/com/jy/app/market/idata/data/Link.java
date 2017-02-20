package com.jy.app.market.idata.data;

import java.io.Serializable;

import com.jy.app.market.apk.type.LinkType;


public class Link implements Serializable{ 
	private static final long serialVersionUID = 548806411310064004L;

	/**
	 * 链接到新界面的标题
	 */
	private String title;
	  
	/**
	 * URL链接
	 */
	private String url;
	
	/**
	 * url打开的形式
	 */
	private LinkType type;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
 
	 
	public LinkType getType() {
		return type;
	}

	public void setType(LinkType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
