package com.lvmq.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.WithdrawPageRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.UserLogin;
import com.lvmq.model.WithdrawLog;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.repository.WithdrawLogRepository;
import com.lvmq.util.NumberUtils;
import com.lvmq.util.PagePlugin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"提现"})
@RestController
@RequestMapping("api/withdraw")
public class WithdrawAPI extends BaseAPI {
	
	@Autowired
	private UserLoginRepository userRepository;
	
	@Autowired
	private WithdrawLogRepository withdrawLogRepository;

	@ApiOperation(value = "可提现金额查询", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
			})
	@PostMapping("info")
	public ResponseBean<Object> info(String userId) {
		try {
			
			Optional<UserLogin> ouser = userRepository.findById(userId);
			
			UserLogin ul = ouser.get();
			
			double  fee= Double.valueOf(ul.getBalance()) + (double)ul.getGold()/Consts.GOLD_RATIO;

			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, "成功", com.lvmq.util.NumberUtils.format(fee));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
	
	@ApiOperation(value = "提现", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "fee", value = "提现金额，单位元", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "captcha", value = "验证码", required = true, dataType = "String")
			})
	@PostMapping("do")
	@Transactional
	public ResponseBean<Object> do1(String userId, String fee, String captcha) {
		try {
			
			Optional<UserLogin> ouser = userRepository.findById(userId);
			
			UserLogin ul = ouser.get();
			
			double wfee = Double.valueOf(fee);
			
			double  balance= Double.valueOf(ul.getBalance()) + (double)ul.getGold()/Consts.GOLD_RATIO;
			
			//提现金额不足
			if(wfee > balance) {
				return new ResponseBean<>(Code.FAIL, Code.FAIL, "失败", "余额不足~"); 
			}
			
			WithdrawLog log;
			// 1元提现直接提
			if(wfee == 1) {
				
				log = new WithdrawLog(captcha, Calendar.getInstance().getTime(), fee, Consts.Withdraw.State.DEFAULT, userId);
				
			} 
			// 非1元提现
			else {
				
				if(!ul.getNewerMission().equals("1|1|10|2")) {
					return new ResponseBean<>(Code.FAIL, Code.FAIL, "失败", "请先完成1元提现任务~"); 
				}
				
				log = new WithdrawLog(captcha, Calendar.getInstance().getTime(), fee, Consts.Withdraw.State.DEFAULT, userId);
			}
			
			// 减掉金币及余额
			if(Double.valueOf(ul.getBalance()) >= wfee) {
				ul.setBalance(NumberUtils.format(Double.valueOf(ul.getBalance()) - wfee));
			} else {
				double mb = Double.valueOf(ul.getBalance());
				
				wfee -= mb;
				
				ul.setBalance("0");
				ul.setGold(ul.getGold() - (long)(wfee * Consts.GOLD_RATIO));
			}
			
			withdrawLogRepository.save(log);
			userRepository.save(ul);

			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, "提现申请成功，请等待审核");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
	
	@ApiOperation(value = "提现记录查询", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "curPage", value = "当前页，从0开始", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", required = true, dataType = "String") })
	@PostMapping("detail/page")
	public ResponseBean<Object> page(String curPage, String pageSize, String userId) {
		try {
			Page<WithdrawLog> page = withdrawLogRepository.findByUserId(PagePlugin.pagePluginSort(Integer.valueOf(curPage),
					Integer.valueOf(pageSize), Direction.DESC, "createTime"), userId);

			List<WithdrawPageRes> result = new ArrayList<>();
			for (WithdrawLog wl : page) {
				WithdrawPageRes res = new WithdrawPageRes(wl.getFee(), DateUtils.formatDate(wl.getCreateTime(), "yyyy-MM-dd HH:mm:ss"), Consts.Withdraw.State.getName(wl.getState()));
				result.add(res);
			}

			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, "成功", result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
}
