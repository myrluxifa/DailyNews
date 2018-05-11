package com.lvmq.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PagePlugin {
	public static Pageable pagePlugin(int page,int size) {
		if(page>0) {
			page=page-1;
		}
		if(size==0) {
			size=10;
		}
		return new PageRequest(page,size);
	}
	
	public static Pageable pagePluginSort(int page,int size,Direction direction,String... field ) {
		if(page>0) {
			page=page-1;
		}
		if(size==0) {
			size=10;
		}
		return new PageRequest(page,size,new Sort(direction,field));
	}
	
	public static int pageFromLimit(int page,int size) {
		if(page==1) {
			page=0;
		}else {
			page=(page-1)*size;
		}
		return page;
	}
}
