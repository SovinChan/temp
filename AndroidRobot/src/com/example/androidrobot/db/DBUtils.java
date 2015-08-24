package com.example.androidrobot.db;

import java.util.ArrayList;
import java.util.List;

import com.example.androidrobot.bean.ChatMessage;
import com.example.androidrobot.bean.Common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtils {

	private DBhelper mDbHelper;
	public DBUtils(Context context){
		this.mDbHelper = new DBhelper(context);
	}
	public DBUtils(Context context,String dbName){
		this.mDbHelper = new DBhelper(context,dbName);
	}
	
	public void insert(ChatMessage cm){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.execSQL("insert into chat_message(name,msg," +
				"datastr,type,listtype)values(?,?,?,?,?)",new Object[]
				{cm.getName(),cm.getMsg(),cm.getDateStr(),cm.getType(),cm.getListType()});
		Cursor c = db.rawQuery("select last_insert_rowid() from chat_message", null);
		c.moveToFirst();
		int parentid = c.getInt(0);
		if(cm.getList() != null && cm.getList().size() > 0){
			List<Common> list = cm.getList();
			for (int i = 0; i < list.size(); i++) {
				insertToCommon(list.get(i), parentid, db);
			}
		}
		db.close();
	}
	
	public void insertToCommon(Common com,int id,SQLiteDatabase db){
		db.execSQL("insert into common(parentid,detailurl,icon,article,source,name," +
				"info,flight,route,starttime,endtime,trainnum,start,terminal)values" +
				"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{id,com.getDetailurl(),
				com.getIcon(),com.getArticle(),com.getSource(),com.getName(),
				com.getInfo(),com.getFlight(),com.getRoute(),com.getStarttime(),
				com.getEndtime(),com.getTrainnum(),com.getStart(),com.getTerminal()});
	}
	public ChatMessage query(Integer id){
		 ChatMessage cm = new ChatMessage();
		 SQLiteDatabase db =  mDbHelper.getReadableDatabase();
		 Cursor c = db.rawQuery("select id,name,msg,datastr,type,listtype " +
		 		"from chat_message where id=?", new String[]{String.valueOf(id) });
		 while(c.moveToNext()){
			 cm.setName(c.getString(1));
			 cm.setMsg(c.getString(2));
			 cm.setDateStr(c.getString(3));
			 cm.setType(c.getInt(4));
			 cm.setListType(c.getInt(5));
			 int parentid = c.getInt(0);
			 cm.setList(queryCommon(parentid, db));
			 return cm;
		 }
		 db.close();
		 return null;
	}
	
	public List<ChatMessage> queryChatMessageList(){
		SQLiteDatabase db =  mDbHelper.getReadableDatabase();
		List<ChatMessage> list = new ArrayList<ChatMessage>();
		Cursor c = db.query("chat_message", new String[]{"_id","name","msg","datastr","type","listtype"},
				null, null, null, null, null);
		while(c.moveToNext()){
			ChatMessage cm = new ChatMessage();
			cm.setName(c.getString(1));
			cm.setMsg(c.getString(2));
			cm.setDateStr(c.getString(3));
			cm.setType(c.getInt(4));
			cm.setListType(c.getInt(5));
			int parentid = c.getInt(0);
			cm.setList(queryCommon(parentid, db));
			list.add(cm);
		}
		db.close();
		return list;
		
	}
	
	public List<Common> queryCommon(Integer parentid,SQLiteDatabase db){
		Cursor c = db.rawQuery("select parentid,detailurl,icon,article,source,name," +
				"info,flight,route,starttime,endtime,trainnum,start,terminal " +
				"from common where parentid=?", new String[]{String.valueOf(parentid)});
		List<Common> list = new ArrayList<Common>();
		
		while(c.moveToNext()){
			Common com = new Common(); 
			com.setDetailurl(c.getString(1));
			com.setIcon(c.getString(2));
			com.setArticle(c.getString(3));
			com.setSource(c.getString(4));
			com.setName(c.getString(5));
			com.setInfo(c.getString(6));
			com.setFlight(c.getString(7));
			com.setRoute(c.getString(8));
			com.setStarttime(c.getString(9));
			com.setEndtime(c.getString(10));
			com.setTrainnum(c.getString(11));
			com.setStart(c.getString(12));
			com.setTerminal(c.getString(13));
			list.add(com);
		}
		return list;
	}
	
	public void delete(ChatMessage cm){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.delete("chat_message", "datastr=?", new String[]{cm.getDateStr()});
		db.close();
	}
	
	
	
	
	
}
