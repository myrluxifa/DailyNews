package com.lvmq.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.model.GoldLog;
import com.lvmq.repository.GoldLogRepository;

@RestController
@RequestMapping("api/account")
public class AccountAPI extends BaseAPI {

	@Autowired
	private GoldLogRepository goldLogRepository;
	
	public ResponseBean<Object> page(String curPage, String pageSize, String userId) {
		try {
			
			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
}

class AccountPageRes {
	private String name;
	private String time;
	private int cnt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

}
