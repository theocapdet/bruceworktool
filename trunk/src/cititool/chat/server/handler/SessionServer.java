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
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import javax.swing.JTextArea;

/**
 *
 * @author zx04741
 */
public class SessionServer extends Server {

    private JTextArea log;
    private String username;
    private Map<String, SessionServer> pool;

    public SessionServer(String serverName,String username,  Socket socket) {
        super(socket,serverName);
        this.username = username;
        pool=ServerContext.getServer(serverName).getSessionPool();
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                Object content = TransProtocol.responseObject(socket).toString();
                if (content instanceof String) {
                    String t = content.toString();
                    if (t.startsWith(TransProtocol.TALK_SEND_H)) {
                        //talk
                        String p[] = t.substring(TransProtocol.TALK_SEND_H.length()).split(TransProtocol.SPLIT);
                        String user = p[0];
                        String talk = p[1];
                        ServerContext.productServerLog("sendMsg::  from [" + username + "] to[" + user + "]==>" + talk, null);
                        SessionServer sSr = pool.get(user);
                        sSr.sendMsg(talk, username);

                    }
                    else if (t.startsWith(TransProtocol.USERLIST_HEADER)) {
                        Set onlineusers = pool.keySet();
                        Object[] users = (Object[]) onlineusers.toArray();
                        TransProtocol.writeObj(users, socket);
                    } else if (t.startsWith(TransProtocol.USERNAME_HEADER)) {
                        String username = t.substring(TransProtocol.USERNAME_HEADER.length());
                        UserInfo user = ServerContext.getUserByUserName(username);
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
        TransProtocol.sendTalk(str, username, socket);
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }
}
