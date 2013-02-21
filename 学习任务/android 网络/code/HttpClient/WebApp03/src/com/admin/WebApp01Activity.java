package com.admin;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WebApp01Activity extends Activity {
    /** Called when the activity is first created. */
	
	private Button button;
	private Handler handler;
	private TextView resultView;
	private String result = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        button = (Button) findViewById(R.id.button);
        resultView = (TextView) findViewById(R.id.result);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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
        		}
        	};
        };
    }
    
    public void send() {
    	String target = "http://86.5.71.211:8080/blog/index.jsp?param=get";
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpGet request = new HttpGet(target);
    	try {
			HttpResponse response = httpClient.execute(request);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity());
			} else {
				result = "请求失败!";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}