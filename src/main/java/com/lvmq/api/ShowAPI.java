package com.lvmq.api;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	
	@ApiOperation(value = "账户明细，金币分页", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
			})
	@PostMapping("income")
	public ResponseBean<Object> income(String userId) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), Calendar.MONTH, Calendar.DAY_OF_MONTH, 0, 0, 0);
			Date from = cal.getTime();
			cal.set(cal.get(Calendar.YEAR), Calendar.MONTH, Calendar.DAY_OF_MONTH, 23, 59, 59);
			Date to = cal.getTime();
			
			List<BalanceLog> logs = balanceRepository.findByUserIdAndCreateTimeBetween(userId, from, to);
			double income = 0;
			for (BalanceLog log : logs) {
				income += Double.valueOf(log.getNum());
			}
			
			ShowRes res = new ShowRes();
			
			res.setIncome(income);
			res.setUrl(Consts.QRCODE);
			
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "成功", res);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<Object>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
}
