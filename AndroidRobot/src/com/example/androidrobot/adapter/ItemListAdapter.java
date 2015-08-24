package com.example.androidrobot.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidrobot.R;
import com.example.androidrobot.WebActivity;
import com.example.androidrobot.bean.ChatMessage;
import com.example.androidrobot.bean.Common;
import com.example.androidrobot.http.MyApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ItemListAdapter<T> extends BaseAdapter{
	Context c;
	List<Common> list;
	ChatMessage cm;
	DisplayImageOptions options;
//	AnimateFirstDisplayListener listener;
	ImageLoader imageLoader;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemListAdapter(Context c, ChatMessage cm){
		this.c = c;
		this.list = cm.getList();
		this.cm = cm;
		this.imageLoader = MyApplication.imageLoader;
//		this.listener = listener;
		
        options = MyApplication.options;
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
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(c).inflate(R.layout.news_list_item, null);
			holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_detail_news_list_item);
			holder.tv_source = (TextView) convertView.findViewById(R.id.tv_source_news_list_item);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title_news_list_item);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_news_list_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Common common = list.get(position);
		holder.tv_detail.setText(common.getDetailurl()+"（点击可查看详情）");
		holder.tv_detail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(c, WebActivity.class);
				it.putExtra("url", common.getDetailurl());
				c.startActivity(it);
			}
		});
		if(cm.getListType() == 1){
			holder.tv_source.setText("来源:"+ common.getSource());
			holder.tv_title.setText(common.getArticle());
		}else if(cm.getListType() == 2){
			holder.tv_source.setText("航班线路："+common.getRoute()+"\n起飞时间："+common.getStarttime()+"\n到达时间："+common.getEndtime());
			holder.tv_title.setText("国家:"+common.getState()+"\n航班："+common.getFlight());
		}else if(cm.getListType() == 3){
			holder.tv_source.setText("发车时间："+ common.getStarttime() + "\n到达时间："+ common.getEndtime());
			holder.tv_title.setText(common.getStart()+" 开往："+common.getTerminal()+"\n车次:"+common.getTrainnum());
		}else if(cm.getListType() == 4){
			holder.tv_source.setText("材料:"+ common.getInfo());
			holder.tv_title.setText(common.getName());
		}
		if(!TextUtils.isEmpty(common.getIcon())){
			holder.iv.setVisibility(View.VISIBLE);
			imageLoader.displayImage(common.getIcon(), holder.iv, options);
		} else {
			holder.iv.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	class ViewHolder {
		TextView tv_title, tv_source,tv_detail;
		ImageView iv;
	}

}
