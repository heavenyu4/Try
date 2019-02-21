package com.thunder.as31try.exceptioncheck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.thunder.as31try.R;

public class TestExceptionActivity extends AppCompatActivity {
    String TAG = "TestExceptionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_exception);
        int num = getNum();
        Log.d(TAG, "onCreate: num: "+num);
    }

    public int getNum(){
        try {
            int a = 1/0;
            return 1;
        } catch (Exception e) {
            return 2;
        }finally{
//int b = 1/0;  //有这句会报崩溃java.lang.ArithmeticException: divide by zero
            return 3;
        }
    }

}
