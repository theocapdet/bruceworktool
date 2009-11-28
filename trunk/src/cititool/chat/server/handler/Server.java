/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import java.net.Socket;

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
        this.serverName=serverName;
    }

    protected Server() {
        this.setDaemon(true);
    }

    protected void setSocket(Socket socket) {
        this.socket = socket;
    }
}
