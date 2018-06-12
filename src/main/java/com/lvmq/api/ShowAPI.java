package com.lvmq.api;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.ShowRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.BalanceLog;
import com.lvmq.repository.BalanceLogRepository;
import com.lvmq.repository.GoldLogRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"晒收入"})
@RestController
@RequestMapping("api/show")
public class ShowAPI extends BaseAPI {

	@Autowired
	private BalanceLogRepository balanceRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@ApiOperation(value = "晒收入", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
			})
	@PostMapping("income")
	public ResponseBean<Object> income(String userId) {
		try {
			
			List<BalanceLog> logs = balanceRepository.findByUserId(userId);
			double income = 0;
			for (BalanceLog log : logs) {
				income += Double.valueOf(log.getNum());
			}
			
			//金币奖励
			List<String> goldTypeList=Arrays.asList(Consts.GoldLog.Type.MASTER_READ_REWARDS);
			int goldSum=goldLogRepository.sumNumByTypeInAndUserId(goldTypeList,userId);
			
			double  fee= income + (double)goldSum/Consts.GOLD_RATIO;
			
			ShowRes res = new ShowRes();
			
			res.setIncome(fee);
			res.setUrl(Consts.QRCODE);
			
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "成功", res);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<Object>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
	
}
