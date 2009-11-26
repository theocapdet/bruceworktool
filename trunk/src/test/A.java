/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zx04741
 */
public class A {

    Semaphore s = new Semaphore(1);

    public void test() {

        try {
            s.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(A.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("gggg..");
        s.release();

        for (int i = 0; i < 100; i++) {
            try {
                s.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(A.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(i);

        }
    }

    public static void main(String args[]) {
        A a = new A();
        a.test();

    }
}
