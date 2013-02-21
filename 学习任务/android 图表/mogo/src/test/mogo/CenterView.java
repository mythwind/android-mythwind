package test.mogo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

public class CenterView extends View {
	private Paint centerPaint;
	private Paint centerTextPaint;
	private RadialGradient mRadialGradient;
	private double sum;

	public CenterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle); // TODO Auto-generated constructor stub
	}

	public CenterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CenterView(Context context) {
		super(context);
		centerPaint = new Paint();
		centerPaint.setAntiAlias(true);
		centerPaint.setAntiAlias(true);
		centerPaint.setStrokeCap(Cap.ROUND);
		centerPaint.setStrokeJoin(Join.ROUND);
		centerTextPaint = new Paint();
		centerTextPaint.setColor(Color.WHITE);
		centerTextPaint.setTextSize(14);
		centerTextPaint.setTextAlign(Align.CENTER);
		centerTextPaint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.sum = getSum();
		for (int i = 0; i < 10; i++) {// 画中心小圆
			mRadialGradient = new RadialGradient(210, 170, 80,
					new int[] { Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY,
							Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY,
							Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY,
							Color.GRAY, Color.GRAY, Color.WHITE }, null,
					Shader.TileMode.MIRROR);
			centerPaint.setShader(mRadialGradient);
			centerPaint.setAlpha(30);
			canvas.drawCircle(210, 170, 80, centerPaint);
			canvas.drawText("合计", 210, 165, centerTextPaint);
			canvas.drawText(sum + "", 210, 185, centerTextPaint);
		}
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}
}