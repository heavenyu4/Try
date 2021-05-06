package com.hyw.as31try;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;

import java.io.File;
import java.io.FileFilter;

/**
 * onesdkgit目录下多个渠道 git更新的时候 生成bat批处理文件
 */
class gitbat {


    public static void main(String[] args) {
        //要更新的git目录
        String dirPath = "d:/project/onesdkgit/";

        String files[] = findDirList(dirPath);

        /*
        首先生成git status的批处理文件, 查下当前所有渠道的git状态
        执行时候, a.bat > 1.txt 可以看详尽的信息
         */
//        String operUnit = "cd %s & git status & cd ../";
        /*
        本地没有更改的情况下, 生成git更新的批处理文件, 这个文件是目录下的所有文件夹, 如果有不想更新的, 需要手动删除该行
         */
//        String operUnit = "cd %s & git pull & cd ../";

        //执行zipAll操作
        String operUnit = "cd %s & call gradlew zipAll & cd ../";

//        File file = new File("d:/project/tmp/gitstatus.bat");
//        File file = new File("d:/project/tmp/gitpull.bat");
        File file = new File("d:/project/tmp/zipAll.bat");
        FileUtils.delete(file);
        for (int i = 0; i < files.length; i++) {
            FileIOUtils.writeFileFromString(file, String.format(operUnit, files[i]) + "\n", true);
        }
        FileIOUtils.writeFileFromString(file, "\npause\n", true);

    }

    /**
     * 查找目录下的所有文件夹
     * @param dirPath
     * @return
     */
    private static String[] findDirList(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        String [] filenames = new String[files.length];
        if (files != null && files.length > 0){
            for (int i = 0; i < files.length; i++) {
                filenames[i] = files[i].getName();
//                System.out.println(files[i].getName());
            }
        }

        return filenames;

    }

}
