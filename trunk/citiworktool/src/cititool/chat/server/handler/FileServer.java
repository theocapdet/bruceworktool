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

    private static ExecutorService pool = Executors.newCachedThreadPool();

    public FileServer() {
    }

    private void task(final Socket sender, final Socket recv, final String file) {
        String str = null;
        try {
            str = TransProtocol.getObject(sender).toString();
        } catch (IOException ex) {
            ServerContext.warnServerLog("FileTransServer::get file type:", ex);
        } catch (ClassNotFoundException ex) {
            ServerContext.warnServerLog("FileTransServer::get file type:", ex);
        }
        if (str.equals(TransProtocol.USERPIC_H)) {
            try {
                TransProtocol.writeStr(TransProtocol.USERPIC_H, sender);
            } catch (IOException ex) {
                ServerContext.warnServerLog("FileTransServer::writ pic header error:", ex);
            }
            FileProcesser fp = new FileProcesser();
            File f = new File(ServerContext.getPicpath() + File.separator + file);
            try {
                fp.writeFile(f, sender);

            } catch (IOException ex) {
                ServerContext.warnServerLog("FileTransServer::writ pic error:", ex);
            } catch (InterruptedException e) {
                ServerContext.warnServerLog("FileTransServer::writ pic error:", e);
            }
        } else if (str.equals(TransProtocol.ISFILE_H)) {
            try {
                TransProtocol.writeStr(TransProtocol.ISFILE_H, recv);
            } catch (IOException ex) {
                ServerContext.warnServerLog("FileTransServer::writ pic header error:", ex);
            }
            FileProcesser fp = new FileProcesser();
            try {
                fp.transferFile(sender, recv);
            } catch (IOException ex) {
                ServerContext.warnServerLog("FileTransServer::transferFile error:", ex);
            } catch (ClassNotFoundException ex) {
                ServerContext.warnServerLog("FileTransServer::transferFile error:", ex);
            }

        }
    }

    public void addTask(final Socket sender, final String file){
        addTask(sender, null, file);
    }

    public void addTask(final Socket sender, final Socket recv, final String file) {
        pool.execute(new Runnable() {
            public void run() {
                task(sender, recv, file);
            }
        });
    }
}
