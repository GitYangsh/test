package com.jy.app.market.idata.data;

import com.jy.app.market.idata.card.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageCard implements Serializable{ 
	private static final long serialVersionUID = 548806411310064004L;
	/**
	 * 页面标题
	 */
	private String title;

	/**
	 * 页面数
	 */
	private int totalPage = 1;

	/**
	 * 当前页面号(从1开始)
	 */
	private int pageNo = 1;
	
	/**
	 * 数据版本号
	 */
	private long dataVersion;

	/**
	 * 卡片列表
	 */
	private List<Card> cards=new ArrayList<Card>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public PageCard addCard(Card card) {
		if(cards==null){
			cards=new ArrayList<Card>();
		}
		cards.add(card);
		
		return this;
	}
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public long getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(long timestamp) {
		this.dataVersion = timestamp;
	}
}
