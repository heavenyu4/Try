package com.hyw.as31try.pwrd;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;

import java.io.File;
import java.io.FileFilter;

/**
 * AS分包时, 生成index.xml 用来合成jar包
 */
public class ExtractIndexFile {
    private static int ind = 0;

    public static void main(String[] args) {

        String path = "D:\\file\\testmultidex\\unitydemo0906";
        File dir = new File(path);
        FileFilter fileFilter = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".jar")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        File[] jarFiles = dir.listFiles(fileFilter);
        if (jarFiles != null && jarFiles.length > 0) {

            /**
             <project basedir="D:xxx" default="makeSuperJar" name="base">
             <target description="description" name="makeSuperJar">
             <jar destfile="baseabc.jar">
             */
            File indexFile = new File(path, "index.xml");
            FileUtils.delete(indexFile);
            FileIOUtils.writeFileFromString(indexFile, String.format("<project basedir=\"%s\" default=\"makeSuperJar\" name=\"base\">\n", path), true);
            FileIOUtils.writeFileFromString(indexFile, "\t<target description=\"description\" name=\"makeSuperJar\">\n", true);
            FileIOUtils.writeFileFromString(indexFile, "\t\t<jar destfile=\"baseabc.jar\">\n", true);

//            System.out.println(String.format("<project basedir=\"%s\" default=\"makeSuperJar\" name=\"base\">", path));
//            System.out.println("\t<target description=\"description\" name=\"makeSuperJar\">");
//            System.out.println("\t\t<jar destfile=\"baseabc.jar\">");

            for (int i = 0; i < jarFiles.length; i++) {
                File jarFile = jarFiles[i];
//                System.out.println(String.format("\t\t\t<zipfileset src=\"%s\"/>", jarFile.getName()));
                FileIOUtils.writeFileFromString(indexFile, String.format("\t\t\t<zipfileset src=\"%s\"/>\n", jarFile.getName()), true);
            }

            /*
            		</jar>
                </target>
            </project>
             */
            FileIOUtils.writeFileFromString(indexFile, "\t\t</jar>\n", true);
            FileIOUtils.writeFileFromString(indexFile, "\t</target>\n", true);
            FileIOUtils.writeFileFromString(indexFile, "</project>\n", true);
//            System.out.println("\t\t</jar>");
//            System.out.println("\t</target>");
//            System.out.println("</project>");
        }
    }

}
