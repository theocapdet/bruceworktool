/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client.handler;

import cititool.chat.client.ClientContext;
import cititool.chat.client.com.panel.FilePanel;
import cititool.chat.client.listener.FileProcesser;
import cititool.chat.protocol.TransProtocol;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JPanel;

/**
 *
 * @author zx04741
 */
public class SenderFileJob extends Thread implements WorkJob {

    private FilePanel widget;
    private Socket socket;
    private File f;
    private FileProcesser process;
    private String recv;
    private JPanel main;

    public SenderFileJob(File file, JPanel panel, String recv) {
        this.f = file;
        this.recv = recv;
        socket = ClientContext.getCurrentSocket();
        this.main = panel;
    }

    public void prepared() throws IOException {
        this.widget = new FilePanel(f.getPath(), this);
        widget.isSender();
        main.add(widget);
        main.updateUI();
        TransProtocol.writeStr(TransProtocol.TRANSFER_FH + recv + TransProtocol.SPLIT + f.getName(), socket);
    }

    public void run() {
        try {
            process = new FileProcesser();
            process.setProcessBar(widget.getProcessBar());
            process.setProcessLabel(widget.getProgressLabel());
            process.setSpeedLabel(widget.getSpeedLabel());            
            process.writeFile(f, socket);
            
        }  catch (IOException ex) {
            ClientContext.productLog("upload file " + f.getPath() + " IOerror:" + ex);
        } catch (InterruptedException ex) {
            ClientContext.productLog("upload file " + f.getPath() + " Inerrupterror:" + ex);
        }
    }

    public void cancel() {
        this.interrupt();
    }

    public void lock() {
        process.stop();
    }

    public void unlock() {
        process.retore();
    }
}
