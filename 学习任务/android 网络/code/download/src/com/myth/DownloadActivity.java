package com.myth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DownloadActivity extends Activity {
    /** Called when the activity is first created. */
	
	private Button downloadTxt = null;
	private Button downloadMp3 = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        downloadTxt = (Button) findViewById(R.id.downloadTxt);
        downloadTxt.setOnClickListener(new DownloadTxtListener());
        downloadMp3 = (Button) findViewById(R.id.downloadMp3);
        downloadMp3.setOnClickListener(new DownloadMp3Listener());
    }
    
    class DownloadTxtListener implements OnClickListener {
    	@Override
    	public void onClick(View v) {
    		HttpDownloader httpDownloader = new HttpDownloader();
    		String lrc = httpDownloader.download(" http://86.5.71.211:8080/mp3/a1.lrc");
//    		"http://music.baidu.com/song/974156/download"  //86.5.71.211   192.168.1.101
    		System.out.println(lrc);
    	}
    }
    
    class DownloadMp3Listener implements OnClickListener {
    	@Override
    	public void onClick(View v) {
    		HttpDownloader httpDownloader = new HttpDownloader();
    		int lrc = httpDownloader.downFile("http://86.5.71.211:8080/mp3/a1.lrc", "vol/", "a1.lrc");
    		System.out.println(lrc);
    	}
    }

}