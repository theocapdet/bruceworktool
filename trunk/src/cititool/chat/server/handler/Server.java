/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.chat.server.handler;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author zx04741
 */
public abstract class Server extends Thread{

    protected Socket socket;

    protected Server(){
        this.setDaemon(true);
        this.socket=socket;
    }

    protected void setSocket(Socket socket){

        this.socket=socket;
    }
    

}
