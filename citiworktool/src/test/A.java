/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

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
        JFrame f = new JFrame();
        f.setSize(200, 200);
        Dimension d=new Dimension(100, 200);
        JLabel p = new JLabel();
        p.setLayout(new GridLayout(1, 2));
        JLabel l1 = new JLabel("haha");
        JLabel l2 = new JLabel("haha1");
        p.setBorder(new LineBorder(Color.red));
        l1.setBorder(new LineBorder(Color.blue));
        l2.setBorder(new LineBorder(Color.yellow));
        p.add(l1);
        p.add(l2);
        f.add(p);
        f.setVisible(true);
    }
}
