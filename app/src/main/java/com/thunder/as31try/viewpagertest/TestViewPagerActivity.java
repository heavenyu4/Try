package com.thunder.as31try.viewpagertest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.thunder.as31try.R;

import java.util.ArrayList;
import java.util.List;

public class TestViewPagerActivity extends AppCompatActivity {


    private ViewPager mVp;
    private int currentItem;
    private Handler mHandler;
    private List<View> list;
    private int count = 3;
    String TAG = getClass().getSimpleName();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_pager);

        mVp = findViewById(R.id.vp);

        list = new ArrayList<>();
        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.leak_canary_icon);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView iv1_copy = new ImageView(this);
        iv1_copy.setImageResource(R.drawable.leak_canary_icon);
        iv1_copy.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.pic);
        iv2.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.ic_launcher);
        iv3.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageView iv3_copy = new ImageView(this);
        iv3_copy.setImageResource(R.drawable.ic_launcher);
        iv3_copy.setScaleType(ImageView.ScaleType.FIT_XY);


        list.add(iv3_copy);
        list.add(iv1);
        list.add(iv2);
        list.add(iv3);
        list.add(iv1_copy);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(list);
        mVp.setAdapter(myPagerAdapter);

        mHandler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = currentItem % (count + 1) + 1;
                Log.i(TAG, "aft: curr:" + currentItem + " count:" + count);
                if (currentItem == 1) {
                    mVp.setCurrentItem(currentItem, false);
                    mHandler.post(runnable);
                } else {
                    mVp.setCurrentItem(currentItem);
                    mHandler.postDelayed(runnable, 3_000);
                }
            }
        };

        mHandler.post(runnable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
