/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client.handler;

import cititool.chat.client.ClientContext;
import cititool.chat.client.UI.UserInfoFrame;
import cititool.chat.client.listener.FileProcesser;
import cititool.chat.model.SystemConstants.SystemBlank;
import cititool.chat.model.SystemConstants.SystemColor;
import cititool.chat.protocol.TransProtocol;
import cititool.model.UserInfo;
import cititool.util.ComponentHelper;
import cititool.util.ReflectHelper;
import java.io.IOException;
import java.net.Socket;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author zx04741
 */
public class ClientRecHandler extends Thread {

    private Socket socket;
    //private ExecutorService pool = Executors.newCachedThreadPool();
    private UserInfoFrame frame;
    private ClientOperation oper;
    private String userpic = "";

    public ClientRecHandler(Socket socket, UserInfoFrame frame) {
        this.setDaemon(true);
        this.socket = socket;
        this.frame = frame;
    }

    public void setOperation(ClientOperation oper) {
        this.oper = oper;
    }

    public void run() {
        ClientContext.productLog("recieve thread start...");
        while (!socket.isClosed()) {
            try {
                Object content = TransProtocol.getObject(socket).toString();

                if (content instanceof String) {
                    String t = content.toString();
                    if (t.startsWith(TransProtocol.TALK_REC_H)) {
                        String p[] = t.substring(TransProtocol.TALK_REC_H.length()).split(TransProtocol.SPLIT);
                        String user = p[0];
                        String talk = p[1];
                        ComponentHelper.chatDefined(frame.getTab().getCurrentChatPane(), user, SystemColor.BROWN_GREEN, true);
                        ComponentHelper.chatDefined(frame.getTab().getCurrentChatPane(), SystemBlank.CONTENT_START + talk, SystemColor.DEFAULT, false);
                        if(!frame.isFocused()){
                            frame.setVisible(true);
                        }


                    } //load userinfo
                    else if (t.startsWith(TransProtocol.USERNAME_HEADER)) {
                        UserInfo user = (UserInfo) TransProtocol.getObject(socket);
                        ClientContext.setCacheInfo(user.getUsername(), user);
                        System.out.println(ReflectHelper.getBeanStr(user));
                        oper.unlock();
                    } else if (t.startsWith(TransProtocol.USERLIST_HEADER)) {
                        Object[] onlineuser = (Object[]) TransProtocol.getObject(socket);
                        ClientContext.productLog("onlineuser size==>" + onlineuser.length, null);
                        DefaultMutableTreeNode root = new DefaultMutableTreeNode("users online");
                        for (int i = 0; i < onlineuser.length; i++) {
                            String username = onlineuser[i].toString();
                            DefaultMutableTreeNode node = new DefaultMutableTreeNode(username);
                            root.add(node);
                        }
                        DefaultTreeModel tree = new DefaultTreeModel(root);
                        frame.getUserTree().setModel(tree);
                        frame.getUserTree().updateUI();
                    } // load pic
                    else if (t.startsWith(TransProtocol.USERPIC_H)) {
                        System.out.println("in...");
                        FileProcesser fp = new FileProcesser();
                        fp.readFile(userpic, socket);
                        oper.unlock();
                    }//load file
                    else if (t.startsWith(TransProtocol.ISFILE_H)) {
                    }
                }
            } catch (BadLocationException ex) {
                ClientContext.warnLog("ClientRecThread() contentPane error:", ex);
                break;
            } catch (IOException ex) {
                ClientContext.warnLog("ClientRecThread() recv content error:", ex);
                break;
            } catch (ClassNotFoundException ex) {
                ClientContext.warnLog("ClientRecThread() recv content error:", ex);
                break;
            }
        }
        ClientContext.productLog("recieve thread end...");
    }

    /**
     * @return the userpic
     */
    public String getUserpic() {
        return userpic;
    }

    /**
     * @param userpic the userpic to set
     */
    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }
}
