/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.client.listener.FileProcesser;
import cititool.chat.protocol.TransProtocol;
import cititool.chat.server.ServerContext;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Administrator
 */
public class FileServer {

    private String requestType;
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public void setReqType(String type) {

        this.requestType = type;
    }

    private void task(final Socket sender, final String sendName, final SessionServer recv, final String file) {

        if (requestType.equals(TransProtocol.REQUEST_FILE_H)) {
            try {
                TransProtocol.writeStr(TransProtocol.REQUEST_FILE_H, sender);
            } catch (IOException ex) {
                ServerContext.warnServerLog("FileTransServer::writ pic header error:", ex);
            }
            FileProcesser fp = new FileProcesser();
            File f = new File(ServerContext.getSystemFileFolder() + File.separator + file);
            try {
                if (f.exists()) {
                    ServerContext.productServerLog(file + " exists!");
                    TransProtocol.writeStr(TransProtocol.FILE_EXISTS + file, sender);
                    fp.writeFile(f, sender);
                } else {
                    ServerContext.productServerLog(file + "dont exists!");
                    TransProtocol.writeStr(TransProtocol.FILE_NO_EXISTS + file, sender);
                }
            } catch (IOException ex) {
                ServerContext.warnServerLog("FileTransServer::writ pic error:", ex);
            } catch (InterruptedException e) {
                ServerContext.warnServerLog("FileTransServer::writ pic error:", e);
            }

        } else if (requestType.equals(TransProtocol.TRANSFER_FH)) {
            try {
                recv.writeStr(TransProtocol.TRANSFER_FH + sendName + TransProtocol.SPLIT + file);
            } catch (IOException ex) {
                ServerContext.warnServerLog("FileTransServer::write transfer header error:", ex);
            }


        }
    }

    public void addTask(final Socket sender, final String file) {

        addTask(sender, null, null, file);
    }

    public void addTask(final Socket sender, final String sendName, final SessionServer recv, final String file) {
        pool.execute(new Runnable() {

            public void run() {
                task(sender, sendName, recv, file);
            }
        });
    }
}
