package com.jy.app.market.idata.data;

import java.io.Serializable;

public class Ad implements Serializable {
	private static final long serialVersionUID = -996866930626846660L;
	 
	private String image;
 
	private Link link;
  

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}