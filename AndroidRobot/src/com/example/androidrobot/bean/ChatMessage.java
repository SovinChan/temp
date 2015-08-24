package com.example.androidrobot.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 消息类型:接收的为 1，发送的为2
	 */
	private int type ;
	/**
	 * 消息内容
	 */
	private String msg;
	/**
	 * 日期
	 */
	private Date date;
	/**
	 * 日期的字符串格式
	 */
	private String dateStr;
	/**
	 * 发送人
	 */
	private String name;
	private String url;
	private List<Common> list;
//    public enum ListType{
//    	NEWS,
//    	TRAIN,
//    	FLIGHT,
//    	MENUS;
//    }
	// 新闻为 1，列车为2 飞机为3 菜谱为4
    private int listType;
	

	public int getListType() {
		return listType;
	}

	public void setListType(int listType) {
		this.listType = listType;
	}

	public List<Common> getList() {
		return list;
	}

	public void setList(List<Common> list) {
		this.list = list;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

//	public enum Type
//	{
//		INPUT, OUTPUT
//	}

	public ChatMessage()
	{
	}

	public ChatMessage(int type, String msg)
	{
		super();
		this.type = type;
		this.msg = msg;
		setDate(new Date());
	}

	public String getDateStr()
	{
		return dateStr;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.dateStr = df.format(date);

	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

}
