package com.hyw.as31try.xml;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;

class xmlTest {

    public static void main(String[] args) {

//    }
//    public static HashMap<String, Object> stringToXmlByDom4j(String str) {

        HashMap<String, Object> result = new HashMap<String, Object>();

        try {
            SAXReader saxReader = new SAXReader();
            File file = new File("D:\\file\\1.5.8.androidmanifest.xml");
            org.dom4j.Document docDom4j = saxReader.read(file);
            org.dom4j.Element root = docDom4j.getRootElement();
            List<Attribute> rooAttrList = root.attributes();
            for (Attribute rootAttr : rooAttrList) {
//                System.out.println(rootAttr.getName() + ": " + rootAttr.getValue());
                result.put(rootAttr.getName(), rootAttr.getValue());
            }

            List<Element> childElements = root.elements();

            for (org.dom4j.Element e1 : childElements) {
//        System.out.println("第一层：" + e1.getName() + ": " + e1.getText());
                result.put("fir " + e1.getName(), e1.getText());
            }

            for (org.dom4j.Element child : childElements) {
                //未知属性名情况下
                List<Attribute> attributeList = child.attributes();
                for (Attribute attr : attributeList) {
//                System.out.println("第二层：" + attr.getName() + ": " + attr.getValue());
                    result.put("sec" + attr.getName(), attr.getValue());
                }

                List<Element> elementList = child.elements();
                for (org.dom4j.Element ele : elementList) {

                    if (!ele.getName().equals("activity")){
                        continue;
                    }
                    System.out.println("第二层：" + ele.getName() + ": " + ele.getText());
                    result.put("sec" + ele.getName(), ele.getText());

                    List<Attribute> kidAttr = ele.attributes();
                    for (Attribute kidattr : kidAttr) {
                    System.out.println("第三层：" + kidattr.getName() + ": " + kidattr.getValue());
                        result.put("thi" + kidattr.getName(), kidattr.getValue());
                    }

                    List<org.dom4j.Element> lidList = ele.elements();
                    int size = lidList.size();
                    if (size > 0) {
                        for (org.dom4j.Element e2 : lidList) {
                        System.out.println("第三层：" + e2.getName() + ": " + e2.getText());
                            result.put("thi" + e2.getName(), e2.getText());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("");
//        return result;
    }
}