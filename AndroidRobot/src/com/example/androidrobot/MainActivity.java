package com.example.androidrobot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidrobot.adapter.ChatListAdatper;
import com.example.androidrobot.bean.ChatMessage;
import com.example.androidrobot.db.DBUtils;
import com.example.androidrobot.db.DBhelper;
import com.example.androidrobot.http.HttpUtils;
import com.example.androidrobot.http.MyApplication;
import com.example.androidrobot.view.AddPopWindow;
import com.example.androidrobot.view.AddPopWindow.DeleteListener;

@SuppressLint("HandlerLeak")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MainActivity extends Activity implements DeleteListener{
	ListView lv;
	ChatListAdatper mAdapter;
	ImageView iv,iv_back;
	EditText et;
	Button btn_send;
	List<ChatMessage> cmList;
	private String myMsg;
	String robotName;
	TextView title;
	long exitTime;
    AddPopWindow addPop;
    DBUtils dbUtiles;
    String str;
    
    String dbName = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/chatRobot.db";
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 1000){
				ChatMessage from = (ChatMessage) msg.obj;
				cmList.add(from);
				dbUtiles.insert(from);
				mAdapter.notifyDataSetChanged();
				lv.setSelection(cmList.size() - 1);
			}
			if(msg.what == 100){
				mAdapter.notifyDataSetChanged();
				lv.setSelection(cmList.size()-1);
			}
			if(msg.what == 10){
				mAdapter.notifyDataSetChanged();
				lv.setSelection(cmList.size()-1);
//				mAdapter.changeFirstItem(str);
				ChatMessage cm1 = new ChatMessage(1, "您还记得大明湖畔的"+robotName+"吗？就是我喽，" +
						"人称江湖百晓生，熟知段子、新闻、天气、列车、菜谱、航班、百度百科，很高兴为您服务！");
				cm1.setListType(0);
				cmList.add(cm1);
				dbUtiles.insert(cm1);
				handler.sendEmptyMessage(100);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_chatting);
//		dbUtiles = new DBUtils(this);
		dbUtiles = new DBUtils(this, dbName);
		
		initView();
		addPop = new AddPopWindow(this);
		addPop.setOnDeleteListener(this);
	}

	private <T> void initView() {
		cmList = new ArrayList<ChatMessage>();
		title = (TextView) findViewById(R.id.title);
		robotName = MyApplication.getString(HttpUtils.ROBOT_NIKE_NAME, "小黄鸭");
		title.setText(robotName);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<ChatMessage> tempList = new ArrayList<ChatMessage>();
				tempList = dbUtiles.queryChatMessageList();
				if(tempList.size() == 0 || tempList == null){
					ChatMessage cm1 = new ChatMessage(1, "您还记得大明湖畔的"+robotName+"吗？就是我喽，" +
							"人称江湖百晓生，熟知段子、新闻、天气、列车、菜谱、航班、百度百科，很高兴为您服务！");
					cmList.add(cm1);
					cm1.setListType(0);
					dbUtiles.insert(cm1);
				} else {
					cmList.addAll(tempList);
				}
				handler.sendEmptyMessage(100);
			}
		}).start();
		
		lv = (ListView) findViewById(R.id.id_chat_listView);
		iv = (ImageView) findViewById(R.id.iv_add);
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addPop.showAsDropDown(iv, iv.getLayoutParams().width / 2, 18);
			}
		});
		mAdapter = new ChatListAdatper(this, cmList, dbUtiles);
		lv.setAdapter(mAdapter);
		lv.setSelection(cmList.size()-1);
		
		et = (EditText) findViewById(R.id.id_chat_msg);
		btn_send = (Button) findViewById(R.id.id_chat_send);
		btn_send.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				myMsg = et.getText().toString();
				if(TextUtils.isEmpty(myMsg)){
					Toast.makeText(MainActivity.this, "请输入您要发送的内容", 0).show();
					return;
				} else {
					ChatMessage to = new ChatMessage(2, myMsg);
					to.setDate(new Date());
					to.setName(MyApplication.getString(HttpUtils.NIKE_NAME, "我"));
					to.setListType(0);
					cmList.add(to);
					dbUtiles.insert(to);
					mAdapter.notifyDataSetChanged();
					lv.setSelection(cmList.size()-1);
					et.setText("");
				}
				// 关闭软键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				// 得到InputMethodManager的实例
				if (imm.isActive())
				{
					// 如果开启
					imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
							InputMethodManager.HIDE_NOT_ALWAYS);
					// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
				}
				if(!isNetworkAvailable()){
					Toast.makeText(MainActivity.this,"请检查您的网络连接！" , 0).show();
					return;
				}
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						ChatMessage from = HttpUtils.sendMsg(myMsg);
						from.setType(1);
						from.setDate(new Date());
						from.setName(robotName);
						
						Message msg = handler.obtainMessage();
						msg.obj = from;
						msg.what = 1000;
						handler.sendMessage(msg);
					}
				}).start();
				

			}
		});
	}
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.KEYCODE_MENU){
			if(addPop.popFirst){
				addPop.showAsDropDown(iv, iv.getLayoutParams().width / 2, 18);
			}
			
			return true;
		}
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if(System.currentTimeMillis() - exitTime > 2000){
				Toast.makeText(this, "再按一次退出程序", 0).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onRestart() {
		mAdapter.notifyDataSetChanged();
		// 关闭软键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive())
		{
			// 如果开启
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
			// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		}
		robotName = MyApplication.getString(HttpUtils.ROBOT_NIKE_NAME, "小黄鸭");
		title.setText(robotName);
		str =  "您还记得大明湖畔的"+robotName+"吗？就是我喽，" +
				"人称江湖百晓生，熟知段子、新闻、天气、列车、菜谱、航班、百度百科，很高兴为您服务！";
		mAdapter.changeFirstItem(str);
		super.onRestart();
	}
	
	public boolean isNetworkAvailable() {
		  ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		  if (networkInfo != null && networkInfo.isConnected()) {
		   return true;
		  }
		  return false;
		 }

	@Override
	public void onDelete() {
		if(cmList.size() == 0||cmList == null||dbUtiles == null){
    		return;
    	}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < cmList.size(); i++) {
					dbUtiles.delete(cmList.get(i));
				}
				cmList.clear();
				handler.sendEmptyMessage(10);
			}
		}).start();
	}
	
}
