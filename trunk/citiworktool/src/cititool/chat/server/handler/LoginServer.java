/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.model.SystemConstants;
import cititool.chat.protocol.TransProtocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zx04741
 */
public class LoginServer extends Server {

    public LoginServer(Socket socket) {
        super(socket);
    }


    public void run() {

        while (!socket.isClosed() ) {
            

        }
    }
}
