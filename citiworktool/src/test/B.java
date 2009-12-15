/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.File;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class B extends Thread{
    
    
    private Lock lock=new ReentrantLock();
    private Condition con=lock.newCondition();

    public void test(){

       con.signal();
    }

    public void run(){

         System.out.println("in...");
            try {
                con.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(B.class.getName()).log(Level.SEVERE, null, ex);
            }

        System.out.println("out.....");
      

    }

    public void del(File root){

        t(root);
    }

    private void t(File f){

        if(f.isDirectory()){

            if(f.getName().equals(".svn")){
                System.out.println(f.getPath());
                boolean flg= f.delete();
                System.out.println("flg=>"+flg);
            }else{

                File[] fs=f.listFiles();
                for(int i=0;i<fs.length;i++)
                    t(fs[i]);
            }
        }
    }

    public static void main(String args[]){

        B b=new B();
        b.del(new File("G:/netbeansws/citiworktool"));
    }

}
