package com.example.androidrobot.http;

import java.io.File;

import com.example.androidrobot.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class MyApplication extends Application{
	public static ImageLoader imageLoader = ImageLoader.getInstance();
	public static DisplayImageOptions options;
	public static SharedPreferences sp;
	private static Editor editor;

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sp.edit();
	}
	
	public static void putString(String key,String value){
		editor.putString(key, value);
		editor.commit();
	}
	public static String getString(String key,String value){
		return sp.getString(key, value);
	}
	
	 public static void initImageLoader(Context context) {
	        //缓存文件的目录
	        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache"); 
	        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
	                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽 
	                .threadPoolSize(3) //线程池内加载的数量
	                .threadPriority(Thread.NORM_PRIORITY - 2)
	                .denyCacheImageMultipleSizesInMemory()
	                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
	                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
	                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
	                .diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
	                .tasksProcessingOrder(QueueProcessingType.LIFO)
	                // 由原先的discCache -> diskCache
	                .discCacheFileCount(100) //缓存的文件数量  
	                .diskCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径  
	                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间  
	                .writeDebugLogs() // Remove for release app
	                .build();
	        //全局初始化此配置  
	        imageLoader.init(config);
	     // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
	        options = new DisplayImageOptions.Builder()
	                .showImageOnLoading(R.drawable.loading) // 设置图片下载期间显示的图片
	                .showImageForEmptyUri(R.drawable.loading) // 设置图片Uri为空或是错误的时候显示的图片
	                .showImageOnFail(R.drawable.loading) // 设置图片加载或解码过程中发生错误显示的图片
	                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
	                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
	                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
	                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
	                .build(); // 构建完成
	    }
}
 