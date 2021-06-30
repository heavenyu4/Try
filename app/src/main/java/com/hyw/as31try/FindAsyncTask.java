package com.hyw.as31try;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.hyw.as31try.utils.FileUtilsThird;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.chrono.IsoChronology;
import java.util.HashSet;
import java.util.Iterator;

class FindAsyncTask {

    private static String dirPath = "D:\\file\\testAsynctask\\zhanshen\\04\\bilibili_svn_133783_0.6.8_10_signed_signed";
    private static HashSet<File> execFile = new HashSet<>(128);
    private static HashSet<File> superFile = new HashSet<>(128);

    public static void main(String[] args) {

        find(dirPath);
    }

    private static void find(String dirPath) {

        File dirfile = new File(dirPath);
        dirfile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    find(pathname.getAbsolutePath());
                    return true;
                } else {
                    if (pathname.getPath().endsWith(".smali")) {
                        findContent(pathname);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });

    }

    private static void findContent(File pathname) {
        if (pathname.getAbsolutePath().contains("android\\support")) {
            return;
        }
        if (pathname.getAbsolutePath().contains("androidx\\")) {
            return;
        }
        if (pathname.getName().contains("CustomAsyncTask.smali")) {
            return;
        }
        if (pathname.getName().contains("TaskExecutor.smali")) {
            return;
        }
        if (pathname.getName().contains("TaskExecutor$1.smali")) {
            return;
        }
        if (pathname.getName().contains("TaskExecutor$2.smali")) {
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathname));
            String line = "";
            boolean isNeedRp = false;
            boolean isSubClass = false;
            while ((line = br.readLine()) != null) {
                if (line.contains("->execute([Ljava/lang/Object;)Landroid/os/AsyncTask")) {
//                    System.out.println(pathname.getAbsolutePath());
//                    System.out.println(line);
                    isNeedRp = true;
                    break;
//                    execFile.add(pathname);
                }
                if (line.contains(".super Landroid/os/AsyncTask")) {
//                    System.out.println(pathname.getAbsolutePath());
//                    System.out.println(line);
                    isNeedRp = true;
                    isSubClass = true;
//                    superFile.add(pathname);
                    break;
                }
            }

            br.close();
            if (isNeedRp) {
//                replaceFile(pathname, "->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;",
//                        "->executeNew([Ljava/lang/Object;)Landroid/os/AsyncTask;");
//                replaceFile(pathname, "Landroid/os/AsyncTask",
//                        "Lcom/pwrd/onesdk/CustomAsyncTask");
                replaceFile(pathname, isSubClass);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void replaceFile(File file, boolean isSubClass) {
        System.out.println();
        System.out.println("modify " + file.getAbsolutePath());

        try {
            String bakPath = "D:\\file\\testAsynctask\\smali-two\\tmp";
            File tmpFile = new File(bakPath, file.getName());
//            System.out.println("bakpath: " + tmpFile.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile, false));
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.contains("->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;")
                        && !line.contains("Landroid/os/AsyncTask;->executeNew([Ljava/lang/Object;)Landroid/os/AsyncTask;")
                ) {
                    String replace = line.replace("->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;", "->executeNew([Ljava/lang/Object;)Landroid/os/AsyncTask;");
                    System.out.println(line + " -> " + replace);
                    bw.write(replace);
                    bw.newLine();
                } else if (isSubClass && line.contains("Landroid/os/AsyncTask")) {
                    //避免executeOnExecutor情况, 这种已经在不同线程的操作, 我们就不再处理了
//                    if (line.contains("Landroid/os/AsyncTask;->executeOnExecutor(Ljava/util/concurrent/Executor;[Ljava/lang/Object;)Landroid/os/AsyncTask")) {
//                    if (line.contains("->executeOnExecutor(Ljava/util/concurrent/Executor;[Ljava/lang/Object;)Landroid/os/AsyncTask")) {
//                        continue;
//                    }
//                    //避免调用AsyncTask的THREAD_POOL_EXECUTOR变量的情况
//                    if (line.contains("Landroid/os/AsyncTask;->THREAD_POOL_EXECUTOR")) {
//                        continue;
//                    }
//                    //避免方法里有AsyncTask参数的情况 e.g.  public static <T> void m16317a(AsyncTask asyncTask, T... tArr) {
//                    if (line.contains(".method") && line.contains("Landroid/os/AsyncTask")){
//                        continue;
//                    }
                    String replace = "";
                    if (line.contains(".super Landroid/os/AsyncTask;")
                            || line.contains("\"Landroid/os/AsyncTask\",")
                            || line.contains("invoke-direct {p0}, Landroid/os/AsyncTask;-><init>()V")
                    ) {
                        replace = line.replace("Landroid/os/AsyncTask", "Lcom/pwrd/onesdk/CustomAsyncTask");
                        System.out.println(line + " -> " + replace);
                    } else if (line.contains("->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;")
                            && !line.contains("Landroid/os/AsyncTask;->executeNew([Ljava/lang/Object;)Landroid/os/AsyncTask;")
                    ) {
                        replace = line.replace("->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;", "->executeNew([Ljava/lang/Object;)Landroid/os/AsyncTask;");
                        System.out.println(line + " -> " + replace);
                    } else {
                        replace = line;
                    }
                    bw.write(replace);
                    bw.newLine();
                } else {
                    bw.write(line);
                    bw.newLine();
                }

            }
            br.close();
            bw.flush();
            bw.close();
            FileUtils.move(tmpFile, file, new FileUtils.OnReplaceListener() {

                @Override
                public boolean onReplace(File srcFile, File destFile) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
