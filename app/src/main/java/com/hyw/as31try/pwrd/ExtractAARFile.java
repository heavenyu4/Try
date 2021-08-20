package com.hyw.as31try.pwrd;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ZipUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

/**
 * AS分包时, 处理aar方法
 * aar提出里边的jar包, 然后资源还是在aar里
 * jar包以aar名命名, 放在out\libs_second里
 * 剔除jar包的aar放在out\libs里
 */
public class ExtractAARFile {
    private static int ind = 0;

    public static void main(String[] args) {
        //将lib工程下libs里的所有文件去提取即可
        String path = "D:\\file\\testmultidex\\libs";
        String tmp = path + "\\tmp";
        String out = path + "\\out";
        String lib_second = out + "\\libs_second";
        String libs = out + "\\libs";

        FileUtils.deleteAllInDir(tmp);
        FileUtils.deleteAllInDir(out);
        FileUtils.deleteAllInDir(lib_second);
        FileUtils.deleteAllInDir(libs);

        FileUtils.createOrExistsDir(tmp);
        FileUtils.createOrExistsDir(out);
        FileUtils.createOrExistsDir(lib_second);
        FileUtils.createOrExistsDir(libs);

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
                String dstName = aarFile.getName().substring(0, aarFile.getName().lastIndexOf("."));
                File dstFile = new File(tmp, dstName);
                try {
                    //解压aar文件
                    ZipUtils.unzipFile(aarFile,dstFile);

                    //移动classes.jar
                    File classjar = new File(dstFile, "classes.jar");
                    if (classjar.exists()){
                        File secondjar = new File(lib_second, dstName+".jar");
                        FileUtils.move(classjar, secondjar);
                    }

                    //压缩去除jar包的aar
                    File aarFileNoJar = new File(libs, aarFile.getName());
                    File[] files = dstFile.listFiles();
                    List<File> fileList = Arrays.asList(files);
                    ZipUtils.zipFiles(fileList, aarFileNoJar);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            FileUtils.deleteAllInDir(tmp);
        }
    }

}
