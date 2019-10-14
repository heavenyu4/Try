package com.thunder.as31try.fileseq;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.thunder.as31try.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FileSequentialActivity extends AppCompatActivity {
    String TAG = "FileSequentialActivity";


    public void getAllAppNamesPackages(){
        PackageManager pm=getPackageManager();
        List<PackageInfo> list=pm.getInstalledPackages(0);
        int i = 0;
        for (PackageInfo packageInfo : list) {
            //获取到设备上已经安装的应用的名字,即在AndriodMainfest中的app_name。
            String appName=packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            String label = pm.getApplicationLabel(packageInfo.applicationInfo).toString();
            //获取到应用所在包的名字,即在AndriodMainfest中的package的值
            String packageName=packageInfo.packageName;
            Log.i("zyn", "应用的名字:"+label +" 应用的包名字:"+packageName);
            i++;

        }
        Log.i("zyn", "应用的总个数:"+i);
    }

    /**
     * 获取当前挂载的所有外置存储的挂载路径 不包括SD卡
     *
     * @return
     * @throws Exception
     */
    public  List<String> getExStoragePaths() throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            StorageManager storageManager = (StorageManager)  this.getApplicationContext().getSystemService(
                    Context.STORAGE_SERVICE);
            List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();
            List<String> results = new ArrayList<>();
            Method getPathMethod = StorageVolume.class.getMethod("getPath");
            if (getPathMethod != null) {
                File sdcard = Environment.getExternalStorageDirectory();
                for (StorageVolume storageVolume : storageVolumes) {
                    String path = (String) getPathMethod.invoke(storageVolume);
                    if (path != null && !new File(path).equals(sdcard)) {
                        results.add(path);
                    }
                }
            }
            return results;
        }
//        List<String> paths = new ArrayList<>();
//        String extFileStatus = Environment.getExternalStorageState();
//        File extFile = Environment.getExternalStorageDirectory();
//        //首先判断一下外置SD卡的状态，处于挂载状态才能获取的到
//        if (extFileStatus.equals(Environment.MEDIA_MOUNTED)
//                && extFile.exists() && extFile.isDirectory()
//                && extFile.canWrite()) {
//            //外置SD卡的路径
//            paths.add(extFile.getAbsolutePath());
//        }
//        // obtain executed result of command line code of 'mount', to judge
//        // whether tfCard exists by the result
//        Runtime runtime = Runtime.getRuntime();
//        Process process = runtime.exec("mount");
//        InputStream is = process.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//        String line;
//        int mountPathIndex = 1;
//        while ((line = br.readLine()) != null) {
//            // format of sdcard file system: vfat/fuse
//            // L.w("DeviceInit", line);
//            if ((!line.contains("fat") && !line.contains("fuse") && !line
//                    .contains("storage") && !line.contains("mnt"))
//                    || line.contains("secure")
//                    || line.contains("asec")
//                    || line.contains("firmware")
//                    || line.contains("shell")
//                    || line.contains("obb")
//                    || line.contains("legacy")) {
//                continue;
//            }
//            String[] parts = line.split(" ");
//            int length = parts.length;
//            if (mountPathIndex >= length) {
//                continue;
//            }
//            String mountPath = parts[mountPathIndex];
//
//            // L.w("DeviceInit", "mountPath:"+mountPath);
//
//            if (!mountPath.contains("/") || mountPath.contains("com/data")
//                    || mountPath.contains("Data")) {
//                continue;
//            }
//            File mountRoot = new File(mountPath);
//            if (!mountRoot.exists() || !mountRoot.isDirectory()
//                    || !mountRoot.canWrite()) {
//                continue;
//            }
//            boolean equalsToPrimarySD = mountPath.equals(extFile
//                    .getAbsolutePath());
//            if (equalsToPrimarySD) {
//                continue;
//            }
//            //扩展存储卡即TF卡或者SD卡路径
//            paths.add(mountPath);
//        }
//
//        return paths;
        return null;
    }

    public static void main(String[] args) {
        String url = "file:///mnt/media_rw/sda1/hami.apk";
//        Uri uri = Uri.fromFile(new File("/mnt/media_rw/sda1/hami.apk"));
        Uri uri = Uri.parse(url);
        String path = uri.getPath();
        System.out.println(path);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_sequential);

//        getAllAppNamesPackages();

//        String url = "file:///mnt/media_rw/sda1/hami.apk";
////        Uri uri = Uri.fromFile(new File("/mnt/media_rw/sda1/hami.apk"));
//        Uri uri = Uri.parse(url);
//        String path = uri.getPath();
//        System.out.println(path);

//        System.out.println(Environment.getExternalStorageDirectory());
//        System.out.println(Environment.getDataDirectory());
//        System.out.println(getCacheDir());
//        System.out.println(getExternalCacheDir());
//        System.out.println(getCodeCacheDir());
//
//        try {
//            List<String> paths = getExStoragePaths();
//            System.out.println(paths);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//
//        File file = new File("/storage/sda1/bgvideo/bg7.ts");
//        boolean delete = file.delete();
//        Log.d(TAG, "onCreate: ");

//        boolean label = isSongRespoLabel("/storage/sda1");

//        getUSBHostName(this);
//        ArrayList<String> list = executeCommand("blkid -s LABEL  /dev/block/sda1", true);
//        Intent intent = new Intent();
//        Class<? extends Intent> intentClass = intent.getClass();
//        try {
//            Field requestShutdownField = intentClass.getDeclaredField("ACTION_REQUEST_SHUTDOWN");
//            String strShutDown = (String) requestShutdownField.get(intent);
//            Log.d(TAG, "onCreate: " + strShutDown);
//
//            Field keyConfirmField = intentClass.getDeclaredField("EXTRA_KEY_CONFIRM");
//            String strKeyConfirmField = (String) keyConfirmField.get(intent);
//            Log.d(TAG, "onCreate: " + strKeyConfirmField);
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }


//        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//        Log.d(TAG, "onCreate: " + path);

//        FileSequential fileSequential = new FileSequential();
//        fileSequential.init();

//        testExec();
    }


    public void getUSBHostName(Context context) {
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Method getVolumeList = StorageManager.class.getDeclaredMethod("getVolumeList", null);
            getVolumeList.setAccessible(true);

            Class<?> classStorageVolume = Class.forName("android.os.storage.StorageVolume");
            Method getPath = classStorageVolume.getDeclaredMethod("getPath", null);
            Method getLabel = classStorageVolume.getDeclaredMethod("getUserLabel", null);
            Object[] volumes = (Object[]) getVolumeList.invoke(storageManager, null);
            Log.i(TAG, "detectUSBName: +++" + volumes);
            if (volumes != null) {
                for (int i = 0; i < volumes.length; i++) {
                    String path = (String) getPath.invoke(volumes[i], null);
                    String label = (String) getLabel.invoke(volumes[i], null);
                    Log.i(TAG, "detectUSBName Path:" + path + " Label:" + label);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static boolean isSongRespoLabel(String path){
        String TAG = "FileSequentialActivity";
        BufferedReader reader = null;
        try {
//            String command = "blkid -s LABEL  /dev/block/" + path.substring(path.lastIndexOf("/") + 1);
            String command = "ls -al /sdcard";
            Log.d(TAG, "isSongRespoLabel: " + command);
            Process process = Runtime.getRuntime().exec(command);
             reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String label = "";
            while((label = reader.readLine()) != null){
                Log.d(TAG, "isSongRespoLabel: " + label);
//                if (StringUtils.isBlank(label)){
//                    return false;
//                }else{
//                    if (label.contains("KBAR_VIDEO_DISK0")){
//                        return true;
//                    }else{
//                        return false;
//                    }
//                }
                if (!StringUtils.isEmpty(label) && label.contains("KBAR_VIDEO_DISK0")){
                    return true;
                }
            }
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally{
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<String> executeCommand(String cmd , boolean withLog) {
        String line = null;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess = null;
        try {
            localProcess = Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            return null;
            //e.printStackTrace();
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
        try {
            while ((line = in.readLine()) != null) {
                if(withLog) {
                    Log.d(TAG,"–> Line received: " + line);
                }
                fullResponse.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(withLog) {
            Log.d(TAG,"–> Full response was: " + fullResponse);
        }
        return fullResponse;
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
