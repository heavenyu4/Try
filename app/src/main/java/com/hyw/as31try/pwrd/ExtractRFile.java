package com.hyw.as31try.pwrd;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ZipUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 提取rules.xml下的R文件的id
 */
public class ExtractRFile {
    private static int ind = 0;

    public static void main(String[] args) {
        //将lib工程下libs里的所有文件去提取即可
        String path = "D:\\project\\onesdkgit\\hw\\hwSDK_Android_LibProject\\libs";
        File dir = new File(path);
        FileFilter fileFilter = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith("aar")){
                    return true;
                }else {
                    return false;
                }
            }
        };
        File[] aarFiles = dir.listFiles(fileFilter);
        if (aarFiles!=null && aarFiles.length > 0){
            for (int i = 0; i < aarFiles.length; i++) {
                File aarFile = aarFiles[i];
                String dst = aarFile.getName().substring(0, aarFile.getName().lastIndexOf("."));
                File dstFile = new File(path, dst);
                try {
                    //解压aar文件
                    ZipUtils.unzipFile(aarFile,dstFile);
                    File RFile = new File(dstFile, "R.txt");
                    File AndroidManiFile = new File(dstFile, "AndroidManifest.xml");
                    String content = FileIOUtils.readFile2String(RFile);

                    //如果R.txt内容不为空的话  文件长度大于1行的话(避免只有一行app_name的情况) 提取包名
                    if (!StringUtils.isEmpty(content)) {
                        String[] split = content.split("\n");
                        if (split != null && split.length > 1) {
                            getPackageName(AndroidManiFile);
                        }
                    }
                    boolean delete = deleteDir(dstFile);
//                    System.out.println("delete " + dst + " result: " + delete);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void getPackageName(File file){
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            if ((line = reader.readLine()) != null) {
                while ((line = reader.readLine()) != null) {
//                    sb.append(LINE_SEP).append(line);
                    if(line.contains("package")){
//                        System.out.println(line);
                        String[] split = line.split("\"");
                        if (split!= null && split.length > 0){
                            ++ind;
//                            attr name="package1" value="android.support.graphics.drawable.animated"
                            System.out.println("<attr name=\"package"+ind+"\" value=\""+split[1]+"\" />");
                        }
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }
}
