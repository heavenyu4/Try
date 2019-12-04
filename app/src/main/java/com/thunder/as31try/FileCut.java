package com.thunder.as31try;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Author: heaven
 * Time: 2019/4/15  20:13
 * Description:
 */
public class FileCut {

    /**
     *
     * @Description 文件分割
     * @param src 分割文件路径
     * @param maxline 最大行数（即每个文件中存储的行数）
     * @throws IOException
     */
    public static void splitFileDemo(String path,int maxline) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        // 获取文件名
        String fileName = path.substring(0, path.indexOf("."));
        // 获取文件后缀
        String endName = path.substring(path.lastIndexOf("."));
        try {
            int i = 0;
            boolean end = false;//判断文件是否读取完毕
            while (true) {
                if (end)
                    break;
                StringBuffer sb = new StringBuffer();
                sb.append(fileName);
                sb.append("_data");
                sb.append(i);
                sb.append(endName);
                System.out.println(sb.toString());// 新生成的文件名
                // 写入文件
                FileOutputStream fos = new FileOutputStream(new File(
                        sb.toString()));
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);
                String line = "";// 一行行读取文件
                int m = 1;
                while((line = br.readLine())!=null ){
                    bw.write(line+"\t\n");
                    if(m>=maxline){
                        break;
                    }
                    m++;
                }
                if(m<maxline)
                    end = true;
                i++;
                //关闭写入流
                bw.close();
                osw.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭输入流
            br.close();
            isr.close();
            fis.close();
        }
        System.out.println("--- 文件分割完成 ---");

    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
       splitFileDemo(
                "D:\\file\\anr\\serial-com20_B_20191202_000000.log",
                30000);
    }

}
