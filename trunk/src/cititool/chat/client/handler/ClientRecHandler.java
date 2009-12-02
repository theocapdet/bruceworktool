/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client.handler;

import cititool.chat.client.ClientContext;
import cititool.chat.protocol.TransProtocol;
import cititool.util.ComponentHelper;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author zx04741
 */
public class ClientRecHandler extends Thread {

    private Socket socket;
    private JTextPane contentPane;
    private ExecutorService pool = Executors.newCachedThreadPool();

    public ClientRecHandler(Socket socket) {
        this.setDaemon(true);
        this.socket = socket;
    }

    public void setPrintPane(JTextPane contentPane){
        this.contentPane=contentPane;
    }

    public void run() {
        ClientContext.productLog ("recieve thread start...");
        while (!socket.isClosed()) {
            try {
                System.out.println("haha");
                Object content = TransProtocol.getObject(socket).toString();
                System.out.println("gaga...");
                if (content instanceof String) {
                    String t = content.toString();
                    if (t.startsWith(TransProtocol.TALK_REC_H)) {
                        String p[] = t.substring(TransProtocol.TALK_REC_H.length()).split(TransProtocol.SPLIT);
                        String user = p[0];
                        String talk = p[1];
                        ComponentHelper.jtpAppendLine(contentPane, user+":"+talk);
                    }
                } else {
                    /*
                    pool.execute(new Runnable() {
                        public void run() {
                            
                        }
                    });*/
                }

            } catch (BadLocationException ex) {
                ClientContext.warnLog("ClientRecThread() contentPane error:", ex);
            } catch (IOException ex) {
                ClientContext.warnLog("ClientRecThread() recv content error:", ex);
            } catch (ClassNotFoundException ex) {
                ClientContext.warnLog("ClientRecThread() recv content error:", ex);
            }
        }
        ClientContext.productLog ("recieve thread end...");
    }
}
