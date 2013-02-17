package com.work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button[] buttons = new Button[4];
	private int[] buttonIds = new int[] {
			R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		Constant.displayWidth = displayMetrics.widthPixels;
		Constant.displayHeight = displayMetrics.heightPixels;
		
		for(int i = 0;i < buttons.length;i++){
			buttons[i] = (Button) findViewById(buttonIds[i]);
			buttons[i].setOnClickListener(listener);
        }
		
    }
    
 private Button.OnClickListener listener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn1:
				startActivity(new Intent(MainActivity.this, WeightActivity.class));
				break;
			case R.id.btn2:
				startActivity(new Intent(MainActivity.this, DimenActivity.class));
				break;
			case R.id.btn3:
				startActivity(new Intent(MainActivity.this, DynaActivity.class));
				break;
			case R.id.btn4:
				startActivity(new Intent(MainActivity.this, MutilLayoutActiivty.class));
				break;
			default:
				break;
			}
		}
	};

    
}
