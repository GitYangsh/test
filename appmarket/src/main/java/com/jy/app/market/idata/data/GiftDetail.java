package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.List;

public class GiftDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1319197818442721064L;

	private List<Gift> gifts;
	private List<MyGiftCode> myGifts;

	public List<Gift> getGifts() {
		return gifts;
	}

	public void setGifts(List<Gift> gifts) {
		this.gifts = gifts;
	}

	public List<MyGiftCode> getMyGifts() {
		return myGifts;
	}

	public void setMyGifts(List<MyGiftCode> myGifts) {
		this.myGifts = myGifts;
	}

}
