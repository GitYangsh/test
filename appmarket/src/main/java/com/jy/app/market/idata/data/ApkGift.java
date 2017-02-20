package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.List;

public class ApkGift implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Gift> gifts;   //apk的礼包列表

	public List<Gift> getGiftList() {
		return gifts;
	}

	public void setGiftList(List<Gift> giftList) {
		gifts = giftList;
	}

}
