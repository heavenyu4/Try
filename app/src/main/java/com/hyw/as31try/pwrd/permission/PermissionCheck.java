package com.hyw.as31try.pwrd.permission;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author: heaven
 * Description: 检查清单文件里的危险权限
 */
public class PermissionCheck {

    static String tags[] = {

            "uses-permission",

    };
    static String tagsExcluded[] = {
//      "PERFECT_DFGA"
    };
    private static String sFilePath = "D:\\project\\onesdkgit\\oppo\\Common_oppo_sdk\\templatefiles\\AndroidManifest.xml";

    static String dangrousPermissions[] = {
            "android.permission.ACCEPT_HANDOVER",
            "android.permission.ACCESS_BACKGROUND_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_MEDIA_LOCATION",
            "android.permission.ACTIVITY_RECOGNITION",
            "com.android.voicemail.permission.ADD_VOICEMAIL",
            "android.permission.ANSWER_PHONE_CALLS",
            "android.permission.BLUETOOTH_ADVERTISE",
            "android.permission.BLUETOOTH_CONNECT",
            "android.permission.BLUETOOTH_SCAN",
            "android.permission.BODY_SENSORS",
            "android.permission.CALL_PHONE",
            "android.permission.CAMERA",
            "android.permission.GET_ACCOUNTS",
            "android.permission.PROCESS_OUTGOING_CALLS",
            "android.permission.READ_CALENDAR",
            "android.permission.READ_CALL_LOG",
            "android.permission.READ_CONTACTS",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_NUMBERS",
            "android.permission.READ_PHONE_STATE",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_MMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.RECEIVE_WAP_PUSH",
            "android.permission.RECORD_AUDIO",
            "android.permission.SEND_SMS",
            "android.permission.USE_SIP",
            "android.permission.UWB_RANGING",
            "android.permission.WRITE_CALENDAR",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.WRITE_CONTACTS",
            "android.permission.WRITE_EXTERNAL_STORAGE",

    };

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
//        String fileName = path.substring(0, path.indexOf("."));
//        // 获取文件后缀
//        String endName = path.substring(path.lastIndexOf("."));
        try {
            int i = 0;
            boolean end = false;//判断文件是否读取完毕
            while (true) {
                if (end) {
                    break;
                }
//                StringBuffer sb = new StringBuffer();
//                sb.append(fileName);
//                sb.append("_data");
//                sb.append(i);
//                sb.append(endName);
//                System.out.println(sb.toString());// 新生成的文件名
                // 写入文件
//                FileOutputStream fos = new FileOutputStream(new File(
//                        sb.toString()));
//                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
//                BufferedWriter bw = new BufferedWriter(osw);
                String line = "";// 一行行读取文件
                int m = 1;
                while ((line = br.readLine()) != null) {
                    //包含某些tag 并且不包含某些tag
                    if (isIncluded(line) & isDangrous(line) & !isExcluded(line)) {
//                        bw.write(line + "\t\n");
                        System.out.println(line);
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
//                bw.close();
//                osw.close();
//                fos.close();
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

    }

    private static boolean isIncluded(String line) {
        if (line.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < tags.length; i++) {
                if (line.contains(tags[i])) {
                    return true;
                }
            }
            return false;
        }
    }

    private static boolean isDangrous(String line) {
        if (line.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < dangrousPermissions.length; i++) {
                if (line.contains(dangrousPermissions[i])) {
                    return true;
                }
            }
            return false;
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

}
