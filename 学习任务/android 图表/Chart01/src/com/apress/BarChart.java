package com.apress;

import java.util.Arrays;

import android.graphics.Color;
import android.os.Bundle;
import com.kidroid.kichart.ChartActivity;
import com.kidroid.kichart.model.Aitem;
import com.kidroid.kichart.view.BarView;

public class BarChart extends ChartActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle =  getIntent().getExtras();
		float[] data2012 = bundle.getFloatArray("2012");
		float[] data2013 = bundle.getFloatArray("2013");
		System.out.println("-------------------------");
		System.out.println(Arrays.toString(data2012));
		System.out.println(Arrays.toString(data2013));
		String[] arrX = new String[4];
		arrX[0] = "2012.1";
		arrX[1] = "2012.2";
		arrX[2] = "2012.3";
		arrX[3] = "2012.4";
		Aitem[] items = new Aitem[2];
		items[0] = new Aitem(Color.RED, "2012", data2012);
		items[1] = new Aitem(Color.GREEN, "2013", data2013);
		System.out.println(Arrays.toString(items));
		BarView barView = new BarView(this);
		barView.setTitle("Quarterly Sales(Billions)");
		barView.setAxisValueX(arrX);
		barView.setItems(items);
		setContentView(barView);
	}
	
}
