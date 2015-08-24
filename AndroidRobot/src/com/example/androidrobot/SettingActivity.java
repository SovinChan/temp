package com.example.androidrobot;

import com.example.androidrobot.http.HttpUtils;
import com.example.androidrobot.http.MyApplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity {
	ImageView iv_back;
	Button btn;
	EditText et;
	String nike;
	TextView title;
	boolean isNikeName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		init();
	}

	private void init() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		et = (EditText) findViewById(R.id.et_nikename);
		title = (TextView) findViewById(R.id.title);
		isNikeName = getIntent().getBooleanExtra("isNikeName", false);
		btn = (Button) findViewById(R.id.btn_mod_nicname);
		if(isNikeName){
			title.setText("设置昵称");
		} else {
			title.setText("设置机器人昵称");
		}
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nike = et.getText().toString();
				if(TextUtils.isEmpty(nike)){
					Toast.makeText(SettingActivity.this, "请输入修改的昵称", 0).show();
				} else {
					if(isNikeName){
						MyApplication.putString(HttpUtils.NIKE_NAME, nike);
						finish();
					} else {
						MyApplication.putString(HttpUtils.ROBOT_NIKE_NAME, nike);
						finish();
					}
				}
			}
		});
		
		
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		
		
		
	}

}
