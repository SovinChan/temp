package com.example.androidrobot.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.androidrobot.R;
import com.example.androidrobot.SettingActivity;
import com.example.androidrobot.adapter.ChatListAdatper;
import com.example.androidrobot.bean.ChatMessage;
import com.example.androidrobot.db.DBUtils;
import com.example.androidrobot.http.MyApplication;

public class AddPopWindow extends PopupWindow {  
    private View contentView;
    private AddPopWindow pop;
    public static boolean popFirst;
    DeleteListener listener;
    
  
    public AddPopWindow(final Activity context) {
    	pop = this;
        LayoutInflater inflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        contentView = inflater.inflate(R.layout.popwindows_view, null);  
        contentView.setFocusable(true);  
        contentView.setFocusableInTouchMode(true);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();  
        int w = context.getWindowManager().getDefaultDisplay().getWidth();  
        // 设置SelectPicPopupWindow的View  
        pop.setContentView(contentView);  
        // 设置SelectPicPopupWindow弹出窗体的宽  
        pop.setWidth(w / 2);  
        // 设置SelectPicPopupWindow弹出窗体的高  
        pop.setHeight(LayoutParams.WRAP_CONTENT);  
        // 设置SelectPicPopupWindow弹出窗体可点击  
        pop.setFocusable(true);  
        pop.setOutsideTouchable(true);  
        // 刷新状态  
        pop.update();  
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0000000000);  
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
        pop.setBackgroundDrawable(dw);  
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
//        this.setAnimationStyle(R.style.AnimationPreview);  
        contentView.setOnKeyListener(new OnKeyListener() {
        	
        	@Override
        	public boolean onKey(View v, int keyCode, KeyEvent event) {
        		if((keyCode == KeyEvent.KEYCODE_MENU) && (pop.isShowing())){
        			if(!popFirst){
        				pop.dismiss();
        				popFirst = true;
        			} else {
        				popFirst = false;
        			} 
        		}
        		else if((keyCode == KeyEvent.KEYCODE_MENU) &&
        				(!pop.isShowing())){
        			pop.showAtLocation(v, Gravity.RIGHT | Gravity.TOP, 20, 20);
        		}
        		
        		return false;
        	}
        });
        
        
        LinearLayout team1 = (LinearLayout) contentView  
                .findViewById(R.id.team1);  
        LinearLayout team2 = (LinearLayout) contentView  
                .findViewById(R.id.team2);  
        team1.setOnClickListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View arg0) {  
                AddPopWindow.this.dismiss();
                MyApplication.imageLoader.clearMemoryCache();
                Toast.makeText(context, "清除内存缓存成功！", 0).show();
            }  
        });  
  
        team2.setOnClickListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                AddPopWindow.this.dismiss();
                MyApplication.imageLoader.clearDiscCache();
                Toast.makeText(context, "清除SD卡缓存成功！", 0).show();
            }  
        });
        LinearLayout team3 = (LinearLayout) contentView  
                .findViewById(R.id.team3);  
        LinearLayout team4 = (LinearLayout) contentView  
                .findViewById(R.id.team4);  
        team3.setOnClickListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View arg0) {
            	AddPopWindow.this.dismiss();
            	Intent it = new Intent(context, SettingActivity.class);
				it.putExtra("isNikeName", true);
				context.startActivity(it);
            }  
        });  
        team4.setOnClickListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View v) {
            	AddPopWindow.this.dismiss();
            	Intent it = new Intent(context, SettingActivity.class);
				it.putExtra("isNikeName", false);
				context.startActivity(it);
            }  
        });  
        LinearLayout team5 = (LinearLayout) contentView  
        		.findViewById(R.id.team5);
        team5.setOnClickListener(new OnClickListener() {  
        	  
            @Override  
            public void onClick(View v) {
            	AddPopWindow.this.dismiss();
            	if(listener !=null){
            		listener.onDelete();
            	}
            }  
        }); 
        pop.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				popFirst = true;
			}
		});
        
    }  
    public void setOnDeleteListener(DeleteListener listener){
    	this.listener = listener;
    }
    
    public interface DeleteListener{
    	public void onDelete();
    }
    
//    public boolean isShowOnTopRight(){
//    	if(isShowOnTop){
//    		return true;
//    	}
//    	return false;
//    }
  
    /** 
     * 显示popupWindow 
     *  
     * @param parent 
     */  
//    public void showPopupWindow(View parent) {  
//        if (!this.isShowing()) {  
//            // 以下拉方式显示popupwindow  
//            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
//            isShowOnTop = true;
//        } else {  
//            this.dismiss();  
//        }  
//    }  
}  
