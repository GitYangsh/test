package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.List;

public class CommentList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6977320110849897323L;
	private int good; // 好评条数
	private int bad; // 差评条数
	private int other; // 差评条数
	private int totalPage; // 总共页数
	private int pageNo; // 当前页
	private boolean commented;
	private List<Comment> comments; // 评论列表

	public int getGood() {
		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public int getBad() {
		return bad;
	}

	public void setBad(int bad) {
		this.bad = bad;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public boolean isCommented() {
		return commented;
	}

	public void setCommented(boolean commented) {
		this.commented = commented;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}