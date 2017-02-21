package com.ysh.appmarket.util;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map.Entry;

/**
 * 类说明：
 * 
 * @author Baker.li
 * @date 2014年8月11日
 * @version 1.0
 */

public class IdataUtils {
	// private static String TAG = "IdataUtils";

	private static String getJsonObjectString(JsonObject json, String key) {
		if (json == null || TextUtils.isEmpty(key)) {
			return null;
		}

		if (json.get(key).isJsonNull()) {
			return null;
		}

		return json.get(key).getAsString();
	}

	public static final String getIconUrl(JsonObject icons) {
		if (icons == null) {
			return "";
		}
		String url = "";
		do {
			url = getJsonObjectString(icons, "px256");
			if (!TextUtils.isEmpty(url)) {
				break;
			}
			url = getJsonObjectString(icons, "px100");
			if (!TextUtils.isEmpty(url)) {
				break;
			}
			url = getJsonObjectString(icons, "px78");
			if (!TextUtils.isEmpty(url)) {
				break;
			}
		} while (false);
		if (TextUtils.isEmpty(url)) {
			for (Entry<String, JsonElement> entry : icons.entrySet()) {
				if (!entry.getValue().isJsonNull()) {
					url = entry.getValue().getAsString();
					break;
				}
			}
		}
		return url;
	}
}
