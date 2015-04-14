package org.khk.robotctf.robotctfcontroller.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import org.khk.robotctf.robotctfcontroller.ControllerActivity;

/**
 * Created by Joe Dailey on 4/12/2015.
 */
public class ControllerView extends View{

    private ControllerActivity parentActivity;
    private Context context;
    private Paint brush;

    private V_Throttle vKnob;
    private H_Throttle hKnob;

    private void init(Context context){
        this.context = context;
        this.brush = new Paint();
        this.vKnob = new V_Throttle();
        this.hKnob = new H_Throttle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        brush.setColor(Color.CYAN);
        canvas.drawCircle(50, 50, 10, brush);


        vKnob.draw(canvas);
        hKnob.draw(canvas);
    }

    public ControllerView(Context context) {
        super(context);
        init(context);
    }
    public ControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public ControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

//        vKnob.trackTouch(ev);
        hKnob.trackTouch(ev);
        return true;
    }

    private class H_Throttle{
        private Paint brush;
        public float knubPosition;// -1.0 - 1.0
        public int padding;
        private int touchID;
        public float trackWidth;

        public H_Throttle(){
            this.brush = new Paint();
            this.knubPosition = 0.5f;
            this.padding = 10;
        }

        public void draw(Canvas canvas){

            int width = getWidth();
            int halfWidth = width/2;
            int height = getHeight();

            brush.setColor(Color.BLACK);
            brush.setStrokeCap(Paint.Cap.ROUND);
            brush.setStrokeWidth(15);

            canvas.drawLine(halfWidth+padding, height/2, halfWidth+width/2-padding, height/2, brush);

            trackWidth = (width/2-20);
            canvas.drawCircle(halfWidth+padding+knubPosition*trackWidth, height/2, 20, brush);

        }

        public void moveKnob(float x){
            if(x < getWidth()/2+10)
                x = getWidth()/2+10;
            if(x > getWidth()-10)
                x = getWidth()-10;

            float realXonTrack = (x - (getWidth()/2+padding));
            knubPosition = realXonTrack/trackWidth;
        }

        public void trackTouch(MotionEvent ev) {
            Log.d("wat", ev.getX() +">"+ getWidth()/2  +"&&"+ ev.getX() +"<"+ getWidth());
            switch(ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if( ev.getX() > getWidth()/2  && ev.getX() < getWidth()){
                        touchID = ev.getPointerId(ev.getActionIndex());
                        moveKnob(ev.getX());
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
//                    if( ev.getPointerId(ev.getActionIndex()) == touchID ){
                        moveKnob(ev.getX());
//                    }
                    break;
                case MotionEvent.ACTION_UP:
//                    if( ev.getPointerId(ev.getActionIndex()) == touchID ){
                        touchID = -1;
                        moveKnob(0.5f);
//                    }
                    break;
            }
        }
    }

    private class V_Throttle{
        private Paint brush;
        public float knubPosition;// 0.0 - 1.0
        public int padding;

        public V_Throttle(){
            this.brush = new Paint();
            this.knubPosition = 0.5f;
            this.padding = 10;

        }

        public void draw(Canvas canvas){

            int width = getWidth();
            int height = getHeight();

            brush.setColor(Color.BLACK);
            brush.setStrokeCap(Paint.Cap.ROUND);
            brush.setStrokeWidth(15);

            canvas.drawLine(width/2/2, padding, width/2/2, height-padding, brush);

            float trackWidth = (width/2-20);
            canvas.drawCircle(padding+knubPosition*trackWidth, height/2, 20, brush);

        }

    }
}
