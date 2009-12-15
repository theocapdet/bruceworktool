/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.protocol.TransProtocol;
import cititool.chat.server.ServerContext;
import cititool.util.ComponentHelper;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author zx04741
 */
public class ServerController extends Thread {

    private Socket socket;
    private ServerSocket server;
    private JTextArea log;
    private String username;

    public ServerController(String username,ServerSocket server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.username=username;
        this.setDaemon(true);
    }

    public void setLog(JTextArea log) {

        this.log = log;
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                socket = server.accept();
                Object content = TransProtocol.getObject(getSocket()).toString();
                if (content instanceof String) {
                    String t = content.toString();
                    if (t.startsWith(TransProtocol.CTALK_SEND_H)) {
                        //talk
                        String p[] = t.substring(1).split(TransProtocol.SPLIT);
                        String user = p[0];
                        String talk = p[1];
                        productLog("from ["+username+"] to["+user+"]==>"+talk);
                    }
                    else if(t.startsWith(TransProtocol.USERLIST_HEADER)){



                    }else if(t.startsWith(TransProtocol.USERNAME_HEADER)){

                    }

                } else if (content instanceof File) {
                } else if (content instanceof Image) {
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void productLog(String str) {
        if(log!=null)
        ComponentHelper.jtaAppendLine(log, str);
    }

    public void sendMsg(String str, String username) throws IOException {
        TransProtocol.sendTalk(str, username, getSocket());
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @return the server
     */
    public ServerSocket getServer() {
        return server;
    }
}
