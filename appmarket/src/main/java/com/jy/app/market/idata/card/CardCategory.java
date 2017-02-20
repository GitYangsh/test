package com.jy.app.market.idata.card;

import java.util.List;

import com.jy.app.market.idata.data.Category;

/**
 * <b>分类卡片</b><br>
 * 
 *  软件/游戏分类榜单：主要用于组合分类形式,一个主分类(带图标) 和 若干个 子分类
 *
 * @author zzg.zhou(11039850@qq.com)
 */
public class CardCategory extends Card{ 
	private static final long serialVersionUID = 548806411310064004L;
	
	/**
	 * 分类名称
	 */
	private String name;

	/**
	 * 分类图标的URL
	 */
	private String icon;
	
	/**
	 * 获取该分类应用列表的URL
	 */
	private String url;	 
	
	/**
	 * 子分类列表
	 */
	private List<Category>  categories;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
