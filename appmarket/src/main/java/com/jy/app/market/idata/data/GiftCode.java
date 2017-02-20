package com.jy.app.market.idata.data;

import java.io.Serializable;

public class GiftCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4775487351628296120L;
	private String code; // 礼包兑换码

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
