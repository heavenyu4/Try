package com.thunder.as31try.spinnerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.thunder.as31try.R;

public class TestSpinnerActivity extends AppCompatActivity {

    private Spinner mSpinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_spinner);

        mSpinner1 = findViewById(R.id.spinner_1);
        testSpinner1();
    }

    /**
     * 测试：加载数据列，监听选择
     * */
    private void testSpinner1(){
        //原始string数组
        final String[] spinnerItems = {"张三","李四","王二麻子","11","22","33","44","55","77","88","11","22","33","44","55","77","88"};
        //简单的string数组适配器：样式res，数组
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        mSpinner1.setAdapter(spinnerAdapter);
        //选择监听
        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //parent就是父控件spinner
            //view就是spinner内填充的textview,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与positin一致
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
//                LogUtil.i("onItemSelected : parent.id="+parent.getId()+
//                        ",isSpinnerId="+(parent.getId() == R.id.spinner_1)+
//                        ",viewid="+view.getId()+ ",pos="+pos+",id="+id);
//                ToastUtil.showShort(instance,"选择了["+spinnerItems[pos]+"]");
                //设置spinner内的填充文字居中
                //((TextView)view).setGravity(Gravity.CENTER);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }
}
