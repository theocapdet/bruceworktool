/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.protocol.TransProtocol;
import cititool.chat.server.ServerContext;
import cititool.model.UserInfo;
import cititool.util.ComponentHelper;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import javax.swing.JTextArea;

/**
 *
 * @author zx04741
 */
public class SessionServer extends Thread {

    private Socket socket;
    private JTextArea log;
    private String username;
    private static Map pool;

    public SessionServer(String username, Socket socket) {
        this.socket = socket;
        this.username = username;
    }

    public void setPool(Map pool){
        this.pool=pool;
    }

    public void setLog(JTextArea log) {
        this.log = log;
    }
    

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                Object content = TransProtocol.readObject(socket).toString();
                if (content instanceof String) {
                    String t = content.toString();
                    if (t.startsWith(TransProtocol.TALK_HEADER)) {
                        //talk
                        String p[] = t.substring(1).split(TransProtocol.SPLIT);
                        String user = p[0];
                        String talk = p[1];
                        productLog("from [" + username + "] to[" + user + "]==>" + talk);
                    } else if (t.startsWith(TransProtocol.USERLIST_HEADER)) {
                        Set onlineusers = pool.keySet();
                        Object[] users=(Object[])onlineusers.toArray();
                        TransProtocol.writeObj(users, socket);
                    } else if (t.startsWith(TransProtocol.USERNAME_HEADER)) {
                        String username=t.substring(TransProtocol.USERNAME_HEADER.length());
                        UserInfo user=ServerContext.getClientByUserName(username);
                        TransProtocol.writeObj(user, socket);
                    }

                } else if (content instanceof File) {
                } else if (content instanceof Image) {
                }
            } catch (IOException ex) {
                ServerContext.productServerLog("SessionServer error:", ex);
            } catch (ClassNotFoundException ex) {
               ServerContext.productServerLog("SessionServer error:", ex);
            }
        }

    }

    public void productLog(String str) {
        if (log != null) {
            ComponentHelper.jtaAppendLine(log, str);
        }
    }

    public void sendMsg(String str, String username) throws IOException {
        TransProtocol.writeTalk(str, username, getSocket());
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }


}
