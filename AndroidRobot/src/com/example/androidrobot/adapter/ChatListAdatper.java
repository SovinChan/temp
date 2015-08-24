package com.example.androidrobot.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidrobot.MainActivity;
import com.example.androidrobot.R;
import com.example.androidrobot.ResultActivity;
import com.example.androidrobot.SettingActivity;
import com.example.androidrobot.WebActivity;
import com.example.androidrobot.bean.ChatMessage;
import com.example.androidrobot.db.DBUtils;
import com.example.androidrobot.view.MyDialog;

@SuppressWarnings("rawtypes")
public class ChatListAdatper<T> extends BaseAdapter{
	static List<ChatMessage> list;
	Context context;
	DBUtils dbUtiles;
	public ChatListAdatper (Context context,List<ChatMessage> list, DBUtils dbUtiles){
		this.context = context;
		this.list = list;
		this.dbUtiles = dbUtiles;
	}
	
	@Override
	public int getItemViewType(int position) {
		return list.get(position).getType() == 1? 1 : 0;
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final int p = position;
		final ChatMessage item = list.get(p);
		if(convertView == null){
			holder = new ViewHolder();
			if(getItemViewType(position) == 1){
				convertView = LayoutInflater.from(context).inflate(
						R.layout.main_chat_from_msg, null);
				holder.tv_msg = (TextView) convertView.findViewById(R.id.chat_from_content);
				holder.tv_name = (TextView) convertView.findViewById(R.id.chat_from_name);
				holder.tv_time = (TextView) convertView.findViewById(R.id.chat_from_createDate);
				holder.tv_link = (TextView) convertView.findViewById(R.id.link_chat_from);
				holder.tv_link.setVisibility(View.GONE);
				convertView.setTag(holder);
			} else {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.main_chat_send_msg, null);
				holder.tv_msg = (TextView) convertView.findViewById(R.id.chat_send_content);
				holder.tv_name = (TextView) convertView.findViewById(R.id.chat_send_name);
				holder.tv_time = (TextView) convertView.findViewById(R.id.chat_send_createDate);
				convertView.setTag(holder);
			}
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_msg.setText(list.get(position).getMsg());
		holder.tv_time.setText(list.get(position).getDateStr());
		holder.tv_msg.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				final int[] location = new int[2];
				v.getLocationOnScreen(location);
				
				
				View v1 = LayoutInflater.from(context).inflate(R.layout.dialog, null);
				final MyDialog dialog = new MyDialog(context,R.style.dialog, v1);
				Window win = dialog.getWindow();
				WindowManager windowManager = ((Activity) context).getWindowManager();
				WindowManager.LayoutParams lp = win.getAttributes();
				Display display = windowManager.getDefaultDisplay();
				lp.width = (int)(display.getWidth() - 100); //设置宽度
				lp.x = 0; // 新位置X坐标
		        lp.y = location[1]-display.getHeight()/2+25; // 新位置Y坐标
		        lp.height = 100; // 高度
				win.setAttributes(lp);
				dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
				dialog.show();
//				final Dialog dialog = new Dialog(context);
//				dialog.setCanceledOnTouchOutside(true);
//				View v1 = LayoutInflater.from(context).inflate(R.layout.dialog, null);
//				dialog.setContentView(v1);
//				
//				Window dialogWindow = dialog.getWindow();
//			    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//			    dialogWindow.setGravity(Gravity.CENTER);
//			    lp.x = 0; // 新位置X坐标
//		        lp.y = 0; // 新位置Y坐标
//		        lp.width = LayoutParams.MATCH_PARENT; // 宽度
//		        lp.height = 300; // 高度
//		        lp.horizontalMargin = 20;
//		        dialogWindow.setAttributes(lp);
//			    
				TextView tv_copy = (TextView) v1.findViewById(R.id.tv_copy);
				TextView tv_delete = (TextView) v1.findViewById(R.id.tv_delete);
//				dialog.show();
				tv_copy.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						ClipboardManager mana = (ClipboardManager) context.
								getSystemService(Context.CLIPBOARD_SERVICE);
						mana.setText(list.get(p).getMsg());
                        Toast.makeText(context, "成功复制到粘贴板",
                                        Toast.LENGTH_SHORT).show();
					}
				});
				tv_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						dbUtiles.delete(list.get(p));
						list.remove(p);
						notifyDataSetChanged();
					}
				});
				return false;
			}
		});
		if(getItemViewType(position) == 1){
			holder.tv_name.setText(item.getName());
			holder.tv_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent it = new Intent(context, SettingActivity.class);
					it.putExtra("isNikeName", false);
					context.startActivity(it);
				}
			});
			if(!TextUtils.isEmpty(list.get(p).getUrl())||
					(list.get(p).getList() != null && list.get(p).getList().size()> 0)){
				holder.tv_link.setVisibility(View.VISIBLE);
				if(!TextUtils.isEmpty(list.get(p).getUrl())){
					holder.tv_link.setText("（点击可查看详情）"+list.get(position).getUrl());
				} else{
					holder.tv_link.setText("（点击可查看详情）");
				}
				holder.tv_link.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(list.get(p).getList() != null && list.get(p).getList().size()> 0){
							Intent it = new Intent(context, ResultActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("data", item);
							it.putExtras(bundle);
							context.startActivity(it);
						} else {
							Intent it = new Intent(context, WebActivity.class);
							it.putExtra("url", list.get(p).getUrl());
							context.startActivity(it);
						}
					}
				});
			} else {
				holder.tv_link.setVisibility(View.GONE);
			}
		} else{
			holder.tv_name.setText(item.getName());
			holder.tv_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent it = new Intent(context, SettingActivity.class);
					it.putExtra("isNikeName", true);
					context.startActivity(it);
				}
			});
		}
		return convertView;
	}
	
	public void changeFirstItem(String str){
		list.get(0).setMsg(str);
		notifyDataSetChanged();
	}
	
	class ViewHolder{
		TextView tv_msg,tv_time,tv_name, tv_link;
	}
	

}
