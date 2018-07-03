package com.lvmq.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.lvmq.model.WxpubCaptcha;
import com.lvmq.repository.WxpubCaptchaRepository;
import com.lvmq.util.Util;

@Controller
public class CaptchaController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WxpubCaptchaRepository repository;
	
	@RequestMapping("gzh/captcha")
	public String login(String[] code, String[] state, Model model) throws UnsupportedEncodingException {
		
		log.info(new Gson().toJson(code));
		log.info(new Gson().toJson(state));
		
		Map<String, Object> map = getAccessToken(code[0]);
		
		WxpubCaptcha wxpub = repository.findTop1ByOpenidOrderByCreateTimeDesc(map.get("openid").toString());
		wxpub = Optional.ofNullable(wxpub).map(wx -> wx).orElse(new WxpubCaptcha());
		wxpub.setOpenid(map.get("openid").toString());
		Map<String, Object> wxuser = getUserInfo(map.get("access_token").toString(), wxpub.getOpenid());
		log.info(new Gson().toJson(wxuser));
		wxpub.setNickname(StringUtils.newStringUtf8(wxuser.get("nickname").toString().getBytes("ISO-8859-1")));
		wxpub.setHeadimgurl(wxuser.get("headimgurl").toString());
		wxpub.setSex(wxuser.get("sex") + "");
		wxpub.setProvince(StringUtils.newStringUtf8(wxuser.get("province").toString().getBytes("ISO-8859-1")));
		wxpub.setCity(StringUtils.newStringUtf8(wxuser.get("city").toString().getBytes("ISO-8859-1")));
		wxpub.setCountry(StringUtils.newStringUtf8(wxuser.get("country").toString().getBytes("ISO-8859-1")));
		wxpub.setCreateTime(Calendar.getInstance().getTime());
		wxpub.setCaptcha(Util.getRandom(6));
		repository.save(wxpub);
		
		model.addAttribute("captcha", wxpub.getCaptcha());
		
		return "index";
	}
	
	/**
	 * 
	 * @param code
	 * @return access_token expires_in refresh_token openid scope
	 */
	public Map<String, Object> getAccessToken(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=" + "wx70fd7691b93c23d6" + "&secret=" + "45860dbbe4f47c151f2783a1a1efc395"
				+ "&code=" + code + "&grant_type=authorization_code";
		return post(url);
	}
	
	/**
	 * post请求
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> post(String url) {
		CloseableHttpClient client = HttpClients.createMinimal();
		HttpPost post = new HttpPost(url);
		try {
			CloseableHttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			return new Gson().fromJson(result, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public Map<String, Object> getUserInfo(String accessToken, String openId) {
		String url = "https://api.weixin.qq.com/sns/userinfo?" + "access_token=" + accessToken + "&openid=" + openId
				+ "&lang=zh_CN";
		return get(url);
	}
	
	/**
	 * post请求
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> get(String url) {
		CloseableHttpClient client = HttpClients.createMinimal();
		HttpGet get = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			return new Gson().fromJson(result, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 /** 
     * 微信Token验证 
     * @param signature 微信加密签名 
     * @param timestamp 时间戳 
     * @param nonce     随机数 
     * @param echostr   随机字符串 
     * @return 
     * @throws NoSuchAlgorithmException  
     * @throws IOException  
     */  
    @RequestMapping("validation")
    public void getToken(String signature,String timestamp,String nonce,String echostr, HttpServletResponse response) throws NoSuchAlgorithmException, IOException{  
        // 将token、timestamp、nonce三个参数进行字典序排序   
        System.out.println("signature:"+signature);  
        System.out.println("timestamp:"+timestamp);  
        System.out.println("nonce:"+nonce);  
        System.out.println("echostr:"+echostr);  
        System.out.println("TOKEN:"+"feIFENSO328fFeioqxbm");  
        String[] params = new String[] { "feIFENSO328fFeioqxbm", timestamp, nonce };  
        Arrays.sort(params);  
        // 将三个参数字符串拼接成一个字符串进行sha1加密  
        String clearText = params[0] + params[1] + params[2];  
        String algorithm = "SHA-1";  
        String sign = new String(    
                org.apache.commons.codec.binary.Hex.encodeHex(MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));    
        // 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信    
        if (signature.equals(sign)) {    
            response.getWriter().print(echostr);    
        }    
    }  
}
