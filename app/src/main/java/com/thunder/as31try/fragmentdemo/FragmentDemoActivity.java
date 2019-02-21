package com.thunder.as31try.fragmentdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thunder.as31try.R;

public class FragmentDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private AFragment mAFragment;
    private BFragment mBFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);

        findViewById(R.id.tv_a).setOnClickListener(this);
        findViewById(R.id.tv_b).setOnClickListener(this);
        mAFragment = new AFragment();
//        FragmentUtils.add(getSupportFragmentManager(),
//                mAFragment,
//                R.id.fl_container,
//                false,
//                true
//        );
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, mAFragment, "A")
                .addToBackStack("A")
                .commit();
        mBFragment = new BFragment();
//        FragmentUtils.add(getSupportFragmentManager(),
//                mBFragment,
//                R.id.fl_container,
//                true,
//                true
//        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_a:
//                FragmentUtils.add(getSupportFragmentManager(),
//                        new AFragment(),
//                        R.id.fl_container
//                        );
//                FragmentUtils.replace(getSupportFragmentManager(), mAFragment, R.id.fl_container, true);
                if (getSupportFragmentManager().findFragmentByTag("A")!= null){
                    getSupportFragmentManager().beginTransaction().show(mAFragment).hide(mBFragment).commit();
                }else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fl_container, mAFragment, "A")
                            .addToBackStack("A")
                            .commit();
                }
                break;
            case R.id.tv_b:
//                FragmentUtils.add(getSupportFragmentManager(),
//                        new BFragment(),
//                        R.id.fl_container
//                );
//                FragmentUtils.replace(getSupportFragmentManager(), mBFragment, R.id.fl_container, true);
                if (getSupportFragmentManager().findFragmentByTag("B")!= null){
                    getSupportFragmentManager().beginTransaction().show(mBFragment).hide(mAFragment).commit();
                }else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fl_container, mBFragment, "B")
                            .addToBackStack("B")
                            .commit();
                }
                break;
        }
    }
}
