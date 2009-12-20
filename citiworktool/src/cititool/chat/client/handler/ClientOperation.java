/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client.handler;

import cititool.chat.client.ClientContext;
import cititool.chat.client.UI.UserInfoFrame;
import cititool.chat.protocol.TransProtocol;
import cititool.model.UserInfo;
import cititool.util.StringHelper;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Administrator
 */
public class ClientOperation {

    private Socket socket;
    private UserInfoFrame frame;
    private ClientRecHandler handler;
    private Semaphore sig = new Semaphore(1);

    public ClientOperation(Socket socket, UserInfoFrame frame) {
        this.socket = socket;
        this.frame = frame;
    }

    public void setHandler(ClientRecHandler handler) {
        this.handler = handler;
    }

    public void loadTotalUser(String username) throws IOException, ClassNotFoundException {
        try {
            lock();
        } catch (InterruptedException ex) {
            ClientContext.productLog("loadTotalUser ", ex);
        }
        loadUserInfotFromServer(username);

        try {
            lock();
        } catch (InterruptedException ex) {
            ClientContext.productLog("loadTotalUser ", ex);
        }
        UserInfo user = ClientContext.getCacheInfo(username);
        //load picture
        String folder = ClientContext.getUserPath() + File.separator + username;
        String path = folder + File.separator + StringHelper.getFileName(user.getPhotopath());

        System.out.println("request pic path==>" + path);
        File f = new File(path);
        if (!f.exists()) {
            loadFileFromServer(folder, username + File.separator + StringHelper.getFileName(user.getPhotopath()));
        } else {
            unlock();
        }

        System.out.println("request pic over..");
    }

    public void loadUserInfotFromServer(String username) throws IOException, ClassNotFoundException {
        if (ClientContext.getCacheInfo(username) != null) {
            unlock();
            return;
        }

        TransProtocol.requestUserInfo(username, socket);
    }

    public void loadFileFromServer(String folder, String relativePath) throws IOException, ClassNotFoundException {
        ClientContext.requestFile(relativePath, socket);
        File f = new File(folder);
        if (!f.exists()) {
            f.mkdirs();
        }
        handler.setUserpic(folder);
    }

    public void openUserPane(String username) throws IOException, ClassNotFoundException {
        loadTotalUser(username);
        UserInfo userinfo = ClientContext.getCacheInfo(username);
        if (userinfo != null) {
            frame.getTab().addPanel(userinfo);
            //get userinfo socket
        }

    }

    public void loadUserListFromServer(String curuser) throws IOException, ClassNotFoundException {

        TransProtocol.requestUserList(socket);
    }

    public void lock() throws InterruptedException {
        sig.acquire();
    }

    public void unlock() {
        sig.release();
    }
}
