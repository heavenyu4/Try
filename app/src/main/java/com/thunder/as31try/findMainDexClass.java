package com.thunder.as31try;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ZipUtils;
import com.thunder.as31try.utils.FileUtilsThird;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.jar.JarFile;

/**
 * 根据生成在主dex的类文件maindexlist.txt 拆分jar包
 */
class findMainDexClass {


    public static void main(String[] args) {

        String maindexPath = "D:\\file\\yyb\\1\\maindexlist.txt";
        String jarPath = "D:\\file\\yyb\\1\\classes.jar";


        File jarFile = new File(jarPath);
        String dst = jarFile.getName().substring(0, jarFile.getName().lastIndexOf("."));
        String path = jarFile.getParentFile().getAbsolutePath();
        File dstFile = new File(path, dst);
        File mainDexDirFile = new File(path, dst+"_main");
        FileUtils.deleteDir(mainDexDirFile);
        FileUtils.deleteDir(dst);
        BufferedReader reader;
        try {
            //解压aar文件
//            JarFile jarFile1 = new JarFile(jarPath);
            ZipUtils.unzipFile(jarFile, dstFile);

//            FileIOUtils.readFile2String()
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(maindexPath)));
            String line;
            while ((line = reader.readLine()) != null) {
                File file = new File(dstFile.getAbsolutePath() + File.separator + line);
                if (file.exists()) {
                    System.out.println(file.getAbsolutePath());
                    FileUtils.moveFile(file, new File(mainDexDirFile, line), new FileUtils.OnReplaceListener() {
                        @Override
                        public boolean onReplace() {
                            return false;
                        }
                    });

                }
            }


            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }


    }

}
