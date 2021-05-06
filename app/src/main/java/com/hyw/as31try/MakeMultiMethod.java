package com.hyw.as31try;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;

import java.io.File;

/**
 * 制作多方法数的类
 * 使用场景, 当apk只有1个dex 方法数较多时, 可以拿这个方法数填充
 * 这样总的方法数就能超过65535 利用multidex就可以分dex了
 */
class MakeMultiMethod {

//    public static void main(String[] args) {
//        String className = "MethodPad";
//        final int methodCount = 30000;
//        File out = new File("d://file/"+className+".java");
//        FileUtils.deleteFile(out);
//        FileIOUtils.writeFileFromString(out, "\n public class " + className + " {\n");
//        for (int i = 0; i < methodCount; i++) {
//            String pattern = "\tprivate void method_multi_pad_%s(){}   \n";
//            String format = String.format(pattern, i);
//            FileIOUtils.writeFileFromString(out, format, true);
//        }
//        FileIOUtils.writeFileFromString(out, "\n}\n", true);
//
//    }

    public static void main(String[] args) {
        String className = "MethodPad";
        //总共需要的方法数
        final int METHODCOUNTTOTAL = 30000;
        //拆分到每个文件的方法数
        final int EVERYMETHODCOUNT = 1000;
        int fileCnt = METHODCOUNTTOTAL / EVERYMETHODCOUNT;
        String dir = "d://file/method/";
        FileUtils.delete(dir);
        for (int i = 0; i < fileCnt; i++) {

            String finalName = className + i;
            System.out.println(finalName + " start!");
            File out = new File(dir + finalName + ".java");
            FileIOUtils.writeFileFromString(out, "\n public class " + finalName + " {\n");
            for (int j = 0; j < EVERYMETHODCOUNT; j++) {
                String pattern = "\tprivate void method_multi_pad_%s(){}   \n";
                String format = String.format(pattern, j);
                FileIOUtils.writeFileFromString(out, format, true);
            }
            FileIOUtils.writeFileFromString(out, "\n}\n", true);
            System.out.println(finalName + " end!");

        }

    }

}
