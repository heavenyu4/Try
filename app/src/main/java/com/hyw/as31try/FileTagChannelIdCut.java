package com.hyw.as31try;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * Author: heaven
 * Time: 2019/4/15  20:13
 * Description: 检查调用onesdkGetChannelId和
 * onesdkGetSubchannelId的次数
 * 请注意 log是要从游戏启动开始抓的, 到进服以后
 */
public class FileTagChannelIdCut {

    static String tags[] = {
            "onesdkGetChannelId",
            "onesdkGetSubchannelId"
    };
    static String tagsExcluded[]={
//      "PERFECT_DFGA"
    };
    private static String sFilePath = "D:\\log\\20210630_162308.log";

    private static int tagsCount[] = new int[tags.length];
    /**
     *
     * @Description 文件分割
     * @param maxline 最大行数（即每个文件中存储的行数）
     * @throws IOException
     */
    public static void splitFileDemo(String path,int maxline) throws IOException {
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
                while((line = br.readLine())!=null ){
                    //包含某些tag 并且不包含某些tag
                    if (isIncluded(line) & !isExcluded(line) ) {
                        bw.write(line + "\t\n");
                        if (m >= maxline) {
                            break;
                        }
                        m++;
                    }
                }
                if(m<maxline) {
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

    private static boolean isIncluded(String line){
        if (line.isEmpty()){
            return false;
        }else{
            for (int i = 0; i < tags.length; i++) {
                if (line.contains(tags[i]) && line.contains("PERFECT_DFGA")
                        //DfgaSDK save event success表示本地化存储成功
                        && line.contains("DfgaSDK save event success")) {
                    tagsCount[i]++;
                    return true;
                }
            }
            return false;
        }
    }
    private static boolean isExcluded(String line){
        if (line.isEmpty()){
            return false;
        }else{
            for (int i = 0; i < tagsExcluded.length; i++) {
                if (line.contains(tagsExcluded[i])){
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
        for (int i = 0; i < tags.length; i++) {
            System.out.println(tags[i] + " : " + tagsCount[i] + "次");
        }
    }

}
