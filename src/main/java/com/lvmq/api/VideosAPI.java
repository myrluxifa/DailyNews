package com.lvmq.api;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
	
	
	@ApiOperation(value = "获得视频地址", notes = "")
	@RequestMapping(value="/getVideoUrl",method=RequestMethod.POST)
	public ResponseBean getVideoUrl(String url) {
		Document document;
		try {
			
			/** HtmlUnit请求web页面 */
			WebClient wc = new WebClient(BrowserVersion.CHROME);
			wc.getOptions().setUseInsecureSSL(true);
			wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
			wc.getOptions().setCssEnabled(false); // 禁用css支持
			wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
			wc.getOptions().setTimeout(100000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
			wc.getOptions().setDoNotTrackEnabled(false);
			HtmlPage page = wc
					.getPage(url);
			document = Jsoup.connect(url).maxBodySize(0)
				    .timeout(600000).get();
			return new ResponseBean(Code.SUCCESS, Code.SUCCESS_CODE, "成功", "");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return  new ResponseBean(Code.FAIL, Code.FAIL, "获取视频地址失败", "");
		}
	}
	
	@RequestMapping(value="/getVideosFromIDataAPI",method=RequestMethod.POST)
	public void getVideosFromIDataAPI() {
		videosService.getVideosFromIDataAPI();
	}
	
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
