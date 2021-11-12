package com.hyw.as31try.checksamexml;

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
    static String AxmlPath = "D:\\project\\onesdkgit\\oppo\\Common_oppo_sdk\\templatefiles\\AndroidManifest.xml";


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
                ++cnt;
                checkSomeFeature(line, cnt);
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
        if (line.contains("<provider")) {
            System.out.println(count + ": " + line);
        }
        if (line.contains("targetActivity")) {
            System.out.println(count + ": " + line);
        }
        if (line.contains("${applicationId}")) {
            System.out.println(count + ": " + line);
        }
        if (line.contains("uses-permission") && isDangrous(line)) {
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

    private static void printNode() {
        for (int i = 0; i < allNodes.size(); i++) {

            Node node = allNodes.get(i);
            if (node.isDuplicated()) {
                System.out.println(node);
            }
        }
    }

    static String dangrousPermissions[] = {
            "android.permission.ACCEPT_HANDOVER",
            "android.permission.ACCESS_BACKGROUND_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_MEDIA_LOCATION",
            "android.permission.ACTIVITY_RECOGNITION",
            "com.android.voicemail.permission.ADD_VOICEMAIL",
            "android.permission.ANSWER_PHONE_CALLS",
            "android.permission.BLUETOOTH_ADVERTISE",
            "android.permission.BLUETOOTH_CONNECT",
            "android.permission.BLUETOOTH_SCAN",
            "android.permission.BODY_SENSORS",
            "android.permission.CALL_PHONE",
            "android.permission.CAMERA",
            "android.permission.GET_ACCOUNTS",
            "android.permission.PROCESS_OUTGOING_CALLS",
            "android.permission.READ_CALENDAR",
            "android.permission.READ_CALL_LOG",
            "android.permission.READ_CONTACTS",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_NUMBERS",
            "android.permission.READ_PHONE_STATE",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_MMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.RECEIVE_WAP_PUSH",
            "android.permission.RECORD_AUDIO",
            "android.permission.SEND_SMS",
            "android.permission.USE_SIP",
            "android.permission.UWB_RANGING",
            "android.permission.WRITE_CALENDAR",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.WRITE_CONTACTS",
            "android.permission.WRITE_EXTERNAL_STORAGE",

    };


    private static boolean isDangrous(String line) {
        if (line.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < dangrousPermissions.length; i++) {
                if (line.contains(dangrousPermissions[i])) {
                    return true;
                }
            }
            return false;
        }
    }


}
