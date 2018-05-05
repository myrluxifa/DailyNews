package com.lvmq.idata.res;

import java.util.List;

public class ToutiaoResponseDto {
	//是否有下一页
	private String hasNext;
	//返回的状态码
	private String retcode;
	//本次查询的api名
	private String appCode;
	//本次查询的api类型
	private String dataType;
	//翻页值
	private String pageToken;
	//数据
	private List<ToutiaoDataResponseDto> data;

	public String getHasNext() {
		return hasNext;
	}

	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPageToken() {
		return pageToken;
	}

	public void setPageToken(String pageToken) {
		this.pageToken = pageToken;
	}

	public List<ToutiaoDataResponseDto> getData() {
		return data;
	}

	public void setData(List<ToutiaoDataResponseDto> data) {
		this.data = data;
	}
}
