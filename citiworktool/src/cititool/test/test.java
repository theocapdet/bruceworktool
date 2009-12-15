/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *
 * @author zx04741
 */
public class test {

    private static Semaphore sem = new Semaphore(1);

    public static void test1() {

        MyThread t = new MyThread();
        t.start();

        MyThread t1 = new MyThread();
        t1.start();


    }

    static class MyThread extends Thread {

        private static int GO = 0;

        public void run() {
            try {
                sem.acquire();

                System.out.println(Thread.currentThread().getId() + ",val:" + (++GO));
                Thread.sleep(3000);


                sem.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {
        try {
            String str = "1241-33-11";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(str);
            System.out.println(str);
        } catch (ParseException ex) {
           System.out.println(ex.getMessage());
        }

    }


}



class MyLabel extends JLabel {

    private String filename;

    public MyLabel(String filename) {
        this.filename = filename;
        this.setPreferredSize(new Dimension(300, 300));
        this.setBorder(new javax.swing.border.LineBorder(Color.RED, 2, true));
    }

    public void paint(Graphics g) {
    }

    {
        try {
            System.out.println(filename);
            BufferedImage img = ImageIO.read(new FileInputStream(filename));
            Graphics g = img.getGraphics();
            g.drawImage(img, this.getWidth(), this.getHeight(), null);
        } catch (IOException ex) {
            Logger.getLogger(MyLabel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
