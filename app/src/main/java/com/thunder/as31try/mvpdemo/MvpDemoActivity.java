package com.thunder.as31try.mvpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.thunder.as31try.R;

public class MvpDemoActivity extends AppCompatActivity implements MvpContract.View {
    String TAG = getClass().getSimpleName();
    MvpContract.Presenter presenter;
    private MvpPresenter mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_demo);
        mvpPresenter = new MvpPresenter(this);
        mvpPresenter.loadMore();
    }

    @Override
    public void showProcess(int processs) {
        Log.d(TAG, "showProcess: " + processs);
    }
}
