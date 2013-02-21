package test.mogo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/** 
 * 随手记中可以任意旋转的炫酷饼图的实现原理 
 *  
 * 小记： 
 * 在实现的过程中，主要是用到了一些数学计算来实现角度和屏幕位置坐标的计算 
 * 关于任意两个点之间的角度计算的问题，一开始想了很久没有结果，最后，一个偶然的灵光，让整个 
 * 事情变得简单起来，就是计算任意两个点相对于中心坐标的角度的时候，首先，计算 
 * 每个点相对于x轴正方向的角度，这样，总可以将其转化为计算直角三角形的内角计算问题 
 * 再将两次计算的角度进行减法运算，就实现了。是不是很简单？呵呵，对于像我们这样数学 
 * 没有学好的开发者来说，也只有这样化难为简了 
 *  
 * @author liner 
 * 
 */  
public class PieChart extends View{  
    public static final String TAG = "PieChart";  
    public static final int ALPHA = 100;  
    public static final int ANIMATION_DURATION = 800;  
    public static final int ANIMATION_STATE_RUNNING = 1;  
    public static final int ANIMATION_STATE_DOWN = 2;  
    /** 
     * 不要问我这个值是怎么设置的。这个就是图片中的一大块圆形区域对应的长方形四个边的坐标位置 
     * 具体的值，自己需要多次尝试并调整了。这样，我们的饼图就是相对于这个区域来画的 
     */  
    private static final RectF OVAL = new RectF(18,49,303,340);  
    
    private int[] colors; //每部分的颜色值   
      
    private int[] values; //每部分的大小   
      
    private int[] degrees; //值转换成角度   
      
    private String[] titles; //每部分的内容   
      
    private Paint paint;  
      
    private Paint maskPaint;  
      
    private Paint textPaint;  
      
    private Point lastEventPoint;  
      
    private int currentTargetIndex = -1;  
      
    private Point center; //这个是饼图的中心位置   
      
    private int eventRadius = 0; //事件距离饼图中心的距离   
      
    //测试的时候使用的   
    //private ChartClickListener clickListener;   
      
    private Bitmap mask; //用于遮罩的Bitmap   
      
    private int startDegree = 90; //让初始的时候，圆饼是从箭头位置开始画出的   
      
      
    private int animState = ANIMATION_STATE_DOWN;  
      
    private boolean animEnabled = false;  
      
    private long animStartTime;  
      
    public PieChart(Context context) {  
        super(context);  
        init();  
    }  
      
    public PieChart(Context context, AttributeSet attrs){  
        this(context, attrs, 0);  
    }  
      
    public PieChart(Context context, AttributeSet attrs, int defStyle){  
        super(context, attrs, defStyle);  
        init();  
    }  
      
    private void init(){  
        paint = new Paint();  
          
        maskPaint = new Paint();  
          
          
        textPaint = new Paint();  
        textPaint.setColor(Color.WHITE);  
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);  
        textPaint.setAlpha(100);  
        textPaint.setTextSize(16);  
          
        values = new int[]{  
                60,  
                90,  
                30,  
                50,  
                70  
        };  
          
//      titles = new String[]{   
//              "川菜",   
//              "徽菜",   
//              "粤菜",   
//              "闽菜",   
//              "湘菜"   
//      };   
        //测试文字居中显示   
        titles = new String[]{  
                "我是三岁",  
                "说了算四大皆空",  
                "士大",  
                "史蒂芬森地",  
                "湘"  
        };        
          
        colors = new int[]{  
            Color.argb(ALPHA, 249, 64, 64),  
            Color.argb(ALPHA, 0, 255, 0),  
            Color.argb(ALPHA, 255, 0, 255),  
            Color.argb(ALPHA, 255, 255, 0),  
            Color.argb(ALPHA, 0, 255, 255)  
        };  
          
        degrees = getDegrees();  
          
        //Drawable d = getResources().getDrawable(R.drawable.mask);   
        mask = BitmapFactory.decodeResource(getResources(), R.drawable.mask);  
          
        //获取初始位置的时候，下方箭头所在的区域          
        animEnabled = true; //同时，启动动画   
    }  
      
//  public void setChartClickListener(ChartClickListener l){   
//      this.clickListener = l;   
//  }   
      
    //计算总和   
    private int sum(int[] values){  
        int sum = 0;  
        for(int i=0; i<values.length;i++){  
            sum += values[i];  
        }  
          
        return sum;  
    }  
      
    /** 
     * 根据每部分所占的比例，来计算每个区域在整个圆中所占的角度 
     * 但是，有个小细节，就是计算的时候注意，可能并不能整除的情况，这个时候，为了 
     * 避免所有的角度和小于360度的情况，姑且将剩余的部分送给某个部分，反正也不影响 
     * @return 
     */  
    private int[] getDegrees(){  
        int sum = this.sum(values);  
          
        int[] degrees = new int[values.length];  
        for(int i=0; i<values.length; i++){  
            degrees[i] = (int)Math.floor((double)((double)values[i]/(double)sum)*360);  
            //Log.v("Angle", angles[i]+"");   
        }  
        int angleSum = this.sum(degrees);  
        if(angleSum != 360){  
            //上面的计算可能导致和小于360   
            int c = 360 - angleSum;  
            degrees[values.length-1] += c; //姑且让最后一个的值稍大点   
        }  
          
        return degrees;  
    }  
      
    /** 
     * 重写这个方法来画出整个界面 
     */  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
  
        if(animEnabled){  
            /** 
             * 说明是启动的时候，需要旋转着画出饼图 
             */  
            Log.e(TAG, "anim enabled");  
            if(animState == ANIMATION_STATE_DOWN){  
  
                animStartTime = SystemClock.uptimeMillis();  
                animState = ANIMATION_STATE_RUNNING;  
                  
            }  
                  
            final long currentTimeDiff = SystemClock.uptimeMillis() - animStartTime;  
            int currentMaxDegree = (int)((float)currentTimeDiff/ANIMATION_DURATION*360f);  
              
            Log.e(TAG, "当前最大的度数为:"+currentMaxDegree);  
              
            if(currentMaxDegree >= 360){  
                //动画结束状态,停止绘制   
                currentMaxDegree = 360;  
                animState = ANIMATION_STATE_DOWN;  
                animEnabled = false;  
            }  
              
            int[] degrees = getDegrees();  
            int startAngle = this.startDegree;  
              
            //获取当前时刻最大可以旋转的角度所位于的区域   
            int maxIndex = getEventPart(currentMaxDegree);  
              
            //根据不同的颜色画饼图   
            for(int i=0; i<= maxIndex; i++){  
                int currentDegree = degrees[i];  
                  
                if(i== maxIndex){  
                    //对于当前最后一个绘制区域，可能只是一部分，需要获取其偏移量   
                    currentDegree = getOffsetOfPartStart(currentMaxDegree, maxIndex);  
                }  
                  
                if(i > 0){  
                    //注意，每次画饼图，记得计算startAngle   
                    startAngle += degrees[i-1];  
                }  
                  
                paint.setColor(colors[i]);  
                canvas.drawArc(OVAL, startAngle, currentDegree, true, paint);  
            }  
              
            if(animState == ANIMATION_STATE_DOWN){  
  
                //如果动画结束了，则调整当前箭头位于所在区域的中心方向   
                onStop();  
                  
            }else{  
                postInvalidate();      
            }     
              
        }else{  
  
            int[] degrees = getDegrees();  
            int startAngle = this.startDegree;  
              
            /** 
             * 每个区域的颜色不同，但是这里只要控制好每个区域的角度就可以了，整个是个圆 
             */  
            for(int i=0; i<values.length; i++){  
                paint.setColor(colors[i]);  
                if(i>0){  
                    startAngle += degrees[i-1];  
                }  
                canvas.drawArc(OVAL, startAngle, degrees[i], true, paint);  
  
            }             
        }  
          
          
        /** 
         * 画出饼图之后，画遮罩图片，这样图片就位于饼图之上了，形成了遮罩的效果 
         */  
        canvas.drawBitmap(mask, 0, 0, maskPaint);  
          
        /** 
         * 根据当前计算得到的箭头所在区域显示该区域代表的信息 
         */  
        if(currentTargetIndex >= 0){  
            String title = titles[currentTargetIndex];  
            textPaint.setColor(colors[currentTargetIndex]);  
            //简单作个计算,让文字居中显示   
            int width = title.length()*17;  
            canvas.drawText(title, 157-width/2+3, 383, textPaint);  
        }  
  
    }  
  
      
    /** 
     * 处理饼图的转动 
     */  
    public boolean onTouchEvent(MotionEvent event){  
          
        if(animEnabled && animState == ANIMATION_STATE_RUNNING){  
            return super.onTouchEvent(event);  
        }  
          
        Point eventPoint = getEventAbsoluteLocation(event);  
        computeCenter(); //计算中心坐标   
          
        //计算当前位置相对于x轴正方向的角度   
        //在下面这个方法中计算了eventRadius的   
        int newAngle = getEventAngle(eventPoint, center);   
          
        int action = event.getAction();  
          
        switch (action) {  
        case MotionEvent.ACTION_DOWN:  
              
            lastEventPoint = eventPoint;  
              
            if(eventRadius > getRadius()){  
                /** 
                 * 只有点在饼图内部才需要处理转动,否则直接返回 
                 */  
                Log.e(TAG, "当前位置超出了半径："+eventRadius+">"+getRadius());  
                return super.onTouchEvent(event);  
            }  
              
            break;  
        case MotionEvent.ACTION_MOVE:  
            //这里处理滑动   
            rotate(eventPoint, newAngle);  
              
            //处理之后，记得更新lastEventPoint   
            lastEventPoint = eventPoint;  
            break;  
              
        case MotionEvent.ACTION_UP:  
            onStop();  
            break;  
  
        default:  
            break;  
        }  
          
        return true;  
    }      
      
    /** 
     * 当我们停止旋转的时候，如果当前下方箭头位于某个区域的非中心位置，则我们需要计算 
     * 偏移量，并且将箭头指向中心位置 
     */  
    private void onStop() {  
          
        int targetAngle = getTargetDegree();  
        currentTargetIndex = getEventPart(targetAngle);  
  
        int offset = getOffsetOfPartCenter(targetAngle, currentTargetIndex);  
          
        /** 
         * offset>0,说明当前箭头位于中心位置右边，则所有区域沿着顺时针旋转offset大小的角度 
         * offset<0,正好相反 
         */  
        startDegree += offset;  
          
        postInvalidateDelayed(200);  
    }  
  
    private void rotate(Point eventPoint, int newDegree) {  
          
        //计算上一个位置相对于x轴正方向的角度   
        int lastDegree = getEventAngle(lastEventPoint, center);   
          
  
        /** 
         * 其实转动就是不断的更新画圆弧时候的起始角度，这样，每次从新的起始角度重画圆弧就形成了转动的效果 
         */  
        startDegree += newDegree-lastDegree;  
          
        //转多圈的时候，限定startAngle始终在-360-360度之间   
        if(startDegree >= 360){  
            startDegree -= 360;  
        }else if(startDegree <= -360){  
            startDegree += 360;  
        }  
          
        Log.e(TAG, "当前startAngle："+startDegree);  
          
        //获取当前下方箭头所在的区域，这样在onDraw的时候就会转到不同区域显示的是当前区域对应的信息   
        int targetDegree = getTargetDegree();  
        currentTargetIndex = getEventPart(targetDegree);          
          
        //请求重新绘制界面，调用onDraw方法   
        postInvalidate();  
  
    }  
      
      
    /** 
     * 获取当前事件event相对于屏幕的坐标 
     * @param event 
     * @return 
     */  
    protected Point getEventAbsoluteLocation(MotionEvent event){  
        int[] location = new int[2];  
        this.getLocationOnScreen(location); //当前控件在屏幕上的位置       
          
        int x = (int)event.getX();  
        int y = (int)event.getY();  
          
        x += location[0];  
        y += location[1]; //这样x,y就代表当前事件相对于整个屏幕的坐标   
          
        Point p = new Point(x, y);  
          
        Log.v(TAG, "事件坐标："+p.toString());  
          
        return p;  
    }  
      
    /** 
     * 获取当前饼图的中心坐标，相对于屏幕左上角 
     */  
    protected void computeCenter(){  
        if(center == null){  
            int x = (int)OVAL.left + (int)((OVAL.right-OVAL.left)/2f);  
            int y = (int)OVAL.top + (int)((OVAL.bottom - OVAL.top)/2f)+50; //状态栏的高度是50   
            center = new Point(x,y);  
            //Log.v(TAG, "中心坐标："+center.toString());               
        }  
    }  
      
    /** 
     * 获取半径 
     */  
    protected int getRadius(){  
        int radius = (int)((OVAL.right-OVAL.left)/2f);  
        //Log.v(TAG, "半径："+radius);   
        return radius;  
    }  
      
    /** 
     * 获取事件坐标相对于饼图的中心x轴正方向的角度 
     * 这里就是坐标系的转换，本例中使用饼图的中心作为坐标中心，就是我们从初中到大学一直使用的"正常"坐标系。 
     * 但是涉及到圆的转动，本例中一律相对于x正方向顺时针来计算某个事件在坐标系中的位置 
     * @param eventPoint 
     * @param center 
     * @return 
     */  
    protected int getEventAngle(Point eventPoint, Point center){  
        int x = eventPoint.x - center.x;//x轴方向的偏移量   
        int y = eventPoint.y - center.y; //y轴方向的偏移量   
          
        //Log.v(TAG, "直角三角形两直边长度："+x+","+y);   
          
        double z = Math.hypot(Math.abs(x), Math.abs(y)); //求直角三角形斜边的长度   
          
        //Log.v(TAG, "斜边长度："+z);   
          
        eventRadius = (int)z;  
        double sinA = (double)Math.abs(y)/z;  
          
        //Log.v(TAG, "sinA="+sinA);   
          
        double asin = Math.asin(sinA); //求反正玄，得到当前点和x轴的角度,是最小的那个   
          
        //Log.v(TAG, "当前相对偏移角度的反正弦："+asin);   
          
        int degree = (int)(asin/3.14f*180f);  
          
        //Log.v(TAG, "当前相对偏移角度："+angle);   
          
        //下面就需要根据x,y的正负，来判断当前点和x轴的正方向的夹角   
        int realDegree = 0;  
        if(x<=0 && y<=0){  
            //左上方，返回180+angle   
              
            realDegree = 180+degree;  
              
        }else if(x>=0 && y<=0){  
            //右上方，返回360-angle   
            realDegree = 360-degree;  
        }else if(x<=0 && y>=0){  
            //左下方，返回180-angle   
            realDegree = 180-degree;  
        }else{  
            //右下方,直接返回   
            realDegree = degree;  
              
        }  
          
        //Log.v(TAG, "当前事件相对于中心坐标x轴正方形的顺时针偏移角度为："+realAngle);   
          
        return realDegree;  
  
    }  
      
    /** 
     * 获取当前下方箭头位置相对于startDegree的角度值 
     * 注意，下方箭头相对于x轴正方向是90度 
     * @return 
     */  
    protected int getTargetDegree(){  
          
        int targetDegree = -1;  
          
        int tmpStart = startDegree;  
          
        /** 
         * 如果当前startAngle为负数，则直接+360，转换为正值 
         */  
        if(tmpStart < 0){  
            tmpStart += 360;  
        }  
          
          
        if(tmpStart < 90){  
            /** 
             * 如果startAngle小于90度（可能为负数） 
             */  
            targetDegree = 90 - tmpStart;  
        }else{  
            /** 
             * 如果startAngle大于90，由于在每次计算startAngle的时候，限定了其最大为360度，所以 
             * 直接可以按照如下公式计算 
             */  
            targetDegree = 360 + 90 - tmpStart;  
        }  
          
        //Log.e(TAG, "Taget Angle:"+targetDegree+"startAngle:"+startAngle);   
          
        return targetDegree;  
    }  
      
    /** 
     *判断角度为degree坐落在饼图的哪个部分 
     *注意，这里的角度一定是正值，而且不是相对于x轴正方向，而是相对于startAngle 
     *返回当前部分的索引 
     * @param degree 
     * @return 
     */  
    protected int getEventPart(int degree){  
        int currentSum = 0;  
          
        for(int i=0; i<degrees.length; i++){  
            currentSum += degrees[i];  
            if(currentSum >= degree){  
                return i;  
            }  
        }  
          
        return -1;  
    }  
      
    /** 
     * 在已经得知了当前degree位于targetIndex区域的情况下，计算angle相对于区域targetIndex起始位置的偏移量 
     * @param degree 
     * @param targetIndex 
     * @return 
     */  
    protected int getOffsetOfPartStart(int degree, int targetIndex){  
        int currentSum = 0;  
        for(int i=0; i<targetIndex; i++){  
            currentSum += degrees[i];  
        }  
          
        int offset = degree - currentSum;  
          
        return offset;        
    }  
      
    /** 
     * 在已经得知了当前degree位于targetIndex区域的情况下，计算angle相对于区域targetIndex中心位置的偏移量 
     * 这个是当我们停止旋转的时候，通过计算偏移量，来使得箭头指向当前区域的中心位置 
     * @param degree 
     * @param targetIndex 
     * @return 
     */  
    protected int getOffsetOfPartCenter(int degree, int targetIndex){  
        int currentSum = 0;  
        for(int i=0; i<=targetIndex; i++){  
            currentSum += degrees[i];  
        }  
          
        int offset = degree - (currentSum-degrees[targetIndex]/2);   
          
        //超过一半,则offset>0；未超过一半,则offset<0   
        return offset;  
    }  
      
  
}  

