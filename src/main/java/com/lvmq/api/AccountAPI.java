package com.lvmq.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.GoldLog;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.util.PagePlugin;
import com.lvmq.util.TimeUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/account")
public class AccountAPI extends BaseAPI {

	@Autowired
	private GoldLogRepository goldLogRepository;

	@ApiOperation(value="账户明细，金币分页", notes="", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "curPage", value = "当前页，从0开始", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", required = true, dataType = "String")
	})
	@PostMapping("detail/page")
	public ResponseBean<Object> page(String curPage, String pageSize, String userId) {
		try {
			Page<GoldLog> page = goldLogRepository.findByUserId(PagePlugin.pagePluginSort(Integer.valueOf(curPage), Integer.valueOf(pageSize), Direction.DESC, "createTime"), userId);
			
			List<AccountPageRes> result = new ArrayList<>();
			for (GoldLog gl : page) { 
				AccountPageRes res = new AccountPageRes();
				res.setName(Consts.GoldLog.getTypeName(gl.getType()));
				res.setCnt(gl.getNum());
				res.setTime(TimeUtil.format(gl.getCreateTime()));
				result.add(res);
			}
			
			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, page.getTotalPages()+"", result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
}

class AccountPageRes {
	private String name;
	private String time;
	private long cnt;

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

	public long getCnt() {
		return cnt;
	}

	public void setCnt(long cnt) {
		this.cnt = cnt;
	}

}
