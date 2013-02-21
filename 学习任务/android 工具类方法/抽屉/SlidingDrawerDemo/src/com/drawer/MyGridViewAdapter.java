package com.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGridViewAdapter extends BaseAdapter {

	private Context mContext;
	private Integer[] icons;
	private String[] items;
	
	/* 构造符 */
	public MyGridViewAdapter(Context context, String[] items, Integer[] icons) {
		this.mContext = context;
		this.icons = icons;
		this.items = items;
	}
	  
	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 扩展布局
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.grid, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.icon);
		TextView textView = (TextView) view.findViewById(R.id.text);
		imageView.setImageResource(icons[position]);
		textView.setText(items[position]);
		return view;
	}

}
