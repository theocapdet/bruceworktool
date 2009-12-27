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
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import static cititool.util.ComponentHelper.TreeNodeSearcher;

/**
 *
 * @author zx04741
 */
public class ClientRecHandler extends Thread {

    private Socket socket;
   private ExecutorService pool = Executors.newFixedThreadPool(20);
    private UserInfoFrame frame;
    private ClientOperation oper;
    private String userpic = "";
    private BlockingQueue<RecieveFileJob> queue=new ArrayBlockingQueue<RecieveFileJob>(20);

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
                        JTextPane cur = frame.getTab().getCurrentChatPane();
                        if (cur == null) {
                            oper.openUserPane(user);
                            cur = frame.getTab().getCurrentChatPane();
                        }
                        ComponentHelper.chatDefined(cur, user, SystemColor.BROWN_GREEN, true);
                        ComponentHelper.chatDefined(cur, SystemBlank.CONTENT_START + talk, SystemColor.DEFAULT, false);
                        if (!frame.isFocused()) {
                            frame.setVisible(true);
                        }

                    } //load userinfo
                    else if (t.startsWith(TransProtocol.USERNAME_HEADER)) {

                        UserInfo user = (UserInfo) TransProtocol.getObject(socket);
                        ClientContext.setCacheInfo(user.getUsername(), user);
                        ClientContext.productLog("load user information"+ user.getUsername());
//                        System.out.println(ReflectHelper.getBeanStr(user));
                        oper.unlock();
                    } 
                    //load user list
                    else if (t.startsWith(TransProtocol.USERLIST_HEADER)) {
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
                        FileProcesser fp = new FileProcesser();
                        fp.readFile(userpic, socket);
                        oper.unlock();
                    }//load file
                    else if (t.startsWith(TransProtocol.ISFILE_H)) {
                    } else if (t.startsWith(TransProtocol.ONLINE_H)) {                        
                        String onuser=t.substring(TransProtocol.ONLINE_H.length());
                        ClientContext.productLog(onuser+" log on!");
                        DefaultTreeModel tree =(DefaultTreeModel)frame.getUserTree().getModel();
                        DefaultMutableTreeNode root=(DefaultMutableTreeNode) tree.getRoot();
                        tree.insertNodeInto(new DefaultMutableTreeNode(onuser) , root, root.getChildCount());
                        tree.reload();
                    }else if(t.startsWith(TransProtocol.OFFLINE_H)){
                        String offuser=t.substring(TransProtocol.OFFLINE_H.length());
                        ClientContext.productLog(offuser+" log off!");
                        DefaultTreeModel tree =(DefaultTreeModel)frame.getUserTree().getModel();
                        DefaultMutableTreeNode root=(DefaultMutableTreeNode) tree.getRoot();
                        TreeNodeSearcher search=new TreeNodeSearcher(offuser);
                        DefaultMutableTreeNode node= search.searchTree(root);
                        tree.removeNodeFromParent(node);
                        tree.reload();
                    }
                    //pop up msg dialog
                    else if(t.startsWith(TransProtocol.POPMSG_H)){
                        oper.popupMsg(t);
                    }
                    //
                    else if(t.startsWith(TransProtocol.READY_TRANSFER_FH)){
                        String filename=t.substring(TransProtocol.READY_TRANSFER_FH.length());
                        JPanel panel=frame.getWorkArea();
                        RecieveFileJob job=new RecieveFileJob(filename, panel);
                        try {
                            queue.put(job);
                        } catch (InterruptedException ex) {
                            ClientContext.warnLog("receive job into jobqueue error: ", ex);
                        }
                    }
                    else if(t.startsWith(TransProtocol.START_TRANSFER_FH)){
                        try {                          
                            RecieveFileJob job = queue.take();
                            pool.execute(job);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ClientRecHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
