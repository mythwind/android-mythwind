package com.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
    private PopupWindow popupWindow;
    private View parent;
    private GridView gridView;
    
    private Integer[] imageIds = {
    		R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, 
    		R.drawable.p5, R.drawable.p6, R.drawable.p8, R.drawable.p9
    };
    private String[] text = {
    		"搜索", "文件", "下载", "全屏", "网址", "书签", "添加", "分享"
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 请求自定义标题
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
        
        parent = findViewById(R.id.parent);
        View contentView = getLayoutInflater().inflate(R.layout.popwindow, null);
        gridView = (GridView) contentView.findViewById(R.id.gridView);
        
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for (int i = 0; i < imageIds.length; i++) {
        	HashMap<String, Object> item = new HashMap<String, Object>();
        	item.put("image", imageIds[i]);
        	item.put("text", text[i]);
        	data.add(item);
		}
        SimpleAdapter adapter = new SimpleAdapter(this, data, 
        		R.layout.gridview_item, 
        		new String[] {"image", "text"}, 
        		new int[] {R.id.grid_imageView, R.id.grid_textView});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(listener);
        
    	popupWindow = new PopupWindow(contentView, 
    			ViewGroup.LayoutParams.MATCH_PARENT, 
    			ViewGroup.LayoutParams.WRAP_CONTENT);
    	popupWindow.setFocusable(true);  // 取得焦点
    	// 点击其他地方，关闭popupWindow
    	popupWindow.setBackgroundDrawable(new BitmapDrawable());
    	popupWindow.setAnimationStyle(R.style.animation);
    }
    
    private OnItemClickListener listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
//			Toast.makeText(MainActivity.this, position, Toast.LENGTH_SHORT).show();
			if(popupWindow.isShowing()) popupWindow.dismiss(); 
		}
	};
    
    public void add(View view) {
    	Toast.makeText(this, "点击了Button", Toast.LENGTH_SHORT).show();
    	popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }
    
}
