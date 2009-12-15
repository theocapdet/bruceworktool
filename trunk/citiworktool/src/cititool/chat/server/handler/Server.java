/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zx04741
 */
public abstract class Server extends Thread {

    protected Socket socket;
    protected String serverName;

    protected Server(Socket socket) {
        this();
        this.socket = socket;
    }

    protected Server(Socket socket, String serverName) {
        this(socket);
        this.serverName = serverName;
    }

    protected Server() {
        this.setDaemon(true);
    }

    protected synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }

    protected boolean isConnected() {
        try {
            socket.sendUrgentData(0xFF);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}
