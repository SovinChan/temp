package com.example.androidrobot.bean;

import java.util.List;

public class Result
{
	private int code;
	private String text;
	private String url;
	private List<Common> list;
	
	public List<Common> getList() {
		return list;
	}

	public void setList(List<Common> list) {
		this.list = list;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Result()
	{
	}
	
	public Result(int resultCode, String msg)
	{
		this.code = resultCode;
		this.text = msg;
	}

	public Result(int resultCode)
	{
		this.code = resultCode;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	

}
