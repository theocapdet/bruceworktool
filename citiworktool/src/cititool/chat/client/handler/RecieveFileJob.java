/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client.handler;

import cititool.chat.client.ClientContext;
import cititool.chat.client.com.FilePanel;
import cititool.chat.client.listener.FileProcesser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class RecieveFileJob extends Thread implements WorkJob {

    private FilePanel widget;
    private Socket socket;
    private String filename;
    private JPanel mainpanel;
    private FileProcesser process;
    

    public RecieveFileJob(String filename, JPanel panel) {
        this.filename = filename;
        socket = ClientContext.getCurrentSocket();
        this.mainpanel = panel;

    }

    public void prepared() {
        this.widget = new FilePanel(filename, this);
        widget.setRecv();
        mainpanel.add(widget);
    }

    public void run() {
        boolean flg=false;
        try {
            process = new FileProcesser();
            process.setProcessBar(widget.getProcessBar());
            process.setProcessLabel(widget.getProgressLabel());
            process.setSpeedLabel(widget.getSpeedLabel());
            process.setReadFileName(filename);
            process.readFile(null, socket);
        } catch (ClassNotFoundException ex) {
             ClientContext.productLog("upload file " + filename + " IOerror:" + ex);
             flg=true;
        } catch (IOException ex) {
            ClientContext.productLog("upload file " + filename + " IOerror:" + ex);
            flg=true;
        } finally {
            if(!flg)

            mainpanel.remove(widget);
        }
    }

    public void cancel() {
        this.interrupt();
    }
}
