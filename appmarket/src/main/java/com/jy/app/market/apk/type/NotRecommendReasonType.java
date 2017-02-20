package com.jy.app.market.apk.type;

public class NotRecommendReasonType {
	private Type type;
	private String description;
	
	/**
	 * 如果是 false，代表签名不一致的应用，需要先卸载再升级
	 */
	private boolean signatureMatch;
	 
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSignatureMatch() {
		return signatureMatch;
	}

	public void setSignatureMatch(boolean signatureMatch) {
		this.signatureMatch = signatureMatch;
	}
	
	public static enum Type{
		/**
		 * 非豌豆荚验证的 Apk
		 */
		NOT_SUPERIOR,
	
		/**
		 * 签名不一致，主要指的是山寨版本 -> 正版升级，会丢失数据；
		 */
		SIGNATURE_NOT_MATCH,
	
		/**
		 * 最新版有通知栏广告
		 */
		HAS_POP_ADS,
		/**
		 * 签名非官方版本，指的是山寨版本之间的同签名升级；
		 */
		SIGNATURE_NOT_OFFCIAL,
		/**
		 * 无法判断签名是否官方版本
		 */
		SIGNATURE_OFFCIAL_UNKNOWN,
	}
}
