package com.jy.app.market.idata.card;

import java.util.List;

import com.jy.app.market.idata.data.Apk;
import com.jy.app.market.idata.data.Link;

/**
 * <b>专题组合(</b>
 *  专题组合: 标题, 副标题, 多个APK
 *  
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardGroupSubject extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	/**
	 * 标题
	 */
	private String title;

	/**
	 * 副标题
	 */
	private String subTitle;
	
	/**
	 * 数据连接地址
	 */
	private Link link;
	
	private List<Apk> apks;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public List<Apk> getApks() {
		return apks;
	}

	public void setApks(List<Apk> apks) {
		this.apks = apks;
	}

	 
}
