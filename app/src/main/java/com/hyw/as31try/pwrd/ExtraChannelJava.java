package com.hyw.as31try.pwrd;

import com.blankj.utilcode.util.FileUtils;

import java.io.File;
import java.io.FileFilter;

/**
 * 提取onesdkchannel.java文件
 * unity ue4 统一接口 经常要看各个渠道的使用情况
 */
class ExtraChannelJava {
    public static final String ONE_SDKCHANNEL_JAVA = "OneSDKChannel.java";
    static String dirPath = "D:\\project\\onesdkgitcommon";
    static String tmp = "D:\\file\\onesdkchanneljava";

    public static void main(String[] args) {

        FileUtils.deleteAllInDir(tmp);
        FileUtils.createOrExistsDir(tmp);
        findChannel(dirPath);
        System.out.println("find end");
    }

    private static void findChannel(String path) {
        System.out.println(path);
        File dir = new File(path);
        dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String absolutePath = pathname.getAbsolutePath();
                if (pathname.isDirectory()) {
                    findChannel(absolutePath);
                } else {
                    if (pathname.getName().equals(ONE_SDKCHANNEL_JAVA)) {
                        String substring1 = absolutePath.substring(absolutePath.indexOf(dirPath) + dirPath.length());
                        String chName = substring1.substring(0, substring1.indexOf("\\"));
                        FileUtils.copy(absolutePath, tmp + File.separator + chName + "_"+ONE_SDKCHANNEL_JAVA);
                        return true;
                    }
                }
                return false;
            }
        });
    }

}
