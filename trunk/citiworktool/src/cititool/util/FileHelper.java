/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.util;

import java.io.File;
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
        packagePath = packagePath.replaceAll("//.", "/");
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

    public static class FileCounter {

        private List<File> con = new ArrayList<File>();
        private List<File> rootlist = new ArrayList<File>();
        private int max = -1;
        private boolean flag = false;

        public FileCounter(File root) {
            rootlist.add(root);
        }

        public FileCounter(List<File> list) {
            rootlist.addAll(list);
        }

        public FileCounter(File root, int max) {
            rootlist.add(root);
            this.max = max;
        }

        public FileCounter(List<File> list, int max) {
            rootlist.addAll(list);
            this.max = max;
        }

        public boolean search() {
            con.clear();
            for (int i = 0; i < rootlist.size(); i++) {
                searchFolder(rootlist.get(i));
            }
            return flag;
        }

        private void searchFolder(File f) {
            if (f.isDirectory()) {
                File[] ls = f.listFiles();
                for (int i = 0; i < ls.length; i++) {
                    searchFolder(ls[i]);
                }
            } else {
                if (con.size() >= max && max != -1) {
                    flag = true;
                    return;
                }
                con.add(f);
            }
        }
        public List<File> getTotalFiles(){

            return con;
        }

    }

    public static void main(String args[]) {

        List<File> f=new ArrayList<File>();
        f.add(new File("C:/Documents and Settings/ZX04741/Desktop/New Folder (2)"));
        f.add(new File("C:/Documents and Settings/ZX04741/Desktop/tmp"));
        FileCounter con=new FileCounter(
                f,3);
        con.search();
        System.out.println(con.getTotalFiles().size());
    }
}
