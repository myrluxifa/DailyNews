package com.lvmq.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.service.MakeMoneyService;

import ch.qos.logback.core.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"赚钱"})
@RestController
@RequestMapping("api/makeMoney")
public class MakeMoneyAPI {
	
	@Autowired
	private MakeMoneyService makeMoneyService;
	
	@ApiOperation(value = "赚钱列表", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户编号", required = true, dataType = "String")
	})
	@RequestMapping(value="/getList",method=RequestMethod.POST)
	public ResponseBean getList(String userId) {
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功",makeMoneyService.makeMoneyList(userId));
	}
	
	@ApiOperation(value = "参与", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户编号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "id", value = "编号", required = true, dataType = "String")
	})
	@RequestMapping(value="/takePartIn",method=RequestMethod.POST)
	public ResponseBean takePartIn(String userId,String id) {
		makeMoneyService.takePartIn(userId, id);
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
	}
	
	@ApiOperation(value="上传图片返回图片路径",notes="")
	@RequestMapping(value="/uploadImage",method=RequestMethod.POST)
	public ResponseBean uploadImage(MultipartFile file) {
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功",com.lvmq.util.FileUtil.uploadOneFile(file));
	}
	
	
	@ApiOperation(value="保存截图",notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "imgUrls", value = "图片地址  用$lvmq$分割", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户编号", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "id", value = "编号", required = true, dataType = "String")
})
	@RequestMapping(value="/saveImgs",method=RequestMethod.POST)
	public ResponseBean saveImgs(String imgUrls,String userId,String id) {
		makeMoneyService.saveImgs(imgUrls, userId, id);
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
	}
	
	@ApiOperation(value="取消",notes="")
	@RequestMapping(value="/cancel",method=RequestMethod.POST)
	public ResponseBean cancel(String userId,String id) {
		makeMoneyService.cancel(userId, id);
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
	}
	
	@ApiOperation(value="详情",notes="")
	@RequestMapping(value="/detail",method=RequestMethod.POST)
	public ResponseBean detail(String userId,String id) {
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功",makeMoneyService.detail(userId, id));
	}
}