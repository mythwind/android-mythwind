package com.mfei.html;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	
	WebView mWebView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mWebView = (WebView)findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true); 
        mWebView.setWebViewClient(new MyWebClient());
        mWebView.loadUrl("http://www.example.com"); 
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
    		mWebView.goBack();
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
    class MyWebClient extends WebViewClient {
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		if(Uri.parse(url).getHost().equals("www.example.com")) {
    			return false;
    		}
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    		startActivity(intent);
    		return true;
    	}
    }
    
}
