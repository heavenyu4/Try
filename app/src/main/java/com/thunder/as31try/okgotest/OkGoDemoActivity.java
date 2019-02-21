package com.thunder.as31try.okgotest;

import android.app.Activity;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.thunder.as31try.R;

import java.io.File;

public class OkGoDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_go_demo);

        downloadPic();

    }

    private void downloadPic() {
        String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515671004277&di=b7f172dde2f7d25b6dd446fed596779e&imgtype=0&src=http%3A%2F%2Fimg5.qiyipic.com%2Fimage%2Fqitan%2Fqitan_c1c9629c09094f858365f006a245c3f5_600x477.jpg";
        OkGo.<File>get(imgUrl).execute(new FileCallback() {
            @Override
            public void onSuccess(Response<File> response) {
                LogUtils.d(response.body());
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                LogUtils.e(response.body());
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                LogUtils.d(progress);
            }
        });

    }
}
