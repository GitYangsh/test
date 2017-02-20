package com.jy.app.market.idata.data;

import java.io.Serializable;

import com.jy.app.market.apk.type.CommentType;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1116820458750633565L;
	private String iconUrl; // 用户头像url
	private String nickName; // 昵称
	private String content; // 评论内容
	private String time; // 评论时间
	private CommentType evaluate; // 评论

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public CommentType getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(CommentType evaluate) {
		this.evaluate = evaluate;
	}

}
