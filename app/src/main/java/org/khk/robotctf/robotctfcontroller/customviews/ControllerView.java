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
import android.widget.Toast;

import org.khk.robotctf.robotctfcontroller.ControllerActivity;

import java.util.Calendar;

/**
 * Created by Joe Dailey on 4/12/2015.
 */
public class ControllerView extends View{

    private ControllerActivity parentActivity;
    private Context context;
    private Paint brush;

    private V_Throttle vKnob;
    private H_Throttle hKnob;

    private Long fireCheck1;
    private Long fireCheck2;
    private final Long MAX_CLICK_TIME = 200L;

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
        switch(ev.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                fireCheck1 = Calendar.getInstance().getTimeInMillis();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                fireCheck2 = Calendar.getInstance().getTimeInMillis();
                break;
            case MotionEvent.ACTION_UP: {
                long clickDuration = Calendar.getInstance().getTimeInMillis() - fireCheck1;
                if (clickDuration < MAX_CLICK_TIME) {
                    Toast.makeText(getContext(), "Fire!", Toast.LENGTH_SHORT).show();
                }
            }
                break;
            case MotionEvent.ACTION_POINTER_UP: {
                long clickDuration = Calendar.getInstance().getTimeInMillis() - fireCheck2;
                if (clickDuration < MAX_CLICK_TIME) {
                    Toast.makeText(getContext(), "Fire!", Toast.LENGTH_SHORT).show();
                }
            }
                break;
        }

        vKnob.trackTouch(ev);
        hKnob.trackTouch(ev);
        invalidate();
        return true;
    }

    private class H_Throttle{
        private Paint brush;
        public float knubPosition;// -1.0 - 1.0
        public int padding;
        public float trackWidth;
        private int touchID;

        public H_Throttle(){
            this.brush = new Paint();
            this.knubPosition = 0.5f;
            this.padding = 10;
            this.touchID = -1;
        }

        public void draw(Canvas canvas){

            int width = getWidth();
            int halfWidth = width/2;
            int height = getHeight();

            brush.setColor(Color.BLACK);
            brush.setStrokeCap(Paint.Cap.ROUND);
            brush.setStrokeWidth(15);

            canvas.drawLine(halfWidth+padding, height/2, halfWidth+width/2-padding, height/2, brush);

            trackWidth = (width/2-2*padding);
            canvas.drawCircle(halfWidth+padding+knubPosition*trackWidth, height/2, 20, brush);

        }

        public void moveKnob(float x){
            if(x < getWidth()/2+padding)
                x = getWidth()/2+padding;
            if(x > getWidth()-padding)
                x = getWidth()-padding;

            float realXonTrack = (x - (getWidth()/2+padding));
            knubPosition = realXonTrack/trackWidth;

        }

        public void trackTouch(MotionEvent ev) {
            int index = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

            switch(ev.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if( ev.getX(index) > getWidth()/2  && ev.getX(index) < getWidth() && touchID == -1){
                        touchID = ev.getPointerId(index);
                        moveKnob(ev.getX(index));
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if( touchID != -1){
                        moveKnob(ev.getX(ev.findPointerIndex(touchID)));
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    if( ev.getPointerId(index) == touchID ){
                        touchID = -1;
                        this.knubPosition = 0.5f;
                    }
                    break;
            }
        }
    }

    private class V_Throttle{
        private Paint brush;
        public float knubPosition;// 0.0 - 1.0
        public int padding;
        private float trackWidth;
        private int touchID;

        public V_Throttle(){
            this.brush = new Paint();
            this.knubPosition = 0.5f;
            this.padding = 10;
            touchID = -1;
        }

        public void draw(Canvas canvas){

            int width = getWidth();
            int height = getHeight();

            brush.setColor(Color.BLACK);
            brush.setStrokeCap(Paint.Cap.ROUND);
            brush.setStrokeWidth(15);

            canvas.drawLine(width/2/2, padding, width/2/2, height-padding, brush);

            trackWidth = (height-2*padding);
            canvas.drawCircle(width/2/2, padding+knubPosition*trackWidth, 20, brush);

        }

        public void moveKnob(float y){
            if(y < padding)
                y = padding;
            if(y > getHeight()-padding)
                y = getHeight()-padding;

            float realYonTrack = (y - (padding));
            knubPosition = realYonTrack/trackWidth;
            int i = 0;
        }

        public void trackTouch(MotionEvent ev) {
            int index = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

            switch(ev.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if( ev.getX(index) > 0  && ev.getX(index) < getWidth()/2 && touchID == -1){
                        touchID = ev.getPointerId(index);
                        moveKnob(ev.getY(index));
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if( touchID != -1){
                        moveKnob(ev.getY(ev.findPointerIndex(touchID)));
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    if( ev.getPointerId(index) == touchID ){
                        touchID = -1;
                        this.knubPosition = 0.5f;
                    }
                    break;
            }
        }

    }
}
