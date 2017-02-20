package com.jy.app.market.idata.data;

import java.io.Serializable;

public class GiftDescription implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4815158112872258429L;

	private String title; // 描述title
	private String description; // 描述内容

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
