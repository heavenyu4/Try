package com.hyw.as31try.testpopupwindow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.hyw.as31try.R;

public class TestPopupWindowActivity extends AppCompatActivity {

    private View mTv;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_popup_window);
        initData();
    }

    private void initData() {
        mTv = findViewById(R.id.tv);

        mTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                View view = LayoutInflater.from(TestPopupWindowActivity.this).inflate(R.layout.fragment_a, null);
                mPopupWindow = new PopupWindow(view, 300,300);
                mPopupWindow.setOutsideTouchable(false);
                mPopupWindow.setFocusable(false);
                mPopupWindow.showAsDropDown(mTv);
            }
        }, 2000);

        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }


}
