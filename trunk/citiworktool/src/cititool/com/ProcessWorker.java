/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.com;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author zx04741
 */
public class ProcessWorker {

    private List components;
    private String cmd;
    private boolean isDebug=false;
    private volatile static Semaphore sem =new Semaphore(1);
    

    public ProcessWorker(List components) {

        this.components = components;

    }

    public ProcessWorker(Component com) {
        if (components == null) {
            components = new ArrayList();
        }
        if(!components.contains(com))
        this.components.add(com);
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setDebug(boolean debug){
        isDebug=debug;
    }

    private void updateUI(String s) {

        for (int i = 0; i < components.size(); i++) {

            if (components.get(i) instanceof JTextArea) {
                JTextArea jta1 = (JTextArea) components.get(i);
                jta1.append(s + "\r\n");
//                System.out.println(s);
                jta1.setCaretPosition(jta1.getText().length());
            }
        }
    }

    private void processUpdateUI(final Process pc) {
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(pc.getInputStream(),"gb2312"));
            String s = "";
            while ((s = br.readLine()) != null) {

                updateUI(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcessWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public void exeCmd(){
        this.exeCmd(false, null, cmd);
    }


    public void exeCmd(boolean isUseDir,String[] env,String dir) {
        try {
            sem.acquire();
            if(isDebug)
            updateUI(cmd);
//            over = false;
           Process tmp;
           if(!isUseDir){
            tmp=Runtime.getRuntime().exec("cmd.exe /c"+cmd,env);
           }
           else {
            tmp=Runtime.getRuntime().exec("cmd.exe /c"+cmd,env,new File(dir));
           }
          final Process pc=tmp;

          Thread t=new Thread(new Runnable() {
                public void run() {
                      processUpdateUI(pc);
                      pc.destroy();
                      sem.release();
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessWorker.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(ProcessWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
