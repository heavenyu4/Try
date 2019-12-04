package com.thunder.as31try.interview;

import android.annotation.TargetApi;
import android.os.Build;
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


    /**
     * 广度优先搜索，先入先出 队列
     * @param rootView
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
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
    /**
     * 深度优先搜索，先入后出 栈
     * @param rootView
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void travelViewDeep(View rootView){
        LinkedList<View> viewsDeque = new LinkedList<>();
        viewsDeque.push(rootView);

        while (!viewsDeque.isEmpty()){
            View poll = viewsDeque.pop();
            Log.d("TAG", "travelView: " + poll.getTag().toString());
            if (poll instanceof ViewGroup){
                ViewGroup vg = (ViewGroup) poll;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    viewsDeque.push(vg.getChildAt(i));
                }
            }
        }
    }
}
