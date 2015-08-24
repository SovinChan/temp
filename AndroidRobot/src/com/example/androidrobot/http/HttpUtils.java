package com.example.androidrobot.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import android.text.TextUtils;
import android.util.Log;

import com.example.androidrobot.bean.ChatMessage;
import com.example.androidrobot.bean.Common;
import com.example.androidrobot.bean.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class HttpUtils{
	private static String API_KEY = "2da887cda5464ef9239ca7842b8fadb6";
	private static String BASE_URL = "http://www.tuling123.com/openapi/api";
	public final static int CODE_TEXT = 100000;
	public final static int CODE_TRAIN = 305000;
	public final static int CODE_PLANE = 306000;
	public final static int CODE_HTTP = 200000;
	public final static int CODE_NEWS = 302000;
	public final static int CODE_MENU = 308000;
	public final static String NIKE_NAME = "nike_name";
	public final static String ROBOT_NIKE_NAME = "robot_nike_name";
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ChatMessage sendMsg(String url){
		StringBuffer sb = new StringBuffer();
		Result result = null;
		ChatMessage from = null;
		try {
			String info = URLEncoder.encode(url,"utf-8");
			String getUrl = BASE_URL+"?key="+API_KEY+"&info="+info;
			URL mUrl = new URL(getUrl);
			HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
			conn.connect();
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line = "";
			while((line = br.readLine())!= null){
				sb.append(line);
			}
//			JSONObject obj = new JSONObject(sb.toString());
//			int code = obj.getInt("code");
//			String text = obj.getString("text");
//			Result result = new Result(code, text);
			Gson gson = new Gson();
			Log.d("HttpUtils:result.code==", sb.toString()+"");
			java.lang.reflect.Type type = new TypeToken<Result>() {}.getType(); 
			result = gson.fromJson(sb.toString(), type);
			Log.d("HttpUtils:result.code==", result.getCode()+"");
//			result = gson.fromJson(sb.toString(), Result.class);
			from = new ChatMessage();
				if(TextUtils.isEmpty(result.getText())
						|| result.getCode()<HttpUtils.CODE_TEXT){
					from.setMsg("服务器错误！请检查网络");
				}else{
					switch (result.getCode()) {
					case HttpUtils.CODE_TEXT:
						from.setMsg(result.getText());
						break;
					case HttpUtils.CODE_HTTP:
						from.setMsg(result.getText());
						from.setUrl(result.getUrl());
						break;
					case HttpUtils.CODE_NEWS:
						from.setMsg(result.getText());
//						List<News> newsList = (List<News>) result.getList();
//						List<News> newsList = result.getList();
//						if(newsList != null){
//							from.setList(newsList);
//							from.setListType(ListType.NEWS);
//						}
						List<Common> common1 = result.getList();
						if(common1 != null){
							from.setList(common1);
							from.setListType(1);
						}
						break;
					case HttpUtils.CODE_TRAIN:
						from.setMsg(result.getText());
						List<Common> common2 = result.getList();
						if(common2 != null){
							from.setList(common2);
							from.setListType(2);
						}
						break;
					case HttpUtils.CODE_PLANE:
						from.setMsg(result.getText());
						List<Common> common3 = result.getList();
						if(common3 != null){
							from.setList(common3);
							from.setListType(3);
						}
						break;
					case HttpUtils.CODE_MENU:
						from.setMsg(result.getText());
						List<Common> common4 = result.getList();
						if(common4 != null){
							from.setList(common4);
							from.setListType(4);
						}
						break;
						
						
					}
				}
			br.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return from;
		
	}
}

