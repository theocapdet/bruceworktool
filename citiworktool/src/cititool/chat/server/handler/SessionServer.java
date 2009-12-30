/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.client.listener.FileProcesser;
import cititool.chat.protocol.TransProtocol;
import cititool.chat.server.ServerContext;
import cititool.chat.server.util.ServerUtils;
import cititool.model.UserInfo;
import cititool.util.ComponentHelper;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    //file transport server
    private static FileServer fileServer = new FileServer();
    private ObjectOutputStream oos;

    public SessionServer(String serverName, String username, Socket socket) {
        super(socket, serverName);
        this.username = username;
        pool = ServerContext.getServer(serverName).getSessionPool();
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            Object content = null;
            try {
                content = TransProtocol.getObject(socket).toString();
            } catch (IOException ex) {
                ServerContext.warnServerLog("SessionServer read error:", ex);
                if (!isConnected()) {
                    break;
                }
                continue;
            } catch (ClassNotFoundException ex) {
                ServerContext.warnServerLog("SessionServer read error:", ex);
                if (!isConnected()) {
                    break;
                }
                continue;
            }
            if (content instanceof String) {
                String t = content.toString();
                if (t.startsWith(TransProtocol.CTALK_SEND_H)) {
                    //talk
                    String[] p = t.substring(TransProtocol.CTALK_SEND_H.length()).split(TransProtocol.SPLIT);
                    String user = p[0];
                    String talk = p[1];
                    ServerContext.productServerLog("sendMsg::  from [" + username + "] to[" + user + "]==>" + talk, null);
                    SessionServer sSr = pool.get(user);
                    try {
                        sSr.sendMsg(talk, username);
                    } catch (IOException ex) {
                        ServerContext.warnServerLog("SessionServer sendMsg error:", ex);
                        if (!isConnected()) {
                            break;
                        }
                        continue;
                    }
                } else if (t.startsWith(TransProtocol.USERLIST_HEADER)) {
                    Set onlineusers = pool.keySet();
                    Object[] users = (Object[]) onlineusers.toArray();
                    try {
                        TransProtocol.writeStr(TransProtocol.USERLIST_HEADER, socket);
                        TransProtocol.writeObj(users, socket);
                    } catch (IOException ex) {
                        ServerContext.warnServerLog("SessionServer get userlist error:", ex);
                        if (!isConnected()) {
                            break;
                        }
                        continue;
                    }
                } else if (t.startsWith(TransProtocol.USERNAME_HEADER)) {
                    String username = t.substring(TransProtocol.USERNAME_HEADER.length());
                    UserInfo user = ServerContext.getUserByUserName(username);
                    try {
                        TransProtocol.writeStr(TransProtocol.USERNAME_HEADER, socket);
                        TransProtocol.writeObj(user, socket);
                    } catch (IOException ex) {
                        ServerContext.warnServerLog("SessionServer get user error:", ex);
                        if (!isConnected()) {
                            break;
                        }
                        continue;
                    }
                } else if (t.startsWith(TransProtocol.REQUEST_FILE_H)) {
                    String file = t.substring(TransProtocol.REQUEST_FILE_H.length());
                    fileServer.setReqType(TransProtocol.REQUEST_FILE_H);
                    fileServer.addTask(socket, file);
                } //tranfer file to others
                else if (t.startsWith(TransProtocol.TRANSFER_FH)) {
                    String[] tmp = t.substring(TransProtocol.TRANSFER_FH.length()).split(TransProtocol.SPLIT);
                    String recv = tmp[0];
                    String file = tmp[1];
                    SessionServer sock = pool.get(recv);
                    fileServer.setReqType(TransProtocol.TRANSFER_FH);
                    fileServer.addTask(socket, username, sock, file);
                } else if (t.startsWith(TransProtocol.OFFLINE_H)) {
                    try {
                        ServerUtils.sendAll(TransProtocol.OFFLINE_H, username, pool, username);
                        ServerUtils.remindAll(TransProtocol.POPMSG_H, pool, username, "remind message", username + " is offline now!");
                    } catch (IOException ex) {
                        ServerContext.warnServerLog("SessionServer user exit error:", ex);
                    }
                    break;
                } //server get the cmd from the recv's ready
                else if (t.startsWith(TransProtocol.READY_TRANSFER_FH)) {
                    try {
                        String[] str = t.substring(TransProtocol.READY_TRANSFER_FH.length()).split(TransProtocol.SPLIT);
                        String filepath = str[0];
                        Socket sender = pool.get(str[1]).getSocket();
                        System.out.println("step3==>get recv savepath==>" + filepath);
                        TransProtocol.writeStr(TransProtocol.S_START_TRANSFER_FH, sender);
                        System.out.println("step4==>send to sender start write file");
                        TransProtocol.writeStr(TransProtocol.R_START_TRANSFER_FH, socket);
                        System.out.println("step5==>send to recv start receive file");
                        FileProcesser fp = new FileProcesser();
                        fp.transferFile(sender, socket, filepath);

                    } catch (IOException ex) {
                        ServerContext.warnServerLog("FileTransServer::transferFile error:", ex);
                    } catch (ClassNotFoundException ex) {
                        ServerContext.warnServerLog("FileTransServer::transferFile error:", ex);
                    }
                }
            }
        }
        pool.remove(username);
        ServerContext.productServerLog(username + "==>log off");
    }

    public void productLog(String str) {
        if (log != null) {
            ComponentHelper.jtaAppendLine(log, str);
        }
    }

    public void sendMsg(String str, String username) throws IOException {
        TransProtocol.recvTalk(str, username, socket);
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    public void writeStr(String str) throws IOException {

        TransProtocol.writeStr(str, socket);
    }

    public Object getObject() throws IOException, ClassNotFoundException {

        return TransProtocol.getObject(socket);
    }

    public void writeLong(long t) throws IOException {

        oos.writeLong(t);
        oos.flush();
    }

    public void writeObject(Object o) throws IOException {

        oos.writeObject(o);
        oos.flush();
    }

    public void writeInt(int i) throws IOException {
        oos.writeInt(i);
        oos.flush();
    }

    public ObjectOutputStream getObjectOutputStream() throws IOException {
        return new ObjectOutputStream(socket.getOutputStream());
    }
}
