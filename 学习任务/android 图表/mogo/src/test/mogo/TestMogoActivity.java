package test.mogo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import test.mogo.CenterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class TestMogoActivity extends Activity {
	private RelativeLayout relativeLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new PieChart(getApplicationContext()));
//		setContentView(R.layout.main);
//		List<Double> listData = new ArrayList<Double>();
//		listData.add(1922d);
//		listData.add(2330d);
//		listData.add(17020d);
//		listData.add(3000d);
//		listData.add(2510d);
//		listData.add(1540d);
//		listData.add(3500d);
//		listData.add(2510d);
//		CircleView view = new CircleView(TestMogoActivity.this);
//		view.initData(listData);
//		view.setClickable(true);
//		view.setLongClickable(true);
//		relativeLayout = (RelativeLayout) this.findViewById(R.id.view);
//		relativeLayout.addView(view);
//		CenterView cv = new CenterView(TestMogoActivity.this);
//		cv.setSum(view.getSum());
//		relativeLayout.addView(cv);
//		ListView lv = new ListView(this);
//		lv.getSelectedItemPosition();
	}
}