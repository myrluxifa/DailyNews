package com.lvmq.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class Weixin {

	public static String ACCESS_TOKEN;
	public static long ACCESS_TOKEN_EXPIRES_TIME = 0;
	public static long ACCESS_TOKEN_LAST_FRESH_TIME = 0;

	public static String JSAPI_TICKET;
	public static long JSAPI_TICKET_EXPIRES_TIME = 0;
	public static long JSAPI_TICKET_LAST_FRESH_TIME = 0;

	// test
	public static final String APPID = "wx3fbccfd560b16af6";
	private static final String APPSECRET = "fb9e50fb5eb36b40e9a43ffc55553584";

	public static String UrlEncode(String url) {
		try {
			return URLEncoder.encode(url, "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param code
	 * @return access_token expires_in refresh_token openid scope
	 */
	public static Map<String, Object> getAccessToken(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=" + APPID + "&secret=" + APPSECRET
				+ "&code=" + code + "&grant_type=authorization_code";
		return post(url);
	}

	/**
	 * 
	 * @param code
	 * @return access_token expires_in refresh_token openid scope
	 */
	public static Map<String, Object> refreshAccessToken(String token) {
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?" + "appid=" + APPID
				+ "&grant_type=refresh_token&refresh_token=" + token;
		return post(url);
	}

	/**
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static Map<String, Object> getUserInfo(String accessToken, String openId) {
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
	public static Map<String, Object> post(String url) {
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
	 * post请求
	 * 
	 * @param url
	 * @return
	 */
	public static Map<String, Object> post(String url, String json) {
		CloseableHttpClient client = HttpClients.createMinimal();
		HttpPost post = new HttpPost(url);
		try {
			StringEntity str = new StringEntity(json, "utf-8");
			post.setEntity(str);
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
	 * post请求
	 * 
	 * @param url
	 * @return
	 */
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
	 * 获取access_token
	 * 
	 * @return
	 */
	public static String getAccessToken() {
		if (Calendar.getInstance().getTimeInMillis() - ACCESS_TOKEN_LAST_FRESH_TIME >= ACCESS_TOKEN_EXPIRES_TIME
				* 1000) {
			Map<String, Object> map = get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
					+ "&appid=" + APPID + "&secret=" + APPSECRET);
			System.out.println(map.toString());
			ACCESS_TOKEN_EXPIRES_TIME = Double.valueOf(map.get("expires_in").toString()).longValue();
			ACCESS_TOKEN_LAST_FRESH_TIME = Calendar.getInstance().getTimeInMillis();
			ACCESS_TOKEN = map.get("access_token").toString();
		}
		return ACCESS_TOKEN;
	}
}

