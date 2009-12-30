/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client.handler;

import cititool.chat.client.ClientContext;
import cititool.chat.client.com.panel.FilePanel;
import cititool.chat.client.listener.FileProcesser;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
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
    private String sender;

    public RecieveFileJob(String sender,String filename, JPanel panel) {
        this.filename = filename;
        this.sender=sender;
        socket = ClientContext.getCurrentSocket();
        this.mainpanel = panel;

    }

    public void prepared() {
        this.widget = new FilePanel(filename, this);
        EventQueue.invokeLater(new Runnable() {
            public void run() {               
                widget.isRecv();
                widget.setSenderName(sender);
                mainpanel.add(widget);
                mainpanel.updateUI();
            }
        });

    }

    public void run() {
        boolean flg = false;
        try {
            process = new FileProcesser();
            process.setProcessBar(widget.getProcessBar());
            process.setProcessLabel(widget.getProgressLabel());
            process.setSpeedLabel(widget.getSpeedLabel());
            process.readFile(socket);
        } catch (ClassNotFoundException ex) {
            ClientContext.productLog("upload file " + filename + " IOerror:" + ex);
            flg = true;
        } catch (IOException ex) {
            ClientContext.productLog("upload file " + filename + " IOerror:" + ex);
            flg = true;
        } finally {
            if (!flg) {
                mainpanel.remove(widget);
            }
        }
    }

    public void cancel() {
        this.interrupt();
    }
}
