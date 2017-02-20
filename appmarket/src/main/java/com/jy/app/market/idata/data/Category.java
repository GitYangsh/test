package com.jy.app.market.idata.data;

import java.io.Serializable;

public class Category implements Serializable{ 
	private static final long serialVersionUID = 548806411310064004L;
	/**
	 * 分类名称
	 */
	private String name;
	
	/**
	 * 获取该分类应用列表的URL
	 */
	private String url;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
