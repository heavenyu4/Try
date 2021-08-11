package com.hyw.as31try.checksamexml;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

/**
 * 检查清单文件的所有节点是否有重复的
 * 检查四大组件和meta-data是否有重复的
 * 能输出节点name和所在行数
 */
class CheckSameNode {

    static LinkedList<Node> allNodes;
    static String AxmlPath = "D:\\project\\onesdkgit\\youxifanjuhe\\Common_yxfjh_sdk\\templatefiles\\AndroidManifest.xml";


    public static void main(String[] args) {
        allNodes = new LinkedList<>();
        extractAllNode();
    }

    private static void extractAllNode() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(AxmlPath)));

            String line = "";
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                checkSomeFeature(line, cnt);

                cnt++;
                if (line.contains("uses-permission")) {
                    continue;
                }
                if (line.contains("android:name")) {
                    // android:name="com.pwrd.onesdk.demo.newdemo.DemoActivity"
                    String[] split = line.split("android:name=\"");
                    if (split != null && split.length > 1) {
                        String name = split[1].substring(0, split[1].indexOf('"'));
//                        System.out.println(cnt + ": " + name);
                        addNode(name, cnt);
                    }
                }
            }
            printNode();

            br.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 检查一些清单文件的特性 是否存在
     * @param line
     * @param count
     */
    private static void checkSomeFeature(String line, int count) {
        if (line.isEmpty()){
            return;
        }
        if (line.contains("tools:")){
            System.out.println(count + ": " + line);
        }
        if (line.contains("style") && line.contains("Translucent")){
            System.out.println(count + ": " + line);
        }
        if (line.contains("<provider")){
            System.out.println(count + ": " + line);
        }
        if (line.contains("targetActivity")){
            System.out.println(count + ": " + line);
        }
        if (line.contains("${applicationId}")){
            System.out.println(count + ": " + line);
        }

    }

    private static void addNode(String name, int cnt) {
        boolean isDuplicated = false;
        for (int i = 0; i < allNodes.size(); i++) {
            Node node = allNodes.get(i);
            if (node.getName().equals(name)) {
                //找到重复节点, 添加行数
                isDuplicated = true;
                node.setDuplicated(true);
                node.addNewLine(cnt);
                break;
            }
        }
        if (!isDuplicated){
            Node node = new Node(name);
            node.addNewLine(cnt);
            allNodes.add(node);
        }
    }

    private static void printNode(){
        for (int i = 0; i < allNodes.size(); i++) {

            Node node = allNodes.get(i);
            if (node.isDuplicated()) {
                System.out.println(node);
            }
        }
    }


}
