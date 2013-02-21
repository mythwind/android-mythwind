package com.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WebApp01Activity extends Activity {
    /** Called when the activity is first created. */
	
	private EditText nickname;
	private EditText content;
	private Button button;
	private Handler handler;
	private TextView resultView;
	private String result = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        nickname = (EditText) findViewById(R.id.nickname);
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
        			nickname.setText("");
        		}
        	};
        };
    }
    
    public void send() {
    	String target = "http://86.5.71.211:8080/blog/index.jsp";
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpPost request = new HttpPost(target);
    	//  将要传递的参数保存到 List 集合
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("param", "post"));
    	params.add(new BasicNameValuePair("nickname", nickname.getText().toString()));
    	params.add(new BasicNameValuePair("content", content.getText().toString()));
    	try {
			request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = httpClient.execute(request);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity());
			} else {
				result = "请求失败!";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}