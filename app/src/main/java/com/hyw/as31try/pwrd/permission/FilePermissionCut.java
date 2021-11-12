package com.hyw.as31try.pwrd.permission;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Author: heaven
 * Description: 从android官网的权限文档 提取危险权限列表
 * 官网地址https://developer.android.com/reference/android/Manifest.permission
 * 从官网上提取文本后, 通过下面的方法, 提取其中的危险权限
 * 规则是dangerous后面一行就是权限名称, 我们按照这个规律提取危险权限
 * Protection level: dangerous *
 * Constant Value: "android.permission.ACCEPT_HANDOVER"
 */

enum CHECK {
    START, END
};

public class FilePermissionCut {

    static String tags[] = {
//            "OneSDK",
//            "OneSDKDemo",
//            "OneSDKCore",
//            "OneSDKRequest"

//            "Protection level: dangerous",
            "Constant Value"

    };
    static String tagsExcluded[] = {
//      "PERFECT_DFGA"
    };
    private static String sFilePath = "D:\\file\\permission-all.txt";

    static CHECK state = CHECK.END;

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
                    if (state == CHECK.START & isIncluded(line) & !isExcluded(line)) {
                        bw.write(line.replace("Constant Value: ", "") + ",\t\n");
                        if (m >= maxline) {
                            break;
                        }
                        m++;
                        //2021/11/12 记录了危险权限名称后, 这个环节结束了
                        state = CHECK.END;
                    }
                    if (line.contains("Protection level: dangerous")) {
                        //2021/11/12 dangerous之后的权限行是危险权限, 开始记录
                        state = CHECK.START;
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
