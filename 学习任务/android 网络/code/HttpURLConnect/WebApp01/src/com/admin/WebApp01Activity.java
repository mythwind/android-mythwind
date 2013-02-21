package com.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WebApp01Activity extends Activity {
    /** Called when the activity is first created. */
	
	private EditText content;
	private Button button;
	private Handler handler;
	private TextView resultView;
	private String result = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        content = (EditText) findViewById(R.id.content);
        button = (Button) findViewById(R.id.button);
        resultView = (TextView) findViewById(R.id.result);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("".equals(content.getText().toString())) {
					Toast.makeText(WebApp01Activity.this, "请输入要发表的内容!", Toast.LENGTH_SHORT).show();
				}
				new Thread(new Runnable() {
					public void run() {
						send();
						Message msg = handler.obtainMessage();
						handler.sendMessage(msg);
					}
				}).start();
			}
		});
        handler = new Handler() {
        	public void handleMessage(Message msg) {
        		super.handleMessage(msg);
        		if(result != null) {
        			resultView.setText(result);
        			content.setText("");
        		}
        	};
        };
    }
    
    public void send() {
    	String target = "http://86.5.71.211:8080/blog/index.jsp?content=" + 
    		base64(content.getText().toString().trim());
    	try {
			URL url = new URL(target);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConn.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				result += line + "\n";
			}
			br.close();
			urlConn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public String base64(String content) {
    	try {
			content = Base64.encodeToString(content.getBytes("utf-8"), Base64.DEFAULT);
			content = URLEncoder.encode(content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return content;
    }
}