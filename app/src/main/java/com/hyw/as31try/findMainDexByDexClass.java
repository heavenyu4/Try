package com.hyw.as31try;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ZipUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * 根据生成在主dex的类文件 拆分jar包 应用宝渠道用
 */
class findMainDexByDexClass {

    static String jarDirPath;
    static String dexDirPath;
    static String jarMainPath;
    private static String TAG = "findMainDexByDexClass";

    public static void main(String[] args) {

        String jarFilePath = "D:\\file\\yyb\\9\\classes.jar";
        File jarFile = new File(jarFilePath);
        jarDirPath = jarFile.getAbsolutePath().substring(0, jarFile.getAbsolutePath().lastIndexOf("."));
        File jarDirFile = new File(jarDirPath);

        String dexFilePath = "D:\\file\\yyb\\9\\classes_dex2jar.jar";
        File dexFile = new File(dexFilePath);
        dexDirPath = dexFile.getAbsolutePath().substring(0, dexFile.getAbsolutePath().lastIndexOf("."));
        File dexDirFile = new File(dexDirPath);

        jarMainPath = jarDirPath + "_main";

        FileUtils.delete(jarDirPath);
        FileUtils.delete(dexDirPath);
        FileUtils.delete(jarMainPath);


        try {
            ZipUtils.unzipFile(jarFile, jarDirFile);
            ZipUtils.unzipFile(dexFile, dexDirFile);

            File[] files = jarDirFile.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.isFile()) {
                        findFile(pathname);
                    } else {
                        findDir(pathname);
                    }
                    return false;
                }
            });
            delEmptyDir(jarDirFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }


    }

    /**
     * 删除空的文件夹, 空的文件不会删
     * @param jarDirFile
     */
    private static void delEmptyDir(File jarDirFile) {
        jarDirFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                System.out.println(TAG + " delEmptyDir: " + pathname);
                if (pathname.isDirectory()) {
                    long dirLength = getDirLength(pathname);
                    System.out.println("delEmptyDir: " + pathname + " dirLength: " + dirLength);
                    if (dirLength <= 0L) {
                        FileUtils.delete(pathname);
                    } else {
                        delEmptyDir(pathname);
                    }
                }
                return false;
            }
        });
    }

    private static void findDir(File pathname) {
        pathname.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathnamenew) {
                if (pathnamenew.isDirectory()) {
                    findDir(pathnamenew);
                } else {
                    findFile(pathnamenew);
                }
                return false;
            }
        });

    }

    /**
     * 判断jar包里的类是否在主dex里, 如果在就拿出来
     * @param pathname
     */
    private static void findFile(File pathname) {
        String path = pathname.getAbsolutePath();
        String fileRelativePath = path.substring(path.indexOf(jarDirPath) + jarDirPath.length());
        String dexFilePath = dexDirPath + fileRelativePath;
        File dstFile = new File(jarMainPath + fileRelativePath);
        if (new File(dexFilePath).exists()) {
            System.out.println(pathname.getAbsolutePath());
            FileUtils.moveFile(pathname, dstFile, new FileUtils.OnReplaceListener() {
                @Override
                public boolean onReplace(File srcFile, File destFile) {
                    return true;
                }

            });
        }

    }


    /**
     * 获取目录长度
     *
     * @param dir 目录
     * @return 目录长度
     */
    public static long getDirLength(final File dir) {
        if (!dir.isDirectory()) return -1;
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    long length = file.length();
                    //空的文件也保留, 避免有的jar包 只是要个空文件
                    if (length == 0L){
                        length += 1;
                    }
                    len += length;
                }
            }
        }
        return len;
    }

}
