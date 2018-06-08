package com.lvmq.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.model.Answer;
import com.lvmq.repository.AnswerRepository;

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