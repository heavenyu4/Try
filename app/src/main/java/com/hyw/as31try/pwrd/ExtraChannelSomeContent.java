package com.hyw.as31try.pwrd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;

/**
 * 提取各个渠道中的部分内容
 */
class ExtraChannelSomeContent {
    public static final String FILE_NAME = "build.gradle";
    static String dirPath = "D:\\project\\onesdkgitcommon\\";

    public static void main(String[] args) {

        System.out.println("find start");
        findChannel(dirPath);
        System.out.println("find end");
    }

    private static void findChannel(String path) {
//        System.out.println(path);
        File dir = new File(path);
        dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String absolutePath = pathname.getAbsolutePath();
                if (pathname.isDirectory()) {
                    findChannel(absolutePath);
                } else {
                    if (pathname.getName().equals(FILE_NAME)) {
                        extractContent(absolutePath);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private static void extractContent(String absolutePath) {
        try {
            FileReader fileReader = new FileReader(absolutePath);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                //提取build.gradle里的插件版本
                if (line.contains("com.pwrd.onesdk.plugin:OneSDKPlugin")) {
                    String substring1 = absolutePath.substring(absolutePath.indexOf(dirPath) + dirPath.length());
                    String chName = substring1.substring(0, substring1.indexOf("\\"));
                    System.out.println(chName);
                    System.out.println(line);
                    br.close();
                    return;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
