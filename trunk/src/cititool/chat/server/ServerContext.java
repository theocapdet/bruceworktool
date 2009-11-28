/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server;

import cititool.chat.db.ServerDB;
import cititool.model.UserInfo;
import cititool.chat.protocol.TransProtocol;
import cititool.chat.server.handler.MainServer;
import cititool.chat.server.handler.SessionServer;
import cititool.util.ComponentHelper;
import cititool.util.StringHelper;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import org.jdom.JDOMException;


/**
 *
 * @author Administrator
 */
public class ServerContext {

    //user list
    private static ChatServerFactory factory;
    private static ServerDB db;
    private static String picpath="C:/picstore/";

    /**
     * @return the picpath
     */
    public static String getPicpath() {
        return picpath;
    }

    /**
     * @param aPicpath the picpath to set
     */
    public static void setPicpath(String aPicpath) {
        picpath = aPicpath;
        File f=new File(picpath);
        if(!f.exists())
            f.mkdirs();
    }

    private ServerContext() {
    }
    private static JTextPane slog;
    private static JTextPane dblog;



    public static void setServerLogger(JTextPane log) {
        slog = log;
    }

    public static void setDBLogger(JTextPane log) {
        dblog = log;
    }

    public static void productDBLog(String str, Exception ex) {
        if (dblog != null) {
            try {
                if (ex != null) {
                    if(!StringHelper.isEmpty(str))
                        str = str + ",";
                    else
                        str=  "exception=>" + ex;
                }
                ComponentHelper.jtpAppendLine(dblog, str);
            } catch (BadLocationException ex1) {
                Logger.getLogger(ServerContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

     public static void warnDBLog(String str, Exception ex) {
        if (dblog != null) {
            try {
                if (ex != null) {
                    if(!StringHelper.isEmpty(str))
                        str = str + ",";
                    else
                        str=  "exception=>" + ex;
                }
                ComponentHelper.jtpAppendLine(dblog, str,Color.red);              
            } catch (BadLocationException ex1) {
                Logger.getLogger(ServerContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public static void productServerLog(String str, Exception ex) {
        if (slog != null) {
            try {
                if (ex != null) {
                    str = str + "," + ex;
                }
                ComponentHelper.jtpAppendLine(slog, str);
            } catch (BadLocationException ex1) {
                Logger.getLogger(ServerContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public static void warnServerLog(String str, Exception ex) {
        if (slog != null) {
            try {
                if (ex != null) {
                    str = str + "," + ex;
                }
                ComponentHelper.jtpAppendLine(slog, str,Color.red);
            } catch (BadLocationException ex1) {
                Logger.getLogger(ServerContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public static ServerDB getDB() {
        return db;
    }

    public static void startDB() {
        try {
            productDBLog("startDB==>beign...", null);
            db.startDB();

        } catch (IOException ex) {
            productDBLog("startDB==>", ex);
        } catch (JDOMException ex) {
            productDBLog("startDB==>", ex);
        } catch (ClassNotFoundException ex) {
            productDBLog("startDB==>", ex);
        } catch (NoSuchMethodException ex) {
            productDBLog("startDB==>", ex);
        } catch (InvocationTargetException ex) {
            productDBLog("startDB==>", ex);
        } catch (InstantiationException ex) {
            productDBLog("startDB==>", ex);
        } catch (IllegalAccessException ex) {
            productDBLog("startDB==>", ex);
        }
        productDBLog("startDB==>over...", null);
    }

    public static void instance() {
        factory = ChatServerFactory.createInstance();
        productServerLog("chat server factory  create success", null);
        db = ServerDB.createInstance();
        productServerLog("database instance success", null);
    }

    public static ChatServerFactory getServerFactory() {
        return factory;
    }

    public static MainServer getServer(String serverName){

       return factory.getServerByName(serverName);
    }

    public static UserInfo getUserByUserName(String username) {
        List list = db.getTableList(UserInfo.class);
        for (int i = 0; i < list.size(); i++) {
            UserInfo user = (UserInfo) list.get(i);
            if (user.getUsername().trim().endsWith(username)) {
                return user;
            }
        }
        return new UserInfo();
    }

    public static void getUserInfo(String servername, String username, String geter) throws IOException {

        MainServer server = factory.getServerByName(servername);
        if (server != null) {
            UserInfo user = getUserByUserName(username);
            SessionServer controller = server.getUserServer(geter);
            if (controller != null && controller.getState() == Thread.State.RUNNABLE) {
                TransProtocol.writeObj(user, controller.getSocket());
            }
        }
    }

    public static void LogOff() {
    }

    public static Map getOnlineUserInfo(String serverName) {
        MainServer server = factory.getServerByName(serverName);
        return server.getSessionPool();
    }
}
