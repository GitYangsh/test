package com.jy.app.market.idata.data;

import java.io.Serializable;

public class MyGiftCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -73481578041967968L;
	private Gift gift;
	private GiftCode giftCode;

	public GiftCode getGiftCode() {
		return giftCode;
	}

	public void setGiftCode(GiftCode giftCode) {
		this.giftCode = giftCode;
	}

	public Gift getGift() {
		return gift;
	}

	public void setGift(Gift gift) {
		this.gift = gift;
	}
}
