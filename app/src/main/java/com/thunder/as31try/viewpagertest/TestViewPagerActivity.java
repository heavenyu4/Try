package com.thunder.as31try.viewpagertest;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.thunder.as31try.R;

import java.util.ArrayList;
import java.util.List;

public class TestViewPagerActivity extends AppCompatActivity {


    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_pager);

        mVp = findViewById(R.id.vp);

        List<View> list = new ArrayList<>();
        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.leak_canary_icon);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.pic);
        iv2.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.ic_launcher);
        iv3.setScaleType(ImageView.ScaleType.FIT_XY);



        list.add(iv1);
        list.add(iv2);
        list.add(iv3);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(list);
        mVp.setAdapter(myPagerAdapter);


    }
}
