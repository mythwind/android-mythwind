package com.apress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button viewbc = (Button) findViewById(R.id.viewbc);
        Button viewlc = (Button) findViewById(R.id.viewlc);
        viewbc.setOnClickListener(listener);
        viewlc.setOnClickListener(listener);
    }
    
    private Button.OnClickListener listener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			float[] data2012 = new float[4];
			float[] data2013 = new float[4];
			int[] ids2012 = new int[] {
					R.id.data2012_1, R.id.data2012_2, R.id.data2012_3, R.id.data2012_4
			};
			int[] ids2013 = new int[] {
					R.id.data2013_1, R.id.data2013_2, R.id.data2013_3, R.id.data2013_4
			};
			switch (v.getId()) {
			case R.id.viewbc:
				System.out.println("come on");
				System.out.println(ids2012.length);
				for (int i = 0; i < ids2012.length; i++) {
					EditText editText = (EditText) findViewById(ids2012[i]);
					String str = editText.getText().toString();
					System.out.println(str);
					try {
						float input = Float.parseFloat(str);
						data2012[i] = input;
					} catch (Exception e) {
						data2012[i] = 0;
					}
				}
				System.out.println("ids2013.length: " + ids2013.length);
				for (int i = 0; i < ids2013.length; i++) {
					EditText editText = (EditText) findViewById(ids2013[i]);
					String str = editText.getText().toString();
					try {
						float input = Float.parseFloat(str);
						data2013[i] = input;
						System.out.println(str);
					} catch (Exception e) {
						data2013[i] = 0;
					}
				}
				Intent barIntent = new Intent(MainActivity.this, BarChart.class);
				barIntent.putExtra("2012", data2012);
				barIntent.putExtra("2013", data2013);
				startActivity(barIntent);
				break;
			case R.id.viewlc:
				for (int i = 0; i < ids2012.length; i++) {
					EditText editText = (EditText) findViewById(ids2012[i]);
					String str = editText.getText().toString();
					try {
						float input = Float.parseFloat(str);
						data2012[i] = input;
						System.out.println("data2012:" + data2012[i]);
					} catch (Exception e) {
						data2012[i] = 0;
					}
				}
				for (int i = 0; i < ids2013.length; i++) {
					EditText editText = (EditText) findViewById(ids2013[i]);
					String str = editText.getText().toString();
					try {
						float input = Float.parseFloat(str);
						data2013[i] = input;
						System.out.println("data2013:" + data2013[i]);
					} catch (Exception e) {
						data2013[i] = 0;
					}
				}
				Intent lineIntent = new Intent(MainActivity.this, LineChart.class);
				lineIntent.putExtra("2012", data2012);
				lineIntent.putExtra("2013", data2013);
				startActivity(lineIntent);
				break;
			default:
				break;
			}
		}
	};
    
    
}
