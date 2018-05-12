package com.lvmq.idata;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONObject;

public class IDataAPI {
	private static String readAll(Reader rd) {
		try {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
		}catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}

	public static JSONObject postRequestFromUrl(String url, String body) {
		try {
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.print(body);
		out.flush();

		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			instream.close();
		}}catch (Exception e) {
			// TODO: handle exception
			return new JSONObject();
		}
	}

	public static String getRequestFromUrl(String url){
		try {
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			return jsonText;
		} finally {
			instream.close();
		}}catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}

}
