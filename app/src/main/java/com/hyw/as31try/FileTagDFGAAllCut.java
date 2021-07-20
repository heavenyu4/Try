package com.hyw.as31try;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.blankj.utilcode.util.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * Author: heaven
 * Time: 2019/4/15  20:13
 * Description: 测试统计事件的调用
 * 请注意, log从游戏启动到进入角色后
 */
public class FileTagDFGAAllCut {

    private static String sFilePath = "D:\\log\\20210720_172332.log";
    static String tags[] = {
            "roleLoginSDK",
            "roleLoginErrorSDK",
            "roleLogoutSDK",
            "Create_role",
            "roleLevelUpTo",
            "gameResReqBegin",    //资源版本核对开始  检查资源
            "gameResReqSuccess",
            "gameResReqError",
            "gameUpdateAssetBegin",  //完美统计资源下载开始 热更时用
            "gameUpdateAssetSuccess",
            "gameUpdateAssetError",
            "gameResDecBegin",     //完美统计解压缩开始 热更资源解压缩
            "gameResDecSuccess",
            "gameResDecError",
            "gameGetServerListBegin",  //完美统计服务器列表开始
            "gameGetServerListSuccess",
            "gameGetServerListError",

//            "onAttachedToWindow",
//            "onDetachedFromWindow",
//            "onCreate",
//            "onRestart",
//            "onStart",
//            "onResume",
//            "onPause",
//            "onStop",
//            "onDestroy",
//            "onSaveInstanceState",
//            "onRestoreInstanceState",
//            "onConfigurationChanged",
//            "onActivityResult",
//            "onNewIntent",
//            "onKeyDown",
//            "onRequestPermissionsResult",
//            "dispatchTouchEvent",
//            "onRelease",

            "logInterfaceSDK",

    };
    static String tagsExcluded[] = {
            "startSDK",
            "startGame",
            "loginBegin",
            "loginSDK",
            "New_Login",
            "logoutSDK",
            "sdkNetworkError",

            "startPayment",
            "finishPayment",
            "cancelPayment",
            "logVersionSDK",
    };
    private static int[] result = new int[tags.length];
//    private static ArrayList<Event> allEvent = new ArrayList<>(64);
    private static TreeMap<String, Integer> allevent = new TreeMap<String, Integer>();

    /**
     * @param maxline 最大行数（即每个文件中存储的行数）
     * @throws IOException
     * @Description 文件分割
     */
    public static void splitFileDemo(String path, int maxline) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        // 获取文件名
        String fileName = path.substring(0, path.indexOf("."));
        // 获取文件后缀
        String endName = path.substring(path.lastIndexOf("."));
        try {
            int i = 0;
            boolean end = false;//判断文件是否读取完毕
            while (true) {
                if (end) {
                    break;
                }
                StringBuffer sb = new StringBuffer();
                sb.append(fileName);
                sb.append("_data");
                sb.append(i);
                sb.append(endName);
                System.out.println(sb.toString());// 新生成的文件名
                // 写入文件
                FileOutputStream fos = new FileOutputStream(new File(
                        sb.toString()));
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);
                String line = "";// 一行行读取文件
                int m = 1;
                while ((line = br.readLine()) != null) {
                    //包含某些tag 并且不包含某些tag
                    if (isIncluded(line) & !isExcluded(line)) {
                        String cutline = cutline(line);
                        bw.write(cutline + "\t\n");
                        if (m >= maxline) {
                            break;
                        }
                        m++;
                    }
                }
                if (m < maxline) {
                    end = true;
                }
                i++;
                //关闭写入流
                bw.close();
                osw.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭输入流
            br.close();
            isr.close();
            fis.close();
        }
        System.out.println("--- 文件分割完成 ---");

//        for (int i = 0; i < tags.length; i++) {
//            System.out.println(tags[i] + " : " + result[i]);
//        }
        printEvent();

    }

    private static String cutline(String line) {
        if (StringUtils.isEmpty(line)) {
            return line;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(line.substring(0, line.indexOf("[(")));
        builder.append("\t");
        if (line.contains("logInterfaceSDK")) {
            builder.append(line.substring(line.indexOf("hint"), line.indexOf(", location")));
        } else {
            builder.append(line.substring(line.indexOf("eventKey"), line.indexOf(", location")));
        }
        return builder.toString();
    }

    private static boolean isIncluded(String line) {
        if (line.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < tags.length; i++) {
                if (line.contains(tags[i]) && line.contains("PERFECT_DFGA")
                        //DfgaSDK save event success表示本地化存储成功
                        && line.contains("DfgaSDK save event success")) {
//                    result[i]++;
                    saveEvent(tags[i], line);
                    return true;
                }
            }
            return false;
        }
    }

    private static void saveEvent(String tag, String line) {
        String tagMod = tag;
        if ("logInterfaceSDK".equals(tag)) {
            int beginIndex = line.indexOf("\"name\":\"") + "\"name\":\"".length();
            int endIndex = line.indexOf("\",\"uid\"");
            if (beginIndex == -1 || endIndex == -1){
                /*
                07-06 14:59:55.342 29825 29918 D PERFECT_DFGA: [(null:94)#B]DfgaSDK save event success----------Event{id=252, appId='1256', battery='NULL', channel='9', hint='{"logInterfaceSDK2":"30","miitErrorCode":"NULL"}', eventKey='delkey', location='NULL', netw='null', sessionId='74955ecbe80b82a6fa8aa65318e3bdf9', taskId='2', taskVersion='4.5.5', timestamp='1625554795160', token='9064c664c0db83b545ed88435a700446', type='1', versionCode='1.2.53.32453_54', oneAppId='1256',
                writeEvent='a1c3b872c3eca1546e98fbf2185a2840', platform='9', mediaId='1', priority='2', sourceType='0', cid='NULL', eid='NULL', ext='null'}
                这种命名就不要了
                 */
                return;
            }
            tagMod = line.substring(beginIndex, endIndex);
        }
        if (allevent.containsKey(tagMod)) {
            Integer cnt = allevent.get(tagMod);
            allevent.put(tagMod, ++cnt);
        } else {
            allevent.put(tagMod, 1);
        }
    }

    private static void printEvent(){
        Set<String> tags = allevent.keySet();
        for (String tag : tags) {
            System.out.println(tag + " : " + allevent.get(tag));

        }
    }

    private static boolean isExcluded(String line) {
        if (line.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < tagsExcluded.length; i++) {
                if (line.contains(tagsExcluded[i])) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        splitFileDemo(
                sFilePath,
                100000);
    }


    private class Event {
        String name = "";
        int count = 0;

        public Event() {
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "name='" + name + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
