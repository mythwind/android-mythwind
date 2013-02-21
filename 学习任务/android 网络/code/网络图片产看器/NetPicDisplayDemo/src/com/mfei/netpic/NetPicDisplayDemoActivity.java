package com.mfei.netpic;

import com.mfei.service.ImageService;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NetPicDisplayDemoActivity extends Activity {
	
	EditText editText;
	ImageView imageView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.edittext);
        imageView = (ImageView) findViewById(R.id.imageview);
        button.setOnClickListener(new ButtonClickListener());
    }
    
    private final class ButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			String path = editText.getText().toString();
			System.out.println(path);
			try {
				byte[] data = ImageService.getNetImage(path);
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				imageView.setImageBitmap(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(NetPicDisplayDemoActivity.this, R.string.error, 
						Toast.LENGTH_SHORT).show();
			}
			
		}
    }
    
}
