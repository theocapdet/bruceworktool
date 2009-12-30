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
import java.util.concurrent.Semaphore;

/**
 *
 * @author zx04741
 */
public class FileTransServer extends Server {
/*
    private static Semaphore gate = new Semaphore(1);
    private String relativepath;

    public FileTransServer(Socket socket, String file) {
        super();
        this.setSocket(socket, file);
    }

    public synchronized void setSocket(Socket socket, String path) {
        super.setSocket(socket);
        this.relativepath = path;
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
            String str=null;
            try {
                str = TransProtocol.getObject(socket).toString();
            } catch (IOException ex) {
                 ServerContext.warnServerLog("FileTransServer::get file type:", ex);
            } catch (ClassNotFoundException ex) {
                 ServerContext.warnServerLog("FileTransServer::get file type:", ex);
            }
            long s = System.currentTimeMillis();
            if (str.equals(TransProtocol.USERPIC_H)) {
                try {
                    TransProtocol.writeStr(TransProtocol.USERPIC_H, socket);
                } catch (IOException ex) {
                    ServerContext.warnServerLog("FileTransServer::writ pic header error:", ex);
                }
                FileProcesser fp = new FileProcesser();
                File f = new File(ServerContext.getPicpath() + File.separator + relativepath);
                try {
                    fp.writeFile(f, socket);
                    
                } catch (IOException ex) {
                    ServerContext.warnServerLog("FileTransServer::writ pic error:", ex);
                }catch(InterruptedException e){
                    ServerContext.warnServerLog("FileTransServer::writ pic error:", e);
                }
            }
            else if(str.equals(TransProtocol.ISFILE_H)){

                
            }

            long e = System.currentTimeMillis();
            ServerContext.productServerLog("transfer  file " + relativepath + " execute:" + (e - s) + "ms", null);
        } while (socket != null && !socket.isClosed());
        gate.release();
    }
 * */
}
