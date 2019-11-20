package com.thunder.as31try.interview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thunder.as31try.R;

import java.util.LinkedList;

/**
 * viewGroup遍历view
 */

public class TestViewNodeActivity extends AppCompatActivity {

    private LinearLayout mLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_node);
        mLl = ((LinearLayout) findViewById(R.id.ll));
        travelView(mLl);
    }


    public void travelView(View rootView){
        LinkedList<View> viewsDeque = new LinkedList<>();
        viewsDeque.push(rootView);

        while (!viewsDeque.isEmpty()){
            View poll = viewsDeque.poll();
            Log.d("TAG", "travelView: " + poll.getTag().toString());
            if (poll instanceof ViewGroup){
                ViewGroup vg = (ViewGroup) poll;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    viewsDeque.addLast(vg.getChildAt(i));
                }
            }
        }
    }
}
