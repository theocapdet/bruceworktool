/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.model.SystemConstants;
import cititool.chat.server.*;
import cititool.model.UserInfo;
import java.io.IOException;

/**
 *
 * @author zx04741
 *
 *
 *
 */
public class ServerDataHandler {

    public static int MatchLogin(String user, String pass, String servername) throws IOException, ClassNotFoundException {


        UserInfo ui = ServerContext.getUserByUserName(user);
        if (ui.getPass().equals(pass)) {
            return SystemConstants.LOGON;
        } else {
            return SystemConstants.NOAUTHORIZE;
        }
    }
}
