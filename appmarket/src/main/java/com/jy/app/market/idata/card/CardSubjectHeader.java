package com.jy.app.market.idata.card;

/**
 * <b>专题头部</b><br>
 *  
 *  专题榜单形式：主要用于表现各种专题榜单
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardSubjectHeader extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	/**
	 * 专题图的URL
	 */
	private String imageUrl;

	/**
	 * html格式的文字描述信息
	 */
	private String detail;

	 

	public String getDetail() {
		return detail;
	}

	public void setDetail(String description) {
		this.detail = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	 
}
