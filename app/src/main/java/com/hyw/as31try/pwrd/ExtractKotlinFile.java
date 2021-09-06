package com.hyw.as31try.pwrd;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ZipUtils;

import java.io.File;
import java.io.FileFilter;

/**
 * 提取kotlin jar包下 META-INF下的除了MF文件的其他文件
 * 这些文件放在root目录下, 不放的话, 运行会报错
 */
public class ExtractKotlinFile {
    private static int ind = 0;

    public static void main(String[] args) {
        //将lib工程下libs里的所有文件去提取即可
        String path = "D:\\project\\onesdkgit\\douyin\\douyinSDK_Android_LibProject\\libs";
        String rootPath = "D:\\project\\tmp\\root";
        File dir = new File(path);
        FileFilter fileFilter = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().startsWith("kotlin")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        File[] jarFiles = dir.listFiles(fileFilter);
        if (jarFiles != null && jarFiles.length > 0) {
            FileUtils.deleteAllInDir(rootPath);
            for (int i = 0; i < jarFiles.length; i++) {
                File aarFile = jarFiles[i];
                String dst = aarFile.getName().substring(0, aarFile.getName().lastIndexOf("."));
                File dstFile = new File(path, dst);
                try {
                    //解压jar文件
                    FileUtils.deleteAllInDir(dstFile);
                    ZipUtils.unzipFile(aarFile, dstFile);
                    File metaDir = new File(dstFile, "META-INF");
                    if (metaDir.isDirectory()) {
                        boolean copy = FileUtils.copy(metaDir.getAbsolutePath(), rootPath + "\\META-INF", new FileUtils.OnReplaceListener() {
                            @Override
                            public boolean onReplace(File srcFile, File destFile) {
                                //签名文件就替换吧 没事
                                System.out.println("replace src: " + srcFile.getAbsolutePath());
                                System.out.println("replace dst: " + destFile.getAbsolutePath());
                                return true;
                            }
                        });
                        System.out.println("copy " + metaDir.getAbsolutePath() + " result: " + copy);
                    }


                    boolean delete = FileUtils.delete(dstFile);
//                    System.out.println("delete " + dst + " result: " + delete);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //删除签名文件, 不要这个
            FileUtils.delete(rootPath + "\\META-INF\\MANIFEST.MF");
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
