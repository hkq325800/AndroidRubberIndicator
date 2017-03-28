package com.liangfeizc.rubberindicator;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.liangfeizc.RubberIndicator;

public class MainActivity extends AppCompatActivity
        implements RubberIndicator.OnMoveListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private TextView mTextView;
    private RubberIndicator mRubberIndicator;
    private RubberIndicator mRubberIndicator1;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("focus", mRubberIndicator.getFocusPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRubberIndicator.setCount(5, savedInstanceState.getInt("focus"));
        updateFocusPosition();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set gesture detector
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        mRubberIndicator = (RubberIndicator) findViewById(R.id.rubber);
        mRubberIndicator1 = (RubberIndicator) findViewById(R.id.rubber1);
        mTextView = (TextView) findViewById(R.id.focus_position);
        if (savedInstanceState == null) {
            mRubberIndicator.setCount(5, 2);
            mRubberIndicator1.setCount(5, 2);
        }
        mRubberIndicator.setOnMoveListener(this);
        mRubberIndicator1.setOnMoveListener(this);
        updateFocusPosition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void moveToRight(View view) {
        mRubberIndicator.moveToRight();
        mRubberIndicator1.moveToRight();
    }

    public void moveToLeft(View view) {
        mRubberIndicator.moveToLeft();
        mRubberIndicator1.moveToLeft();
    }

    @Override
    public void onMovedToLeft() {
        updateFocusPosition();
    }

    @Override
    public void onMovedToRight() {
        updateFocusPosition();
    }

    private void updateFocusPosition() {
        mTextView.setText("Focusing on " + mRubberIndicator.getFocusPosition());
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String TAG = "Gestures";

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(TAG, "swipe right");
                    moveToRight(null);
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(TAG, "swipe left");
                    moveToLeft(null);
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}
