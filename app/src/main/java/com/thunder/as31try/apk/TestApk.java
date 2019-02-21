package com.thunder.as31try.apk;

import java.io.File;

public class TestApk {

    public static void main(String[] arg) {

        /*
        创建软件目录 包括lib/arm
         */
        String parent = "D:/result";
        String apkName[] = {"ThunderKtv", "ThunderAndroidService"};

        for (int i = 0; i < apkName.length; i++) {
            File file = new File(parent, apkName[i]);
            if (!file.exists()) {
                file.mkdirs();
            }

            File fileArm = new File(file, "lib/arm");
            if (!fileArm.exists()) {
                fileArm.mkdirs();
            }

        }

    }
}
