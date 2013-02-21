package com.mfei.vortex;

import android.app.Activity;
import android.os.Bundle;

public class VortexActivity extends Activity {
	
	private static final String LOG_TAG = VortexActivity.class.getSimpleName();
	private VortexView vortexView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        vortexView = new VortexView(this);
        setContentView(vortexView);
        
    }
}