/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.server.handler;

import cititool.chat.model.SystemConstants;
import cititool.chat.protocol.TransProtocol;
import cititool.chat.server.ServerContext;
import cititool.util.ComponentHelper;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class MainServer extends Thread {

    private String serverName;
    private Integer port;
    private Integer status;
    private ServerSocket server;
    private JLabel log;
    private String description;
    private String createtime;
    private String lastmodifytime;
    private boolean suspend;

    private JTable serverTab;
    //user connection server
    private  Map<String, SessionServer> pool = new ConcurrentHashMap<String, SessionServer>();
    //register server 
    private  RegisterServer regServer;
    //login server
    private  LoginServer loginServer;

    public void updateTableModel() {

        List<MainServer> servers = ServerContext.getServerFactory().getServers();
        DefaultTableModel model = (DefaultTableModel) serverTab.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            model.removeRow(0);
        }

        for (int i = 0; i < servers.size(); i++) {
            MainServer server = servers.get(i);
            String servername = server.getServerName();
            Integer port = server.getPort();
            String status = SystemConstants.Status.toString(server.getStatus());


            Vector v = new Vector();
            v.add(servername);
            v.add(port);
            v.add(status);
            v.add(ServerContext.getOnlineUserInfo(servername).size());
            model.addRow(v);
        }
        serverTab.setModel(model);
        serverTab.updateUI();
    }

    public void setView(JTable serverTab){

        this.serverTab=serverTab;
    }

    public MainServer(String serverName, Integer port) {
        this.serverName = serverName;
        this.port = port;
        this.description = "";
        this.createtime = "";
        this.lastmodifytime = "";
        this.setDaemon(true);
        suspend = false;
        statusOut(SystemConstants.Status.PREPARED);

    }

    public synchronized void startServer() {
        buildServer();
        if (getState() != Thread.State.NEW) {
            this.notify();
            statusOut(SystemConstants.Status.STARTED);
        } else {
            this.start();
        }
        updateTableModel();
    }

    public void buildServer() {

        try {
            if (server == null || (server != null && server.isClosed())) {
                server = new ServerSocket(port);
            }
        } catch (IOException ex) {
            ServerContext.warnServerLog(serverName+"build error:", ex);
        }

    }

    public void stopServer(boolean isTerminated) {
        if (server != null) {
            try {
                server.close();
                statusOut(SystemConstants.Status.STOPING);
                while (!server.isClosed()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                statusOut(SystemConstants.Status.STOPED);
                if (!isTerminated) {
                    suspend = true;
                }
            } catch (IOException ex) {
                Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateTableModel();
        }
    }

    public SessionServer getUserServer(String username) {

        return pool.get(username);
    }

    public synchronized  void freshpool() {
        for (Iterator<SessionServer> iter = pool.values().iterator(); iter.hasNext();) {
            SessionServer t = iter.next();
            if (t.getState() == Thread.State.TERMINATED) {
                iter.remove();
            }
        }
    }

    private  void scanpool() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                freshpool();
            }
        };
        timer.schedule(task, 500, 1000 * 300);
    }

    public  Map getOnLineUser() {
        return pool;
    }

    @Override
    public synchronized void run() {
        try {
            //login accept thread...

            statusOut(SystemConstants.Status.STARTED);
             updateTableModel();
           // scanpool();
            Socket socket = null;
            
            if (regServer == null) {
                regServer = new RegisterServer();
            }
            regServer.start();
            
            while (this.isAlive()) {
                if (suspend) {
                    this.wait();
                }                
                try {
                    socket = server.accept();
                    Object content = TransProtocol.readObject(socket);
                    if (content instanceof String) {
                        String t = content.toString();
                       
                        if (t.equals(TransProtocol.TESTH)) {
                            // test connection...
                            ServerContext.productServerLog("ip==>"+socket.getInetAddress().getHostAddress()+"  test==>", null);
                            TransProtocol.writeStr(SystemConstants.TESTSUC + "", socket);
                        } //registry server
                        
                        else if (t.startsWith(TransProtocol.REG_HEADER)) {
                            regServer.setSocket(socket);                           
                        }
                        else if (t.startsWith(TransProtocol.LOGIN_HEADER)) {
                            String[] part = t.substring(1).split(TransProtocol.SPLIT);
                            if (ServerDataHandler.testMatchLogin(part[0], part[1], serverName) == SystemConstants.LOGON) {
                               TransProtocol.writeStr(SystemConstants.LOGON + "", socket);
                                ServerContext.productServerLog(part[0]+"==>login", null);
                                SessionServer recv = new SessionServer(part[0], socket);
                                pool.put(part[0], recv);
                                recv.setPool(pool);
                                recv.start();
                            } else {
                                TransProtocol.writeStr(SystemConstants.NOAUTHORIZE + "", socket);
                            }
                        }
                    }
                } catch (IOException ex) {
                     //ServerContext.productServerLog("server socket exception", ex);
                    //System.out.println("server socket exception" + ex.getMessage());
                }
            }

        } catch (InterruptedException ex) {
             ServerContext.warnServerLog("server main thread error:", ex);
        } catch (ClassNotFoundException ex) {
             ServerContext.warnServerLog("server main thread error:", ex);
        }
    }

    private void statusOut(final Integer status) {
        setStatus(status);
        if (log != null) {
            ComponentHelper.labelOutput(log, serverName);
        }
    }

    public void setLogCom(JLabel l) {
        log = l;
    }

    /**
     * @return the serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * @param serverName the serverName to set
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the createtime
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime the createtime to set
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    /**
     * @return the lastmodifytime
     */
    public String getLastmodifytime() {
        return lastmodifytime;
    }

    /**
     * @param lastmodifytime the lastmodifytime to set
     */
    public void setLastmodifytime(String lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }
}
