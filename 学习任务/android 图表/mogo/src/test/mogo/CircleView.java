package test.mogo;

import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CircleView extends android.view.View {
	private int mLastX;
	private int mLastY;
	private double dA = 0d;// 按下的点到圆心的距离
	private double dB = 0d;// 中间某点到圆心的距离
	private double dC = 0d;// 按下点到中间某点的距离
	private double sum = 0d;
	private float mTop = 10f;
	private float mLeft = 50f;
	private float mRadius = 160f;
	private float[] drawAngle;
	private float[] percent;
	float sweepAngle = 0f;// 扇形角度
	float startAngle = 0f;// 起始角度
	float textSweepAngle = 0f;// 画字角度
	float preSweepAngle = 0f;// 每个扇形的开始角度
	private Paint piePaint;
	private Paint centerPaint;
	private Paint centerTextPaint;
	private RadialGradient mRadialGradient;
	private RelativeLayout drawPieLayout;

	public CircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CircleView(Context context) {
		super(context);
		initPaint();// 初始化paint
		initLayout();// 初始化layout参数
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/** * 初始化paint */
	public void initPaint() {
		piePaint = new Paint();
		piePaint.setAntiAlias(true);
		piePaint.setStyle(Paint.Style.FILL);
		piePaint.setStrokeCap(Cap.ROUND);
		piePaint.setStrokeJoin(Join.ROUND);
		centerPaint = new Paint();
		centerPaint.setAntiAlias(true);
		centerPaint.setStyle(Paint.Style.FILL);
		centerPaint.setStrokeCap(Cap.ROUND);
		centerPaint.setStrokeJoin(Join.ROUND);
		centerTextPaint = new Paint();
		centerTextPaint.setColor(Color.WHITE);
		centerTextPaint.setTextSize(14);
		centerTextPaint.setTextAlign(Align.CENTER);
		centerTextPaint.setStrokeWidth(0);
		centerTextPaint.setAntiAlias(true);
	}

	public void initLayout() {
		// 设置画饼图的layout
		drawPieLayout = new RelativeLayout(getContext());
		drawPieLayout.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		// CircleView cv = new CircleView(getContext());
		// cv.setSum(sum);
		// drawPieLayout.addView(cv);
	}

	public void initData(List<Double> listData) {
		for (Double d : listData) {
			sum += d;
		}
		drawAngle = new float[listData.size()];
		percent = new float[listData.size()];
		for (int i = 0; i < listData.size(); i++) {
			float temp = (float) (listData.get(i) / sum * 100);// 百分比
			percent[i] = Float.parseFloat(NumberUtil.getDecimal((double) temp));
			drawAngle[i] = temp * 3.6f;// 画图角度
		}
	}

	@Override
	protected void onDraw(Canvas canvas) { // TODO Auto-generated method stub
		super.onDraw(canvas);
		boolean bCanDrawText = true;
		int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
				Color.YELLOW };
		for (int i = 0; i < drawAngle.length; i++) {// 画多个扇形
			sweepAngle = drawAngle[i]; // 唤醒渲染
			mRadialGradient = new RadialGradient(mLeft + mRadius, mTop
					+ mRadius, mRadius, new int[] { colors[i % colors.length],
					colors[i % colors.length], colors[i % colors.length],
					colors[i % colors.length], colors[i % colors.length],
					colors[i % colors.length], colors[i % colors.length],
					colors[i % colors.length], Color.BLACK }, null,
					Shader.TileMode.MIRROR);
			piePaint.setShader(mRadialGradient); /*
												 * *画一个扇形 *RectF oval 用于画扇形的矩形区域
												 * float startAngle 起始角度 * float
												 * sweepAngle 需要画的扇形角度 * boolean
												 * useCenter * Paint paint 画笔
												 */
			canvas.drawArc(new RectF(mLeft, mTop, mLeft + 2 * mRadius, mTop + 2
					* mRadius), startAngle, sweepAngle, true, piePaint); // 需要画字的角度
			textSweepAngle = startAngle + drawAngle[i] / 2;
			double x = 0d;
			double y = 0d;
			double adjacentSide = Math.abs(Math.cos(textSweepAngle * Math.PI
					/ 180)
					* mRadius * 4 / 3);// 邻边
			double oppositeSide = Math.abs(Math.sin(textSweepAngle * Math.PI
					/ 180)
					* mRadius * 4 / 3);// 对边
			if (textSweepAngle > 360) {
				textSweepAngle = textSweepAngle % 360;
			} // 顺时针
			if (textSweepAngle >= 0 && textSweepAngle < 90) {
				x = adjacentSide + (mLeft + mRadius);
				y = oppositeSide + (mTop + mRadius);
			} else if (textSweepAngle >= 90 && textSweepAngle < 180) {
				x = (mLeft + mRadius) - adjacentSide;
				y = oppositeSide + (mTop + mRadius);
			} else if (textSweepAngle >= 180 && textSweepAngle < 270) {
				x = (mLeft + mRadius) - adjacentSide;
				y = (mTop + mRadius) - oppositeSide;
			} else {
				x = adjacentSide + (mLeft + mRadius);
				y = (mTop + mRadius) - oppositeSide;
			}
			Path path = new Path();
			path.moveTo(mLeft + mRadius, mTop + mRadius);// 圆点
			path.lineTo((int) x, (int) y); // 沿着路径画字
			canvas.drawTextOnPath(percent[i] + "%", path, 0, 0, centerTextPaint);
			float temp1 = startAngle % 360;// 前
			startAngle += sweepAngle;
			float temp2 = temp1 + sweepAngle;// 后
			if (!bCanDrawText) {
				continue;
			}
			if (temp2 >= 360 && temp1 <= 360) {// 若起终角度跨越360度这一点
				temp2 %= 360;
				if (temp1 <= 90 || temp2 >= 90) {
					drawText(canvas, String.valueOf(percent[i]));
					bCanDrawText = false;
				}
			} else if (temp1 <= 90 && (temp2 %= 360) >= 90) {
				drawText(canvas, String.valueOf(percent[i]));
				bCanDrawText = false;
			}
		}
	}

	private void drawText(Canvas canvas, String valueOf) {
		canvas.drawText(valueOf + "%", (mLeft + mRadius),
				(mTop + 2 * mRadius) + 10, centerTextPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) { // 获得触屏手指个数
		int point = event.getPointerCount();
		if (point == 1) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			int action = event.getAction();
			switch (action) { // down
			case MotionEvent.ACTION_DOWN:
				mLastX = x;
				mLastY = y;
				double c = Math.abs(mLastX - (mLeft + mRadius));
				double d = Math.abs(mLastY - (mTop + mRadius)); // 得到按下点到圆心的距离
				dA = getDistance(c, d);
				break; // move
			case MotionEvent.ACTION_MOVE:
				double c1 = Math.abs(event.getX() - mLastX);
				double d1 = Math.abs(event.getY() - mLastY);
				dC = getDistance(c1, d1);
				double c2 = Math.abs(event.getX() - (mLeft + mRadius));
				double d2 = Math.abs(event.getY() - (mTop + mRadius));
				dB = getDistance(c2, d2); // 两点与圆点确定的角度余弦值
				double cos = (dA * dA + dB * dB - dC * dC) / (2 * dA * dB);
				if (mLastY < (mTop + mRadius)) {// 按下的点在圆的上半部分
					if (event.getY() >= (mTop + mRadius)
							&& mLastX <= (mLeft + mRadius)) {// 经过最左侧点,逆时针
						startAngle -= Math.acos(cos) * 180 / Math.PI;
					} else if (event.getY() >= (mTop + mRadius)
							&& mLastX > (mLeft + mRadius)) {
						// 经过最右侧点,顺时针
						startAngle += Math.acos(cos) * 180 / Math.PI;
					} else {// 没有经过边界点
						if (event.getX() - mLastX > 0) {// 顺时针
							startAngle += Math.acos(cos) * 180 / Math.PI;
						} else if (event.getX() - mLastX < 0) {// 逆时针
							startAngle -= Math.acos(cos) * 180 / Math.PI;
						}
					}
				} else if (mLastY > (mTop + mRadius)) {
					// 按下的点在圆的下半部分
					if (event.getY() <= (mTop + mRadius)
							&& mLastX <= (mLeft + mRadius)) {// 经过最左侧点，顺时针
						startAngle += Math.acos(cos) * 180 / Math.PI;
					} else if (event.getY() <= (mTop + mRadius)
							&& mLastX <= (mLeft + mRadius)) {// 经过最右点，逆时针
						startAngle -= Math.acos(cos) * 180 / Math.PI;
					} else {// 没有经过边界点
						if (event.getX() - mLastX < 0) {// 顺时针
							startAngle += Math.acos(cos) * 180 / Math.PI;
						} else if (event.getX() - mLastX > 0) {
							// 逆时针
							startAngle -= Math.acos(cos) * 180 / Math.PI;
						}
					}
				} else if (mLastY == (mTop + mRadius) && mLastX == mLeft) {// 按下的点在最左侧
					if (event.getY() - mLastY < 0) {// 顺时针
						startAngle += Math.acos(cos) * 180 / Math.PI;
					} else if (event.getY() - mLastY > 0) {// 逆时针
						startAngle -= Math.acos(cos) * 180 / Math.PI;
					}
				} else if (mLastY == (mTop + mRadius)
						&& mLastX == (mLeft + 2 * mRadius)) {// 按下的点在最右侧
					if (event.getY() - mLastY > 0) {// 顺时针
						startAngle += Math.acos(cos) * 180 / Math.PI;
					} else if (event.getY() - mLastY < 0) {
						// 逆时针
						startAngle -= Math.acos(cos) * 180 / Math.PI;
					}
				}
				invalidate();// 刷新画图
				mLastX = (int) event.getX();
				mLastY = (int) event.getY();
				double c3 = Math.abs(mLastX - (mLeft + mRadius));
				double d3 = Math.abs(mLastY - (mTop + mRadius)); // 得到按下点到圆心的距离
				dA = getDistance(c3, d3);
				break; // up
			case MotionEvent.ACTION_UP:
				break;
			}
		} else {
			System.out.println("point != 1");
		}
		return super.onTouchEvent(event);
	}

	/** * 计算两点之间距离 * @param x 两点x轴的差距 * @param y 两点y轴的差距 * @return */
	public double getDistance(double x, double y) {
		double z = Math.sqrt(x * x + y * y);
		return z;
	}

	public double getSum() {
		return sum;
	}

}
