package com.lvmq.api.res;

import java.util.List;

public class ReadPackageNews {
	private List<String> ids;
	
	private long time;
	
	public ReadPackageNews() {
		// TODO Auto-generated constructor stub
	}
	
	public ReadPackageNews(List<String> ids,long time) {
		// TODO Auto-generated constructor stub
		this.ids=ids;
		this.time=time;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
}
