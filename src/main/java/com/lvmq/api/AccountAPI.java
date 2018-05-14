package com.lvmq.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.AccessLog;
import com.lvmq.model.GoldLog;
import com.lvmq.repository.AccessLogRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.util.PagePlugin;
import com.lvmq.util.TimeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"账户"})
@RestController
@RequestMapping("api/account")
public class AccountAPI extends BaseAPI {

	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@Autowired
	private AccessLogRepository accessLogRepository;

	@ApiOperation(value = "账户明细，金币分页", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "curPage", value = "当前页，从0开始", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", required = true, dataType = "String") })
	@PostMapping("detail/page")
	public ResponseBean<Object> page(String curPage, String pageSize, String userId) {
		try {
			Page<GoldLog> page = goldLogRepository.findByUserId(PagePlugin.pagePluginSort(Integer.valueOf(curPage),
					Integer.valueOf(pageSize), Direction.DESC, "createTime"), userId);

			List<AccountPageRes> result = new ArrayList<>();
			for (GoldLog gl : page) {
				AccountPageRes res = new AccountPageRes();
				res.setName(Consts.GoldLog.getTypeName(gl.getType()));
				res.setCnt(gl.getNum());
				res.setTime(TimeUtil.format(gl.getCreateTime()));
				result.add(res);
			}

			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, page.getTotalPages() + "", result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
	
	@ApiOperation(value = "账户明细，提现和提现状态分页", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "curPage", value = "当前页，从0开始", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "state", value = "只查提现成功的传1,查所有传空", required = true, dataType = "String") })
	@PostMapping("withdraw/page")
	public ResponseBean<Object> withdraw(String curPage, String pageSize, String userId, String state) {
		try {
			Page<AccessLog> page;
			if(StringUtils.isEmpty(state)) {
				page = accessLogRepository.findByUserId(PagePlugin.pagePluginSort(Integer.valueOf(curPage),
						Integer.valueOf(pageSize), Direction.DESC, "createTime"), userId);
			} else {
				page = accessLogRepository.findByUserIdAndState(PagePlugin.pagePluginSort(Integer.valueOf(curPage),
						Integer.valueOf(pageSize), Direction.DESC, "createTime"), userId, state);
			}

			List<AccountPageRes> result = new ArrayList<>();
			for (AccessLog al : page) {
				AccountPageRes res = new AccountPageRes();
				res.setName(Consts.AccessLog.getTypeName(al.getType()) + Consts.AccessLog.getStateName(al.getState()));
				res.setType(Consts.AccessLog.getTypeName(al.getType()));
				res.setCnt(Math.round(al.getFee()));
				res.setState(Consts.AccessLog.getStateName(al.getState()));
				res.setTime(TimeUtil.format(al.getCreateTime()));
				result.add(res);
			}

			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, page.getTotalPages() + "", result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
}

@ApiModel("账户明细 金币/提现/提现状态 返回类")
class AccountPageRes {
	
	@ApiModelProperty(name = "名称")
	private String name;
	@ApiModelProperty(name = "时间")
	private String time;
	@ApiModelProperty(name = "数量或金额")
	private long cnt;
	@ApiModelProperty(name = "状态")
	private String state;
	@ApiModelProperty(name = "类型")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

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
