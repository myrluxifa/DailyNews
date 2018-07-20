package com.lvmq.api;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.Answer;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.AnswerRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.DayMissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"答题"})
@RestController
@RequestMapping("api/answer")
public class AnswerAPI extends BaseAPI {
	
	private static final String[] QUESTIONS = {"问卷调查问题","新手答题问题"};

	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private DayMissionService dayMissionService;
	
	@ApiOperation(value = "答案提交", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "type", value = "0问卷调查 1新手答题", required = true, dataType = "int"),
			@ApiImplicitParam(paramType = "query", name = "answers", value = "答案，按顺序竖线|分隔", required = true, dataType = "String")
	})
	@PostMapping("mission")
	public ResponseBean<Object> answer(String userId, Integer type, String answers) {
		try {
			
			Answer ans = new Answer();
			ans.setAnswers(answers);
			ans.setCreateTime(new Date());
			ans.setQuestions(QUESTIONS[type]);
			
			// 更新新手任务状态
			Optional<UserLogin> user = userLoginRepository.findById(userId);
			
			UserLogin uu = user.get();
			
			String[] states = uu.getNewerMission().split("\\|");
			if(type == 0) {
				dayMissionService.reward(userId, Consts.GoldLog.Type.QUESTIONNAIRE, Consts.NewerMission.REWARD[Consts.NewerMission.RewardType.QUESTIONNAIRE]);
				uu.setNewerMission(states[0] + "|" + states[1] + "|" + (Integer.valueOf(states[2]) + 1) + "|" + states[3]);				
			}else if(type == 1) {
				dayMissionService.reward(userId, Consts.GoldLog.Type.ANSWER, Consts.NewerMission.REWARD[Consts.NewerMission.RewardType.ANSWER]);
				uu.setNewerMission(states[0] + "|" + states[1] + "|" + states[2] + "|" + (Integer.valueOf(states[3]) + 1));			
			}
			
			userLoginRepository.save(uu);
			// end 更新新手任务状态
			
			ans.setType(type.toString());
			ans.setUserId(userId);
			
			answerRepository.save(ans);
			
			
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
}
