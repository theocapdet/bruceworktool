/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private ExecutorService pool = Executors.newCachedThreadPool();



    public static void main(String args[]) {
        A a = new A();


    }
}
