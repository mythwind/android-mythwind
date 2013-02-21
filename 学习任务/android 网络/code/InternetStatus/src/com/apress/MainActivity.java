package com.apress;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ConnectivityManager connectivityManager;
	
	private Button button;
	private TextView textIsAvailable;
	private TextView textNetworkType;
	private TextView textNetworkSubType;
	private TextView textNetworkStatus;
	private TextView textNetworkDetailStatus;
	private TextView textNetworkExtra;
	private TextView textNetworkIsRoam;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        connectivityManager = (ConnectivityManager) getSystemService(
        		Context.CONNECTIVITY_SERVICE);
        
        findViews();
        registerListener();
    }

	private void findViews() {
		button = (Button) findViewById(R.id.button);
		textIsAvailable = (TextView) findViewById(R.id.isavailable);
		textNetworkType = (TextView) findViewById(R.id.network_type);
		textNetworkSubType = (TextView) findViewById(R.id.subtype);
		textNetworkStatus = (TextView) findViewById(R.id.network_status_01);
		textNetworkDetailStatus = (TextView) findViewById(R.id.network_status_02);
		textNetworkExtra = (TextView) findViewById(R.id.network_extra);
		textNetworkIsRoam = (TextView) findViewById(R.id.network_isroam);
	}
	private void registerListener() {
		button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
				if(null != networkInfo) {
					textIsAvailable.setText(
							networkInfo.isAvailable() ? getString(R.string.yes) : getString(R.string.no));
					textNetworkType.setText(networkInfo.getTypeName());
					textNetworkSubType.setText(networkInfo.getSubtypeName());
					textNetworkStatus.setText(networkInfo.getState().toString());
					textNetworkDetailStatus.setText(networkInfo.getDetailedState().toString());
					textNetworkExtra.setText(networkInfo.getExtraInfo());
					textNetworkIsRoam.setText(
							networkInfo.isRoaming() ? getString(R.string.yes) : getString(R.string.no));
				} else {
					textIsAvailable.setText(getString(R.string.network_unavailable_msg));
				}
			}
		});
	}
    
    
}
