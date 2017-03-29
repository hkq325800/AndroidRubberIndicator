package com.liangfeizc.rubberindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
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
        implements RubberIndicator.OnMoveListener, View.OnTouchListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private int vpCount = 6;
    private int viewCount = 3;
    private TextView mTextView;
    private RubberIndicator mRubberView;
    private RubberIndicator mRubberVP;
    private GestureDetectorCompat mDetectorView, mDetectorVP;

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
////        outState.putInt("viewFocus", mRubberView.getFocusPosition());
////        outState.putInt("vpFocus", mRubberVP.getFocusPosition());
//    }
//
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        mRubberView.setCount(viewCount, savedInstanceState.getInt("viewFocus"));
//        mRubberVP.setCount(vpCount, savedInstanceState.getInt("vpFocus"));
        updateFocusPosition();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set gesture detector
        mDetectorView = new GestureDetectorCompat(this, new MyGestureListener(MyGestureListener.TYPE_0));
        mDetectorVP = new GestureDetectorCompat(this, new MyGestureListener(MyGestureListener.TYPE_1));

        mRubberView = (RubberIndicator) findViewById(R.id.mRubberView);
        mRubberVP = (RubberIndicator) findViewById(R.id.mRubberVP);
        mTextView = (TextView) findViewById(R.id.mTextView);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new MyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("this", String.valueOf(position));
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return vpCount;
            }
        });
        if (savedInstanceState == null) {
            //2是初始位置
//            mRubberView.setCount(viewCount)
//                    .setFocusPosition(2);
//            mRubberVP.setCount(vpCount)
//                    .setFocusPosition(2);
            mRubberView.setCount(viewCount, 2);
            mRubberVP.setCount(vpCount, 2);
        }
        mRubberView.setOnMoveListener(this);
        mRubberView.setAnimDuration(600);
        mRubberVP.setOnMoveListener(this);
        updateFocusPosition();
        //2是初始位置
        mViewPager.setCurrentItem(2);
        mViewPager.setOnTouchListener(this);
    }

    //activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetectorView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //mViewPager
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        mDetectorVP.onTouchEvent(event);
        return false;
    }

    public void moveToRight(RubberIndicator view) {
        view.moveToRight();
    }

    public void moveToLeft(RubberIndicator view) {
        view.moveToLeft();
    }

    //向左移动结束的回调
    @Override
    public void onMovedToLeft() {
        updateFocusPosition();
    }

    //向右移动结束的回调
    @Override
    public void onMovedToRight() {
        updateFocusPosition();
    }

    private void updateFocusPosition() {
        mTextView.setText("Focusing on " + mRubberView.getFocusPosition());
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String TAG = "Gestures";
        static final int TYPE_0 = 0;
        static final int TYPE_1 = 1;
        int type;

        MyGestureListener(int type) {
            this.type = type;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(TAG, "swipe right");
                    if (type == TYPE_0) {
                        moveToRight(mRubberView);
                    } else if (type == TYPE_1) {
                        moveToRight(mRubberVP);
                    }
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d(TAG, "swipe left");
                    if (type == TYPE_0) {
                        moveToLeft(mRubberView);
                    } else if (type == TYPE_1) {
                        moveToLeft(mRubberVP);
                    }
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
}
