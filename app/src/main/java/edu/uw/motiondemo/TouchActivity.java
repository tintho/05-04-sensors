package edu.uw.motiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class TouchActivity extends Activity {

    private static final String TAG = "Touch";

    private DrawingSurfaceView view;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (DrawingSurfaceView)findViewById(R.id.drawingView);

        detector =  new GestureDetector(this, new MyGestureListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //this is added
        boolean guestured = detector.onTouchEvent(event);
//        if(guestured) {
//            return true;
//        } we want to do other things
        float x = event.getX();
        float y = event.getY();

        int pointerIndex = MotionEventCompat.getActionIndex(event);
        int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);

        //handle action
        int action = MotionEventCompat.getActionMasked(event);
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG,"Touch!");

                view.addTouch(pointerId, event.getX(pointerIndex), event.getY(pointerIndex));

                return true;
            case (MotionEvent.ACTION_MOVE) : //move

                int pointerCount = event.getPointerCount();
                for (int index = 0; index < pointerCount; index++) {
                    int pId = event.getPointerId(index);
                    view.moveTouch(pId, event.getX(index), event.getY(index));
                }

                return true;
            case MotionEvent.ACTION_POINTER_DOWN:

                view.addTouch(pointerId, event.getX(pointerIndex), event.getY(pointerIndex));

                Log.v(TAG, "Another finger!");
                return true;
            case MotionEvent.ACTION_POINTER_UP:

                view.removeTouch(pointerId);

                Log.v(TAG, "A fingers left!");
                return true;
            case MotionEvent.ACTION_UP:

                view.removeTouch(pointerId);

                Log.v(TAG, "Last finger left");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        // this is specifically what we did in class
        @Override
        public boolean onDown(MotionEvent e) {
            return true; //whether or not we handled the gesture //we handled this
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //what do we want to do on fling
            Log.v(TAG, "Fling!");
            view.ball.dx = .03f*velocityX;
            view.ball.dy = .03f*velocityY;

            return true; //we handled this
        }
    }
}
