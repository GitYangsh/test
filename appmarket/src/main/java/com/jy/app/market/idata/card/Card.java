package com.jy.app.market.idata.card;

import java.io.Serializable;


public abstract class Card implements Serializable{ 
	private static final long serialVersionUID = 548806411310064004L;

	private String type;
	
	public Card(){
		this.type=this.getClass().getSimpleName();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	  
	
}
