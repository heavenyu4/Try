package com.thunder.as31try.checksamexml;


import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class CheckSameNodeBySax {
    static String AxmlPath = "D:\\file\\11\\AndroidManifest_hw.xml";

    public static void main(String[] args) {
        extractNode();
    }

    private static void extractNode() {
        try {

            //获取文件资源建立输入流对象
            InputStream is = new FileInputStream(AxmlPath);

            //①创建XML解析处理器
            SaxHelper ss = new SaxHelper();
            //②得到SAX解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //③创建SAX解析器
            SAXParser parser = factory.newSAXParser();
            //④将xml解析处理器分配给解析器,对文档进行解析,将事件发送给处理器
            parser.parse(is, ss);
            is.close();


            //xnlpull 这个得安卓机子上才能跑

//            // 创建一个xml解析的工厂
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            // 获得xml解析类的引用
//            XmlPullParser parser = factory.newPullParser();
//            parser.setInput(new FileInputStream(AxmlPath), "UTF-8");
//            // 获得事件的类型
//            int eventType = parser.getEventType();
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                switch (eventType) {
//                    case XmlPullParser.START_DOCUMENT:
////                    persons = new ArrayList<Person>();
//                        System.out.println("START_DOCUMENT: " + parser.getName());
//                        break;
//                    case XmlPullParser.START_TAG:
////                        if ("person".equals(parser.getName())) {
//////                        person = new Person();
//////                        // 取出属性值
//////                        int id = Integer.parseInt(parser.getAttributeValue(0));
//////                        person.setId(id);
////                        } else if ("name".equals(parser.getName())) {
////                            String name = parser.nextText();// 获取该节点的内容
//////                        person.setName(name);
////                        } else if ("age".equals(parser.getName())) {
////                            int age = Integer.parseInt(parser.nextText());
//////                        person.setAge(age);
////                        }
//                        System.out.println("START_TAG: " + parser.getName());
//                        break;
//                    case XmlPullParser.END_TAG:
////                        if ("person".equals(parser.getName())) {
//////                        persons.add(person);
//////                        person = null;
////                        }
//                        System.out.println("END_TAG: " + parser.getName());
//                        break;
//                    default:
//                        break;
//                }
//                eventType = parser.next();
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
