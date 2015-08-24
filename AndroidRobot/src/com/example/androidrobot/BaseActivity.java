package com.example.androidrobot;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidrobot.http.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BaseActivity extends Activity{

	protected ImageLoader imageLoader = MyApplication.imageLoader;  
	  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		setOverflowShowingAlways();
		super.onCreate(savedInstanceState);
	}
//    @Override  
//    public boolean onCreateOptionsMenu(Menu menu) {  
//        // 加载菜单  
//        getMenuInflater().inflate(R.menu.main_menu, menu);
////    	menu.add(0, Menu.FIRST, 0, "清除内存缓存");
////    	menu.add(1, Menu.FIRST+1, 1, "清除SD卡缓存");
//    	
//        return true;  
//    }   
//  
//    @Override  
//    public boolean onOptionsItemSelected(MenuItem item) {  
//        switch (item.getItemId()) {
//              case R.id.action_plus:
//            	  Toast.makeText(BaseActivity.this,"健康廉洁奉公了解到", 0).show();
//            	  break;
////            case R.id.item_clear_memory_cache:  
////                imageLoader.clearMemoryCache();     // 清除内存缓存  
////                return true;  
////            case R.id.item_clear_disc_cache:  
////                imageLoader.clearDiscCache();       // 清除SD卡中的缓存  
////                return true;  
//            default:  
//                return false;  
//        }
//		return true;  
//    } 
//    @Override
//	public boolean onMenuOpened(int featureId, Menu menu) {
//		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
//			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//				try {
//					Method m = menu.getClass().getDeclaredMethod(
//							"setOptionalIconsVisible", Boolean.TYPE);
//					m.setAccessible(true);
//					m.invoke(menu, true);
//				} catch (Exception e) {
//				}
//			}
//		}
//		return super.onMenuOpened(featureId, menu);
//	}
//
//	private void setOverflowShowingAlways() {
//		try {
//			ViewConfiguration config = ViewConfiguration.get(this);
//			Field menuKeyField = ViewConfiguration.class
//					.getDeclaredField("sHasPermanentMenuKey");
//			menuKeyField.setAccessible(true);
//			menuKeyField.setBoolean(config, false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//    
//    private void setMenuBackground(){
//    	getLayoutInflater().setFactory(new Factory() {
//			
//			@Override
//			public View onCreateView(String name, Context context, AttributeSet attrs) {
//				if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
//						|| name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")){
//						LayoutInflater f = getLayoutInflater();
//						View view;
//						try {
//							view = f.createView(name, null, attrs);
//							System.out.println((view instanceof TextView));
//							if(view instanceof TextView){
//								view.setBackgroundResource(R.drawable.bottom_bar);
//							}
//							return view;
//						} catch (ClassNotFoundException e) {
//							e.printStackTrace();
//						} catch (InflateException e) {
//							e.printStackTrace();
//						}
//				}
//				
//				return null;
//			}
//							
//		});
//    }
//    private void setMenuTextColor(){
//    	getLayoutInflater().setFactory(new Factory() {
//			
//			@Override
//			public View onCreateView(String name, Context context, AttributeSet attrs) {
//				if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
//						|| name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")){
//						LayoutInflater f = getLayoutInflater();
//						View view;
//						try {
//							view = f.createView(name, null, attrs);
//							System.out.println((view instanceof TextView));
//							if(view instanceof TextView){
//								((TextView)view).setTextColor(Color.WHITE);
//							}
//							return view;
//						} catch (ClassNotFoundException e) {
//							e.printStackTrace();
//						} catch (InflateException e) {
//							e.printStackTrace();
//						}
//				}
//				
//				return null;
//			}
//							
//		});
//    }
    
    
}
