package com.work;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class DynaActivity extends Activity{

	private Button btn1,btn2,btn3,btn4;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dyna_layout);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        initSize();
    }
    
  //动态设置控件的宽高度
    private void initSize(){
    	// 第一个按钮，宽度100%，高度10%
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				(int) (Constant.displayHeight * 0.1f + 0.5f));
		btn1.setLayoutParams(params);
		// 第二个按钮，宽度100%，高度30%
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				(int) (Constant.displayHeight * 0.3f + 0.5f));
		btn2.setLayoutParams(params2);
		// 第三个按钮，宽度50%，高度20%
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
				(int) (Constant.displayWidth * 0.5f + 0.5f),
				(int) (Constant.displayHeight * 0.2f + 0.5f));
		btn3.setLayoutParams(params3);
		// 第三个按钮，宽度70%，高度填满剩下的空间
		LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
				(int) (Constant.displayWidth * 0.7f + 0.5f),
				LayoutParams.FILL_PARENT);
		btn4.setLayoutParams(params4);
    }
    
}
