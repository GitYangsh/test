package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.List;

public class Gift implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2466017741195496475L;

	private String packageName; //礼包所属应用
	private String giftId; // 礼包ID
	private String iconUrl; // 礼包iconUrl
	private String title; // 礼包名称
	private String description; // 礼包描述
    private int total;          //兑换码个数
    private int surplus;        //兑换码剩余百分比
    private String validDate;   //礼包有效期
    private List<GiftDescription> details;//礼包说明列表
    
	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSurplus() {
		return surplus;
	}

	public void setSurplus(int surplus) {
		this.surplus = surplus;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public List<GiftDescription> getDetails() {
		return details;
	}

	public void setDetails(List<GiftDescription> details) {
		this.details = details;
	}

}
