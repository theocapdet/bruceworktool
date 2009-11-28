/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.chat.model;

import cititool.model.UserInfo;
import java.net.Socket;

/**
 *
 * @author Administrator
 */
public class UserClient {

    private UserInfo user;
    private Socket client;


    public UserClient(UserInfo userinfo){

        this.user=userinfo;

    }

    public UserClient(){
    
    }

    /**
     * @return the user
     */
    public UserInfo getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserInfo user) {
        this.user = user;
    }

    /**
     * @return the client
     */
    public Socket getSocket() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setSocket(Socket client) {
        this.client = client;
    }



}
