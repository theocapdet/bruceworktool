/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.client.listener.FileProcesser;
import cititool.chat.model.SystemConstants;
import cititool.chat.protocol.TransProtocol;
import cititool.chat.server.ServerContext;
import cititool.model.UserInfo;
import cititool.util.ReflectHelper;
import cititool.util.StringHelper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 *
 * @author zx04741
 */
public class RegisterServer extends Server {

    private static Semaphore gate = new Semaphore(1);

    public RegisterServer(Socket socket) {
        super();
        this.setSocket(socket);
    }

    public synchronized void setSocket(Socket socket) {
        super.setSocket(socket);
        if (socket != null) {
            gate.release();
        }
    }

    public void run() {
        try {
            gate.acquire();
        } catch (InterruptedException ex) {
            ServerContext.warnServerLog("register:", ex);
        }
        do {
            try {
                gate.acquire();
            } catch (InterruptedException ex) {
                ServerContext.warnServerLog("register:", ex);
            }
            long s = System.currentTimeMillis();
            UserInfo user = null;
            try {
                user = (UserInfo) TransProtocol.getObject(socket);
                if (!StringHelper.isEmpty(user.getPhotopath())) {
                    long s1 = System.currentTimeMillis();                  
                    FileProcesser fp = new FileProcesser();
                    fp.readFileInFolder(ServerContext.getSystemFileFolder()+File.separator + user.getUsername(), socket);
                    long e1 = System.currentTimeMillis();
                    System.out.println(" update photo time:" + (e1 - s1) + "ms");
                }
                boolean f = false;
                int index = 0;
                try {
                    index = ServerContext.getDB().addorUpdateRecord(user);
                } catch (NoSuchMethodException ex) {
                    f = true;
                    ServerContext.warnDBLog("register:", ex);
                } catch (IllegalAccessException ex) {
                    f = true;
                    ServerContext.warnDBLog("register:", ex);
                } catch (IllegalArgumentException ex) {
                    f = true;
                    ServerContext.warnDBLog("register:", ex);
                } catch (InvocationTargetException ex) {
                    f = true;
                    ServerContext.warnDBLog("register:", ex);
                }

                if (!f) {
                    if (index > -1) {
                        TransProtocol.writeStr(SystemConstants.REG_REP + "", socket);
                    } else {
                        TransProtocol.writeStr(SystemConstants.REG_SUC + "", socket);
                    }
                } else {
                    TransProtocol.writeStr(SystemConstants.REG_FAIL + "", socket);
                }
            } catch (IOException ex) {
                ServerContext.warnServerLog("register:", ex);
            } catch (ClassNotFoundException ex) {
                ServerContext.warnServerLog("register:", ex);
            }
            long e = System.currentTimeMillis();
            ServerContext.productServerLog(ReflectHelper.getBeanStr(user) + " execute:" + (e - s) + "ms", null);
        } while (socket != null && !socket.isClosed());

        gate.release();
    }
}
