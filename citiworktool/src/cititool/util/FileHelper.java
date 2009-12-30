/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        private int lineNum=0;
        private boolean countLine=false;

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

        public void setCountLine(boolean t){
            countLine=t;
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
                if(countLine){
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                        while (br.readLine() != null) {
                            lineNum++;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }  finally {
                        try {
                            br.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                con.add(f);
            }
        }
        public List<File> getTotalFiles(){

            return con;
        }

        public int getLineNumber(){

            return  lineNum;
        }

    }

    public static void main(String args[]) {


        FileCounter con=new FileCounter(new File("C:/brucexx/nbworkspace/citiworktool/src/cititool"));
         con.setCountLine(true);
        con.search();
        System.out.println(con.getTotalFiles().size()+" || "+con.getLineNumber());
    }
}
