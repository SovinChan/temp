package com.example.androidrobot.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "chat_robot.db";
	private static int DB_VERSION = 1;

	public DBhelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	public DBhelper(Context context, String dbName) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql1 = "CREATE TABLE IF NOT EXISTS chat_message"+
	               "(_id integer primary key autoincrement," +
	               "name text," +
	               "msg text," +
	               "datastr varchar," +
	               "type integer," +
	               "listtype integer)";
		db.execSQL(sql1);
		String sql2 = "CREATE TABLE IF NOT EXISTS common" +
				"(_id integer primary key autoincrement," +
				"parentid integer," +
				"detailurl varchar," +
				"icon varchar," +
				"article text," +
				"source varchar," +
				"name text," +
				"info text," +
				"flight varchar," +
				"route varchar," +
				"starttime varchar," +
				"endtime varchar," +
				"trainnum varchar," +
				"start text," +
				"terminal text)";
		db.execSQL(sql2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
