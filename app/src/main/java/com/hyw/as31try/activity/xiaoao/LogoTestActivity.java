package com.hyw.as31try.activity.xiaoao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyw.as31try.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW;

public class LogoTestActivity extends AppCompatActivity {
    private String TAG = "LogoTestActivity";
    private MediaPlayer mMediaPlayer;
    private SurfaceView mSurfaceView;

    Handler mHandler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    requestPermissionByActivity();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_test);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFlash();
            }
        }, 500);
//        loadFlash();
        media(this, true);
//        requestMyPermission();
        requestSecondPermission();

    }

    private void requestPermissionByActivity() {
        ArrayList<String> map = new ArrayList<>();
        map.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Intent intent = new Intent(this, PermissonActivity.class);
        intent.putStringArrayListExtra("needRequest", map);
        startActivity(intent);
    }

    private void requestMyPermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 100);
        }
    }

    private void requestSecondPermission() {
        String[] permissions = {Manifest.permission.READ_PHONE_STATE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 101);
        }
    }

    public void media(final Activity activity, final boolean hascg) {
        mSurfaceView = new SurfaceView(activity);
        mSurfaceView.getHolder().setKeepScreenOn(true);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(3);
//        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                Log.d(TAG, "onPrepared: ");
//                mp.start();
//            }
//        });
        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion: ");
                mp.release();
//                if (hascg) {
//                    SplashManager.hascg(activity, surfaceView);
//                } else {
//                    SplashManager.nocg(activity, surfaceView);
//                }

            }
        });

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated: ");
                if (mMediaPlayer != null) {
                    mMediaPlayer.setDisplay(holder);
                    mMediaPlayer.start();
                }
                mHandler.sendEmptyMessageDelayed(1, 50);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged: ");
//                if (mMediaPlayer != null) {
//                    //避免黑屏 只有声音
//                    mMediaPlayer.setDisplay(holder);
//                    if (!mMediaPlayer.isPlaying()) {
//                        mMediaPlayer.start();
//                    }
//                }

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed: ");
                //按home键时 暂停播放
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
            }
        });
        mSurfaceView.setZOrderOnTop(true);
        mSurfaceView.setZOrderMediaOverlay(true);
        try {
            AssetFileDescriptor fd = activity.getAssets().openFd("logo.mp4");
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mMediaPlayer.setLooping(false);
//            mMediaPlayer.prepareAsync();
            mMediaPlayer.prepare();
//                    NativeCallJava.commonInterfaceFun(13, "haptic.game_xajh.open_rtol");
        } catch (Exception e) {
            e.printStackTrace();
        }
        activity.addContentView(mSurfaceView, new ViewGroup.LayoutParams(-1, -1));
//        Dialog dialog = new Dialog(activity);
//        dialog.setContentView(mSurfaceView, new ViewGroup.LayoutParams(-1, -1));
//        //            dialogWindow.setType(FIRST_APPLICATION_WINDOW);
//
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION);
//        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: requestCode: " + requestCode + " permissions: " + Arrays.toString(permissions)
                + " grantResults: " + Arrays.toString(grantResults));
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow: ");
        super.onDetachedFromWindow();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        if (mSurfaceView != null) {
            mSurfaceView = null;
        }

    }


    /**
     * 展示闪屏页面, B站要求闪屏页不能有拉伸
     * 这里座特殊处理, 去掉assets里的闪屏图, core不去展示闪屏了
     * 渠道这里展示闪屏
     * 这里添加支持了720*1280 1080*1920 2种分辨率的闪屏图
     */
    private void loadFlash() {
        Log.d(TAG, "loadFlash: ");
        final Activity activity = this;
        try {
            final int flashId = activity.getResources().getIdentifier("common_flash", "drawable", activity.getPackageName());
            Log.d(TAG, "loadFlash: resid: " + flashId);
            if (flashId == 0) {
                //flashid没找到
                // 闪屏失败的话 不要影响后续初始化
//                doInit();
                return;
            }
            ImageView view = new ImageView(activity);
            //保持闪屏图的比例, 我们将主图放在中心, 然后周边填充白色
            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setImageResource(flashId);


            final Dialog dialog = new Dialog(activity);
            Window dialogWindow = dialog.getWindow();
//            dialogWindow.setType(FIRST_APPLICATION_WINDOW);
            WindowManager.LayoutParams lp = null;
            // 设置无标题
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // 设置全屏
            if (dialogWindow == null || (lp = dialogWindow.getAttributes()) == null) {
                Log.e(TAG, "loadFlash: dialogWindow: " + dialogWindow + " attributes: " + lp);
                Log.e(TAG, "loadFlash: skip flash");
                // 闪屏失败的话 不要影响后续初始化
//                doInit();
                return;
            }
            //设置全屏
            dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            //设置隐藏虚拟按钮
            lp.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;

            //设置适配刘海屏
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                // 延伸显示区域到刘海
//                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//                // 设置页面全屏显示
//                dialogWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            }
//            dialogWindow.setAttributes(lp);

            //修复类似小米11手机安卓11系统上的bug
//            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                //android10以上有深色模式, 有可能会反色, 如果游戏没有设置主题颜色的话, 所以这里这个view禁止使用深色模式
//                dialogWindow.getDecorView().setForceDarkAllowed(false);
//            }
            //将view加入dialog
            LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.setContentView(linearLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //保持闪屏图的比例, 我们将主图放在中心, 然后周边填充白色
//            dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
            dialogWindow.setBackgroundDrawableResource(android.R.color.white);
            dialog.setCancelable(false);
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            dialog.show();

            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //防止Activity被销毁
                            if(activity.isFinishing()){
                                return;
                            }
                            dialog.dismiss();
//                            doInit();
                        }
                    });
                }
            }, 3_000);
        } catch (Throwable e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 闪屏失败的话 不要影响后续初始化
//                    doInit();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Process.killProcess(Process.myPid());
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        if (mMediaPlayer != null) {
            boolean playing = mMediaPlayer.isPlaying();
            Log.d(TAG, "onPause: playing: " + playing);
            if (playing) {
                mMediaPlayer.pause();
            }
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        if (mMediaPlayer != null) {
            boolean playing = mMediaPlayer.isPlaying();
            Log.d(TAG, "onResume: playing: " + playing);
            if (!playing) {
                mMediaPlayer.start();
            }
        }
    }
}