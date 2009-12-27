/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client;

import cititool.chat.client.listener.FileProcesser;
import cititool.chat.model.UserClient;
import cititool.chat.protocol.TransProtocol;
import cititool.model.UserInfo;
import cititool.util.ComponentHelper;
import cititool.util.ReflectHelper;
import cititool.util.StringHelper;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author zx04741
 */
public class ClientContext {

    private static UserClient currentUser = new UserClient();
    private static Map<String, UserInfo> bufferUserInfo = new ConcurrentHashMap<String, UserInfo>();
    //current session...
//    private static ThreadLocal<String> session = new ThreadLocal<String>();
    private static JTextPane logpane;
    private static ClientContext context;
    private static String userpath;
    private static String defaultPath = "/common";

    private ClientContext() {
    }

    public static ClientContext instance() {
        if (context == null) {
            context = new ClientContext();
        }
        return context;
    }

    public static String getDefaultPath() {

        return userpath + File.separator + defaultPath;
    }

    public static void setCurSocket(Socket socket) {
        currentUser.setSocket(socket);
    }

    public static void setCurClient(UserClient client) {
        currentUser = client;
    }

    public static void setCurUserInfo(UserInfo user) {
        currentUser.setUser(user);
    }

    public static UserClient getCurrentUserClient() {
        return currentUser;
    }

    public static Socket getCurrentSocket() {

        return currentUser.getSocket();
    }

    public static UserInfo getCurrentUserInfo() {
        return currentUser.getUser();
    }

    public static String getUserPath() {

        return userpath;
    }

    public static void setUserPath(String path) {

        userpath = path;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

    }



    public static void setCacheInfo(String username, UserInfo user) {

        bufferUserInfo.put(username, user);

    }

    public static UserInfo getCacheInfo(String username) {

        return bufferUserInfo.get(username);
    }

    public static void registerUser(UserInfo user, Socket socket, JProgressBar bar) {
        try {
            TransProtocol.writeStr(TransProtocol.REG_HEADER, socket);
            TransProtocol.writeObj(user, socket);

            if (!StringHelper.isEmpty(user.getPhotopath())) {
                FileProcesser fp = new FileProcesser();
                if (bar != null) {
                    fp.setProcessBar(bar);
                }
                fp.writeFile(new File(user.getPhotopath()), socket);
            }
        } catch (InterruptedException ex) {
        } catch (IOException ex) {
        }

    }

    public static void setlog(JTextPane log) {
        logpane = log;
    }

    public static void productLog(String str) {

        productLog(str, null);
    }

    public static void productLog(String str, Exception ex) {

        if (logpane != null) {
            try {
                if (ex != null) {
                    if (!StringHelper.isEmpty(str)) {
                        str = str + "," + ex;
                    } else {
                        str = "exception=>" + ex;
                    }
                }
                ComponentHelper.jtpAppendLine(logpane, str);
            } catch (BadLocationException ex1) {
                warnLog("", ex1);
            }
        }
    }

    public static void warnLog(String str, Exception ex) {

        if (logpane != null) {
            try {
                if (ex != null) {
                    if (!StringHelper.isEmpty(str)) {
                        str = str + "," + ex;
                    } else {
                        str = "exception=>" + ex;
                    }
                }
                ComponentHelper.jtpAppendLine(logpane, str, Color.red);
            } catch (BadLocationException ex1) {
                warnLog("", ex1);
            }
        }
    }

    public static String getUserFolder(UserInfo user) {

        return ClientContext.getUserPath() + File.separator + user.getUsername();
    }

    public static String getCurrentUserFolder(){

        return getUserFolder(currentUser.getUser());
    }

    public static void requestFile(String relativePath, Socket s) throws IOException {

       TransProtocol.writeStr(TransProtocol.REQUEST_FILE_H + relativePath, s);
       TransProtocol.writeStr(TransProtocol.ISFILE_H, s);
    }

    public static void requestPic(String relativePath, Socket s) throws IOException {

       TransProtocol.writeStr(TransProtocol.REQUEST_FILE_H + relativePath, s);
       TransProtocol.writeStr(TransProtocol.USERPIC_H, s);
    }
}
