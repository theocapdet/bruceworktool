/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author zx04741
 */
public class FileClearReadOnly {

    public void change() {
        //C:\\Documents and Settings\\ZX04741\\Desktop\\tmp  C:\\3.0workspace
        File root = new File("C:\\3.0workspace");
        go(root);
    }

    private void go(final File f) {

        if (f.isFile()) {
            if (!f.canWrite()) {

                try {
                    System.out.println(f.getPath());
                    Runtime.getRuntime().exec("cmd.exe /c attrib " + f.getName() + " -r", null, f.getParentFile());
                } catch (IOException ex) {
                    System.out.println("error");
                }
            }
        } else if (f.isDirectory()) {

            File[] list = f.listFiles();
            for (int i = 0; i < list.length; i++) {
                go(list[i]);
            }
        }
    }

    public static void main(String args[]) {
        FileClearReadOnly a = new FileClearReadOnly();
        a.change();
    }
}
