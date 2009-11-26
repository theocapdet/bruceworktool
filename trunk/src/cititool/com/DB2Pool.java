/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.com;

import com.ibm.db2.jcc.DB2ConnectionPoolDataSource;
import java.sql.SQLException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author zx04741
 */
public class DB2Pool extends DBConn {

    private static DB2ConnectionPoolDataSource pool = null;
    //private static ExecutorService executor;

    static {

        pool = new DB2ConnectionPoolDataSource();

    }

    public DB2Pool(String host, int port, String dbname, String username, String pass) {


        pool.setServerName(host);
        pool.setPortNumber(port);
        pool.setDatabaseName(dbname);
        pool.setUser(username);
        pool.setPassword(pass);

        //executor=Executors.newCachedThreadPool();

    }

    public void initConn() {


        try {
            productLog("connecting....");
            conn = pool.getPooledConnection().getConnection();
            productLog("connect successful....");
        } catch (SQLException ex) {
            productLog("DB2 conn error:" + ex.getMessage());
        }
    }


}
