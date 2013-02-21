package com.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class MainActivity extends Activity {
	private GridView gridView;
	private SlidingDrawer slidingDrawer;
	private ImageView imageView;
	
	private Integer[] icons={
			R.drawable.alarm,R.drawable.calendar,
            R.drawable.camera,R.drawable.clock,
            R.drawable.music,R.drawable.tv
    };
	private String[] items={
			"Alarm","Calendar","Camera","Clock","Music","TV"
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initSlidingDrawer();
    }

	private void initSlidingDrawer() {
		gridView = (GridView) findViewById(R.id.content);
		slidingDrawer = (SlidingDrawer) findViewById(R.id.drawer);
		imageView = (ImageView) findViewById(R.id.imageView);
		
		/* 使用自定义的MyGridViewAdapter设置GridView里面的item内容 */
		MyGridViewAdapter adapter = new MyGridViewAdapter(this, items, icons);
		gridView.setAdapter(adapter);
		
		/* 设定SlidingDrawer被打开的事件处理 */
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				imageView.setImageResource(R.drawable.close);
			}
		});
		/* 设置SlidingDrawer被关闭的事件处理 */
		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				imageView.setImageResource(R.drawable.open);
			}
		});
		
	}
    
}
