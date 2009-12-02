/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.model.SystemConstants;
import cititool.chat.server.*;
import cititool.model.UserInfo;
import cititool.util.StringHelper;

/**
 *
 * @author zx04741
 *
 *
 *
 */
public class ServerDataHandler {

    public static int MatchLogin(String user, String pass, String servername)  {

        UserInfo ui = ServerContext.getUserByUserName(user);
        if (StringHelper.null2String(ui.getPass()).equals(pass)) {
            return SystemConstants.LOGON;
        } else {
            return SystemConstants.NOAUTHORIZE;
        }
    }
}
