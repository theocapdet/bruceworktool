/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zx04741
 */
public class FileHelper {

    private String suffix;
    private String prefix;
    private String keyword;
    private List<File> searchFiles;
    /**
     *  SUFFIX_MODE=1;
    PREFIX_MODE=2;
    KEYWORD_MODE=4;
     */
    private static int MODE = 0;

    public FileHelper() {
        searchFiles = new ArrayList<File>();
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        if (MODE < 8) {
            MODE += 4;
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        if (MODE < 8) {
            MODE += 2;
        }
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
        if (MODE < 8) {
            MODE += 1;
        }
    }

    public List<File> searchFile(File rootFile) {

        search(rootFile);

        return searchFiles;
    }

    private void search(File f) {

        if (f.isDirectory()) {
            File[] list = f.listFiles();
            for (int i = 0; i < list.length; i++) {
                search(list[i]);
            }
        } else if (f.isFile() && f.exists()) {
            String fn = f.getName();
            boolean add = true;
            if (MODE >> 1 == 0) {
                add = add && fn.endsWith(suffix);
            }
            if ((MODE = (MODE >> 1)) != 0) {
                add = add && fn.startsWith(prefix);
            }
            if ((MODE = (MODE >> 1)) != 0) {
                add = add && fn.contains(keyword);
            }
            if (add) {
                searchFiles.add(f);
            }
        }
    }

    public static String getClassFilePath(Class clz) {
        String resource = clz.getResource("/").toString();
        resource = resource.substring(6);
        String packagePath = clz.getPackage().getName();
        packagePath = packagePath.replaceAll("\\.", "/");
        return resource + packagePath + "/";
    }

    public static String getClassResource(Class clz) {

        return getClassFilePath(clz) + "resources/";
    }

    public static File createFile(String path) throws IOException {

        File f = new File(path);
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            f.createNewFile();

        }
        return f;
    }





    public static void main(String args[]) {

        FileHelper f = new FileHelper();
        System.out.println(getClassFilePath(f.getClass()));
    }
}