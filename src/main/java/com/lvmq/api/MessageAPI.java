package com.lvmq.api;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.MessageRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.model.SysMessage;
import com.lvmq.model.UserReadMessage;
import com.lvmq.repository.SysMessageRepository;
import com.lvmq.repository.UserReadMessageRepository;
import com.lvmq.util.PagePlugin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"消息"})
@RestController
@RequestMapping("api/message")
public class MessageAPI extends BaseAPI {
	
	@Autowired
	private SysMessageRepository sysMessageRepository;
	
	@Autowired
	private UserReadMessageRepository userReadMessageRepository;

	@ApiOperation(value = "消息分页", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "curPage", value = "当前页，从0开始", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", required = true, dataType = "String") })
	@PostMapping("page")
	public ResponseBean<List<MessageRes>> page(String userId, String curPage, String pageSize) {
		
		Page<SysMessage> page = sysMessageRepository.findByFlag(PagePlugin.pagePluginSort(Integer.valueOf(curPage),
				Integer.valueOf(pageSize), Direction.DESC, "createTime"), 2);
		
		List<MessageRes> result = new ArrayList<>();
		
		String lastid = null;
		
		for (SysMessage msg : page.getContent()) {
			MessageRes res = new MessageRes(msg.getId(), msg.getTitle(), msg.getDetail(), DateFormatUtils.format(msg.getCreateTime(), "yyyy-MM-dd"));
			result.add(res);
			if(null == lastid) {
				lastid = msg.getId();
				UserReadMessage urm = userReadMessageRepository.findTop1ByUserIdOrderByCreateTimeDesc(userId);
				if(null == urm) {
					urm = new UserReadMessage();
					urm.setCreateTime(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
					urm.setMessageId(lastid);
					urm.setUserId(userId);
					userReadMessageRepository.saveAndFlush(urm);
				}
			}
		}
		
		return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS, "成功", result);
	}
	
	@ApiOperation(value = "红点", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
	})
	@PostMapping("redot")
	public ResponseBean<Object> readed(String userId) {
		
		UserReadMessage urm = userReadMessageRepository.findTop1ByUserIdOrderByCreateTimeDesc(userId);
		
		if(null == urm) {
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "成功", true);
		}
		
		SysMessage sm = sysMessageRepository.findTop1ByFlagOrderByCreateTimeDesc("2");
		
		if(null != sm && !sm.getId().equals(urm.getMessageId())) {
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "成功", true);
		}
		
		return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "成功", false);
	}
}
