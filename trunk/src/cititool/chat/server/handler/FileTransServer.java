/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.client.listener.FileProcesser;
import cititool.chat.server.ServerContext;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zx04741
 */
public class FileTransServer extends Server {

    private static Semaphore gate = new Semaphore(1);
    private String relativepath;

    public FileTransServer(Socket socket,String file) {
        super();
        this.setSocket(socket,file);
    }

    public synchronized void setSocket(Socket socket,String path) {
        super.setSocket(socket);
        this.relativepath=path;
        if (socket != null) {
            gate.release();
        }
    }


    public void run() {
        try {
            gate.acquire();
        } catch (InterruptedException ex) {
            ServerContext.warnServerLog("FileTransServer:: lock init acquire:", ex);
        }
        do {
            try {
                gate.acquire();
            } catch (InterruptedException ex) {
                ServerContext.warnServerLog("FileTransServer::lock  acquire:", ex);
            }
           long s = System.currentTimeMillis();
           FileProcesser fp=new FileProcesser();
           File f=new File(ServerContext.getPicpath()+File.separator +relativepath);
            try {
                fp.writeFile(f, socket);
            } catch (IOException ex) {
                ServerContext.warnServerLog("FileTransServer::writ error:", ex);
            }
           long e = System.currentTimeMillis();
           ServerContext.productServerLog("transfer  file "+relativepath+ " execute:" + (e - s) + "ms", null);
        } while (socket != null && !socket.isClosed());
        gate.release();
    }
}
