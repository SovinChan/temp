package com.example.androidrobot;

import android.test.AndroidTestCase;

import com.example.androidrobot.http.HttpUtils;

public class Test extends AndroidTestCase
{
	public void testSendMsg()
	{
		HttpUtils.sendMsg("西斜七路堵车吗");
		HttpUtils.sendMsg("你好");
		HttpUtils.sendMsg("讲个笑话");
		HttpUtils.sendMsg("新浪体育");
	}

}
