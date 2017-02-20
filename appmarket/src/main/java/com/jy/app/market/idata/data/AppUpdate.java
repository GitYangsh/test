package com.jy.app.market.idata.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppUpdate implements Serializable {
	private static final long serialVersionUID = 8272075328100198660L;
	private List<UpdateInfo> updates = new ArrayList<UpdateInfo>();
	private long nextQuery;

	public List<UpdateInfo> getUpdates() {
		return updates;
	}

	public void setUpdates(List<UpdateInfo> updates) {
		this.updates = updates;
	}

	public long getNextQuery() {
		return nextQuery;
	}

	public void setNextQuery(long nextQuery) {
		this.nextQuery = nextQuery;
	}
}
