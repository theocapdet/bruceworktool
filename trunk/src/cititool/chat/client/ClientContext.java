/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client;

import cititool.chat.model.UserClient;
import cititool.chat.protocol.TransProtocol;
import cititool.model.UserInfo;
import cititool.util.ComponentHelper;
import cititool.util.StringHelper;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author zx04741
 */
public class ClientContext {

    private static Map<String, UserClient> users = new ConcurrentHashMap<String, UserClient>();
    private static Map<String, UserInfo> bufferUserInfo = new ConcurrentHashMap<String, UserInfo>();
    //current session...
//    private static ThreadLocal<String> session = new ThreadLocal<String>();
    private static JTextPane logpane;

    private static ClientContext context;
    private ClientContext(){
        
    }
    public static ClientContext instance(){
       if(context==null){
            context=new ClientContext();
       }
       return context;
    }


    public static void addorUpdateClient(String username, UserClient client) {
        users.put(username, client);
    }
    
    public static void setlog(JTextPane log){
        logpane=log;
    }

    public static void productLog(String str, Exception ex){
     
     if (logpane != null) {
            try {
                if (ex != null) {
                    if(!StringHelper.isEmpty(str))
                        str = str + ",";
                    else
                        str=  "exception=>" + ex;
                }
                ComponentHelper.jtpAppendLine(logpane, str);
            } catch (BadLocationException ex1) {
                warnLog("",ex1);
            }
        }
    }

    public static void warnLog(String str, Exception ex){
        
        if (logpane != null) {
            try {
                if (ex != null) {
                    if(!StringHelper.isEmpty(str))
                        str = str + ",";
                    else
                        str=  "exception=>" + ex;
                }
                ComponentHelper.jtpAppendLine(logpane, str,Color.red);
            } catch (BadLocationException ex1) {
                warnLog("",ex1);
            }
        }
    }

    public static void addorUpdateSocket(String username, Socket sock) {
        UserClient client = users.get(username);
        if (client == null) {
            client = new UserClient();
        }
        client.setClient(sock);
        addorUpdateClient(username, client);
    }

    public static void addorUpdateUseInfo(String username, UserInfo info) {
        UserClient client = users.get(username);
        if (client == null) {
            client = new UserClient();
        }
        client.setUser(info);
        addorUpdateClient(username, client);
    }

    public static UserClient getCurrentUserClient(String username) {
        return users.get(username);
    }

    public static Socket getUserSocket(String username) {
        UserClient client = users.get(username);
        if (client != null) {
            return client.getClient();
        } else {
            return null;
        }
    }

    public static UserInfo getUserInfo(String username) {
        UserClient client = users.get(username);
        if (client != null) {
            return client.getUser();
        } else {
            return null;
        }
    }

    public static UserInfo loadUserInfotFromServer(String username,String curuser) throws IOException, ClassNotFoundException {

        Socket s = null;
        if ((s = getUserSocket(curuser)) != null) {
            TransProtocol.writeUserName(username, s);
            UserInfo user = (UserInfo) TransProtocol.readObject(s);
            return user;
        } else {
            return null;
        }
    }

    public static Object[] loadUserListFromServer(String curuser) throws IOException, ClassNotFoundException {
        Socket s = null;
        if ((s = getUserSocket(curuser)) != null) {
            TransProtocol.writeUserList(s);
            Object[] userlist = (Object[]) TransProtocol.readObject(s);
            return userlist;
        } else {
            String[] str={};
            return str;
        }
    }
    


    public static void setFriendInfo(String username, UserInfo user) {

        bufferUserInfo.put(username, user);

    }

    public static UserInfo getFriendInfo(String username) {

        return bufferUserInfo.get(username);
    }


    public static void registerUser(UserInfo user,Socket socket){
        try {
            TransProtocol.writeRegister(user, socket);
        } catch (IOException ex) {
            
        } catch (InterruptedException ex) {
           
        }
        
    }
}
