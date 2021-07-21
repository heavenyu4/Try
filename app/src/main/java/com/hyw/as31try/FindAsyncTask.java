package com.hyw.as31try;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

/***
 * 替换游戏包里的AsyncTask类
 *
 *
 * 替换asynctask添加THREAD_POOL_EXECUTOR的替换, 但是还未验证,
 * 添加拷贝one的task类, 执行前记得更新task相关类,
 */
class FindAsyncTask {

    private static String dirPath = "D:\\file\\testAsynctask\\testchannel\\06\\2moddex\\classes";
    private static HashSet<File> execFile = new HashSet<>(128);
    private static HashSet<File> superFile = new HashSet<>(128);

    public static void main(String[] args) {

        find(dirPath);

        //拷贝task到目标主dex
        String smailPath = dirPath + "\\smali\\com\\pwrd\\onesdk";
        File smaliDir = new File(smailPath);
        if (!smaliDir.exists()) {
            //如果smali目录不存在的话, 就是直接对的dex根目录操作的, task直接拷贝进来就行
            smailPath = dirPath + "\\com\\pwrd\\onesdk";
        }
        boolean copy = FileUtils.copy("D:\\file\\testAsynctask\\onetask", smailPath);
        System.out.println("copy onetask result: " + copy + " dstPath: " + smailPath);

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
        if (pathname.getName().contains("OneAsyncTask.smali")) {
            return;
        }
        if (pathname.getName().contains("OneTaskExecutor.smali")) {
            return;
        }
        if (pathname.getName().contains("OneTaskExecutor$1.smali")) {
            return;
        }
        if (pathname.getName().contains("OneTaskExecutor$2.smali")) {
            return;
        }
        //只改B站包
//        if (!pathname.getAbsolutePath().contains("com\\bsgamesdk\\")){
//            return;
//        }
        //只改腾讯包
//        if (!pathname.getAbsolutePath().contains("com\\tencent\\")) {
//            return;
//        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathname));
            String line = "";
            boolean isNeedRp = false;
            boolean isSubClass = false;
            while ((line = br.readLine()) != null) {
                if (line.contains("executeOnExecutor")) {
                    System.out.println("======warning: pathname: " + pathname.getAbsolutePath() + " \t" + line);
                }

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
                if (line.contains("Landroid/os/AsyncTask;->THREAD_POOL_EXECUTOR:Ljava/util/concurrent/Executor;")) {
                    isNeedRp = true;
                    break;
                }
            }

            br.close();
            if (isNeedRp) {
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
                    String result = replaceThreadPoolConst(line);
                    if (!StringUtils.isEmpty(result)) {
                        replace = result;
                    } else if (line.contains(".super Landroid/os/AsyncTask;")
                            || line.contains("\"Landroid/os/AsyncTask\",")
                            || line.contains("invoke-direct {p0}, Landroid/os/AsyncTask;-><init>()V")
                    ) {
                        replace = line.replace("Landroid/os/AsyncTask", "Lcom/pwrd/onesdk/task/OneAsyncTask");
                    } else if (line.contains("->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;")
                            && !line.contains("Landroid/os/AsyncTask;->executeNew([Ljava/lang/Object;)Landroid/os/AsyncTask;")
                    ) {
                        replace = line.replace("->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;", "->executeNew([Ljava/lang/Object;)Landroid/os/AsyncTask;");
                    } else {
                        replace = line;
                    }
                    if (!replace.equals(line)) {
                        System.out.println(line + " -> " + replace);
                    }
                    bw.write(replace);
                    bw.newLine();
                } else {
                    String replace = line;
                    String result = replaceThreadPoolConst(line);
                    if (!StringUtils.isEmpty(result)) {
                        replace = result;
                        System.out.println(line + " -> " + replace);
                    }
                    bw.write(replace);
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

    private static String replaceThreadPoolConst(String line) {
        /*
        sget-object v0, Landroid/os/AsyncTask;->THREAD_POOL_EXECUTOR:Ljava/util/concurrent/Executor;

        -->

            new-instance v0, Lcom/pwrd/onesdk/task/OneTaskExecutor;

            invoke-direct {v0}, Lcom/pwrd/onesdk/task/OneTaskExecutor;-><init>()V


         */

        //1.找掉变量名
        if (line.contains("Landroid/os/AsyncTask;->THREAD_POOL_EXECUTOR:Ljava/util/concurrent/Executor;")) {
            int beginIndex = line.indexOf("sget-object ") + "sget-object ".length();
            int endIndex = line.indexOf(", Landroid/os/AsyncTask;");
            if (beginIndex == -1 || endIndex == -1) {
                System.out.println(line + " index error, begin: " + beginIndex + " endindex: " + endIndex);
                return null;
            }
            String var = line.substring(beginIndex, endIndex);
            String format = "    new-instance %s, Lcom/pwrd/onesdk/task/OneTaskExecutor;\n\n"
                    + "    invoke-direct {%s}, Lcom/pwrd/onesdk/task/OneTaskExecutor;-><init>()V";
            String result = String.format(format, var, var);
            return result;
        }
        return null;
    }
}
