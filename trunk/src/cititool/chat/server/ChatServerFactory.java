/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server;

import cititool.chat.model.SystemConstants;
import cititool.chat.server.handler.MainServer;
import cititool.util.DateHelper;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Administrator
 */
public class ChatServerFactory {

    private List<MainServer> servers = new Vector<MainServer>();
    private Map<String, Integer> serverNames = new ConcurrentHashMap<String, Integer>();
    private static ChatServerFactory factory = null;

    private ChatServerFactory() {
    }

    public static ChatServerFactory createInstance() {

        if (factory == null) {
            factory = new ChatServerFactory();
        }
        return factory;
    }

    public MainServer getServerByName(String serverName) {
        Integer index = 0;
        if ((index = serverNames.get(serverName)) != null) {
            MainServer server = servers.get(index);
            return server;
        }
        return null;
    }

    public void loadServerXML(File f) {

        try {
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(f);
            Element root = doc.getRootElement();
            List list = root.getChildren("server");

            for (int i = 0; i < list.size(); i++) {
                Element ele = (Element) list.get(i);
                String createDatetime = ele.getAttributeValue("createdatetime");
                String lastModifyDatetime = ele.getAttributeValue("lastmodifydatetime");
                String serverName = ele.getChildText("servername");
                String port = ele.getChildText("port");
                String desc = ele.getChildText("description");
                ServerContext.productServerLog("xml load server :" + serverName + "==>port:" + port + ",createDatetime:" + createDatetime + ",lastModifyDatetime:" + lastModifyDatetime + ",description:" + desc, null);
                MainServer server = new MainServer(serverName, Integer.parseInt(port));
                server.setDescription(desc);
                server.setCreatetime(createDatetime);
                server.setLastmodifytime(lastModifyDatetime);

                server.buildServer();
                servers.add(server);
                serverNames.put(serverName, servers.size() - 1);
            }
        } catch (JDOMException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MainServer addServer(String serverName, Integer port, String description) {

        /**
         * add server
         */
        MainServer server = new MainServer(serverName, port);
        server.setDescription(description);
        server.setCreatetime(DateHelper.getCurDateTime());
        server.buildServer();
        servers.add(server);
        serverNames.put(serverName, servers.size() - 1);
        return server;
    }

    public MainServer updateServer(String newServerName, String oldServerName, Integer port, String description) {
        /**
         * update server
         */
        int index = serverNames.get(oldServerName);
        MainServer server = getServerByName(oldServerName);
        if (server.getStatus() != SystemConstants.Status.PREPARED) {
            server.stopServer(false);
        }
        server.setServerName(newServerName);
        server.setPort(port);
        server.setDescription(description);
        server.setLastmodifytime(DateHelper.getCurDateTime());
        server.buildServer();
        serverNames.remove(oldServerName);
        serverNames.put(newServerName, index);
        servers.set(index, server);
        return server;

    }

    public void startServer(List<MainServer> servers) {

        for (MainServer server : servers) {
            if (server.getStatus().equals(SystemConstants.Status.STOPED) || server.getStatus().equals(SystemConstants.Status.PREPARED)) {
                server.startServer();
            }
        }
    }

    public void shiftServer(MainServer server) {
        if (server.getStatus().equals(SystemConstants.Status.STOPED) || server.getStatus().equals(SystemConstants.Status.PREPARED)) {
            server.startServer();
        } else if (server.getStatus().equals(SystemConstants.Status.STARTED)) {
            server.stopServer(false);
        }

    }

    public void stopServer(List<MainServer> servers) {
        for (MainServer server : servers) {
            if (server.getStatus().equals(SystemConstants.Status.STARTED)) {
                server.stopServer(false);
            }
        }
    }

    public void delServer(List<MainServer> servers) {
        for (int i = 0; i < servers.size(); i++) {
            MainServer server = servers.get(i);
            if (server.getStatus().equals(SystemConstants.Status.STARTED)) {
                server.stopServer(true);
            }
            serverNames.remove(server.getServerName());
            this.servers.remove(server);
        }
    }

    public void startServer(String serverName) {
        MainServer server = getServerByName(serverName);
        server.start();

    }

    public List<MainServer> getServers() {

        return servers;
    }
}
