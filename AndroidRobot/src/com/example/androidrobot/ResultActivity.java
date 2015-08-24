package com.example.androidrobot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidrobot.adapter.ItemListAdapter;
import com.example.androidrobot.bean.ChatMessage;
import com.example.androidrobot.bean.Common;

@SuppressWarnings("rawtypes")
public class ResultActivity extends BaseActivity {
	ListView lv;
	private static final String TEST_FILE_NAME = "imageloader.png";  
	ChatMessage cm;
	TextView title;
	ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		initview();
	}

	private void initview() {
		lv = (ListView) findViewById(R.id.lv_chat);
		title = (TextView) findViewById(R.id.tv_title);
		cm = (ChatMessage) getIntent().getSerializableExtra("data");
		ItemListAdapter adapter = new ItemListAdapter(this,cm);
		iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		if(cm != null){
			lv.setAdapter(adapter);
			if(cm.getListType() == 1){
				title.setText("新闻");
			} else if(cm.getListType() == 2){
				title.setText("列车");
			} else if(cm.getListType() == 3){
				title.setText("航班");
			} else if(cm.getListType() == 4){
				title.setText("菜谱");
			}
		}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Common common = (Common) cm.getList().get(position);
				Intent it = new Intent(ResultActivity.this, WebActivity.class);
				it.putExtra("url", common.getDetailurl());
				startActivity(it);
			}
		});

		
		
	}
	
	private void copyTestImageToSdCard(final File pathOnSdCard) {  
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    InputStream is = getAssets().open(TEST_FILE_NAME);  
                    FileOutputStream fos = new FileOutputStream(pathOnSdCard);  
                    byte[] buffer = new byte[8192];  
                    int read;  
                    try {  
                        while ((read = is.read(buffer)) != -1) {  
                            fos.write(buffer, 0, read); // 写入输出流  
                        }  
                    } finally {  
                        fos.flush();        // 写入SD卡  
                        fos.close();        // 关闭输出流  
                        is.close();         // 关闭输入流  
                    }  
                } catch (IOException e) {  
                }  
            }  
        }).start();     // 启动线程  
    }  
	
//	 /** 
//     * 图片加载第一次显示监听器 
//     * @author Administrator 
//     * 
//     */  
//    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {  
//          
//        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());  
//  
//        @Override  
//        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {  
//            if (loadedImage != null) {  
//                ImageView imageView = (ImageView) view;  
//                // 是否第一次显示  
//                boolean firstDisplay = !displayedImages.contains(imageUri);  
//                if (firstDisplay) {  
//                    // 图片淡入效果  
//                    FadeInBitmapDisplayer.animate(imageView, 500);  
//                    displayedImages.add(imageUri);  
//                }  
//            }  
//        }  
//    }  
	
	
	
}  


