package com.lvmq.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.VideosArrayRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.service.VideosService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"视频"})
@RestController
@RequestMapping("/api/videos")
public class VideosAPI {
	
	@Autowired
	private VideosService videosService;

	@ApiOperation(value = "获得视频列表", notes = "")
	@RequestMapping(value="/getComment",method=RequestMethod.POST)
	public ResponseBean<VideosArrayRes> getComment(String page,String pageSize) {
		return new ResponseBean<VideosArrayRes>(Code.SUCCESS, Code.SUCCESS_CODE, "成功", videosService.getVideosArray(Integer.valueOf(page), Integer.valueOf(pageSize)));
	}
	
	
//	@RequestMapping(value="/getVideosFromIDataAPI",method=RequestMethod.POST)
//	public void getVideosFromIDataAPI() {
//		videosService.getVideosFromIDataAPI();
//	}
	
	@ApiOperation(value = "精彩视频", notes = "")
	@RequestMapping(value="/getWonderfulVideo",method=RequestMethod.POST)
	public ResponseBean<VideosArrayRes> getWonderfulVideo(String pageSize) {
		return new ResponseBean<VideosArrayRes>(Code.SUCCESS, Code.SUCCESS_CODE, "成功", videosService.getWonderfulVideosArray(Integer.valueOf(pageSize)));
	}
	
	@ApiOperation(value="增加播放次数")
	@RequestMapping(value="/addAmountOfPlay",method=RequestMethod.POST)
	public ResponseBean addAmountOfPlay(String id) {
		return new ResponseBean(Code.SUCCESS, Code.SUCCESS_CODE, "成功",videosService.addAmountOfPlay(id));
	}
}
