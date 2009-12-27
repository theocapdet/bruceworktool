/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.util;

import cititool.chat.protocol.TransProtocol;
import cititool.chat.server.ServerContext;
import cititool.chat.server.handler.SessionServer;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;

/**
 *
 * @author zx04741
 */
public class ServerUtils {

    public static void sendAll(String header,String str, Map<String, SessionServer> pool, String key) throws IOException {
        for (Iterator<String> iter = pool.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            if (name.equals(key)) {
                continue;
            }
            SessionServer server = pool.get(name);
            Socket ts = server.getSocket();
            TransProtocol.writeStr(header + str, ts);
        }
    }

    public static void remindAll(String header, Map<String, SessionServer> pool, String key, String title, String content) throws IOException {
        for (Iterator<String> iter = pool.keySet().iterator(); iter.hasNext();) {

            String name = iter.next();
            if (name.equals(key)) {
                continue;
            }
            SessionServer server = pool.get(name);
            Socket ts = server.getSocket();
            TransProtocol.writeStr(header + title, ts);
            HTMLDocument doc = new HTMLDocument();
            try {
                doc.insertString(0, content, null);
            } catch (BadLocationException ex) {
                ServerContext.warnServerLog(null, ex);
            }
            TransProtocol.writeObj(doc, ts);
        }
    }

    public static void remindAll(String header, Map<String, SessionServer> pool, String key, String title, Document doc) throws IOException {
        for (Iterator<String> iter = pool.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            if (name.equals(key)) {
                continue;
            }
            SessionServer server = pool.get(name);
            Socket ts = server.getSocket();
            TransProtocol.writeStr(header + title, ts);
            TransProtocol.writeObj(doc, ts);
        }
    }
}
