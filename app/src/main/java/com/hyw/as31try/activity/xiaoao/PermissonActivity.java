//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hyw.as31try.activity.xiaoao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;

import java.util.ArrayList;
import java.util.List;

public class PermissonActivity extends Activity implements OnRequestPermissionsResultCallback {
    private List<String> needRequest;
    private String TAG = "-----PermissonActivity-----";

    public PermissonActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        this.needRequest = new ArrayList();
        this.needRequest = intent.getStringArrayListExtra("needRequest");
//        LogUtil.getInstance(this.TAG).d("needRequest = " + this.needRequest.toString());
        if (this.needRequest != null && this.needRequest.size() > 0) {
            this.request();
        } else {
            this.noneRequest();
        }

    }

    private void request() {
//        LogUtil.getInstance(this.TAG).d("剩余未申请的权限 = " + this.needRequest.toString());
//        LogUtil.getInstance(this.TAG).d("当前申请的权限 = " + (String)this.needRequest.get(0));
        this.tryGetIMEI();
        ActivityCompat.requestPermissions(this, new String[]{(String)this.needRequest.get(0)}, 257);
    }

    private void noneRequest() {
        this.tryGetIMEI();
        this.finish();
    }

    void tryGetIMEI() {
//        String imei = IMEIGet.getIMEI(this);
//        if (!TextUtils.isEmpty(imei) && SDKAppService.dm != null) {
//            SDKAppService.dm.setImeil(imei);
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 257) {
            if (ActivityCompat.checkSelfPermission(this, (String)this.needRequest.get(0)) == 0) {
                if (((String)this.needRequest.get(0)).equals(permissions[0])) {
                    this.needRequest.remove(this.needRequest.get(0));
                }

                if (this.needRequest.size() == 0) {
                    this.noneRequest();
                } else {
                    this.request();
                }
            } else {
                this.noneRequest();
            }
        }

    }
}
