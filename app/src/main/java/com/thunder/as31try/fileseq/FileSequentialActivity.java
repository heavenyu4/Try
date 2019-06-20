package com.thunder.as31try.fileseq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.thunder.as31try.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileSequentialActivity extends AppCompatActivity {
    String TAG = "FileSequentialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_sequential);

        FileSequential fileSequential = new FileSequential();
        fileSequential.init();

//        testExec();
    }

    /**
     *测试exec的返回参数获取
     */
    private void testExec() {
        try {
            //这里的 grep过滤并没有作用, 但是用字符串数组的形式吧, 又会报错找不到/dev/block目录
//            目前还没有找到什么办法
            String [] cmds = new String[2];
            cmds[0] = "ls -al  /dev/block ";
            cmds[1] = "| grep sd";
//            Process ls = Runtime.getRuntime().exec(cmds);
            Process ls = Runtime.getRuntime().exec("ls -al  /dev/block | grep sd");
            InputStream inputStream = ls.getInputStream();

            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                Log.d(TAG, "checkDiskProp: " + line);
            }
            inputStream.close();

        }catch (Exception e){
            Log.e(TAG, "checkDiskProp: ", e);
        }
    }
}
