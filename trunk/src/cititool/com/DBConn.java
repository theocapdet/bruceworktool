/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.com;

import cititool.util.ComponentHelper;
import cititool.util.NumberHelper;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JTextArea;

/**
 *
 * @author zx04741
 */
public class DBConn {

    private String driverClass = "";
    private String url = "";
    protected Connection conn;
    private String user;
    private String pass;
    private JTextArea log;
    private volatile  boolean f=false;

    public void setLog(JTextArea log) {
        this.log = log;
        log.setForeground(Color.blue);
    }

    public DBConn(String className, String url, String user, String pass) {

        this.driverClass = className;
        this.url = url;
        this.user = user;
        this.pass = pass;


    }

    protected DBConn() {
    }

    public void initConn() {
        try {
            Class.forName(driverClass).newInstance();
            conn = DriverManager.getConnection(url, user, pass);
        } catch (InstantiationException ex) {
            productLog("intiConn error ,InstantiationException:" + ex.getMessage());
        } catch (IllegalAccessException ex) {
            productLog("intiConn error,IllegalAccessException:" + ex.getMessage());
        } catch (SQLException ex) {
            productLog("intiConn error,SQLException:" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            productLog("intiConn error,ClassNotFoundException:" + ex.getMessage());
        }
    }

    protected void productLog(String str) {
        if (log != null) {
            ComponentHelper.jtaAppendLine(log, str);
        }
    }

    protected void productWarn(Exception str) {

        if (log != null) {
            log.setForeground(Color.red);
            ComponentHelper.jtaAppendLine(log, str.toString());
            log.setForeground(Color.blue);

        }
    }

    public boolean executeSql(String sql) {

        boolean isSuccess = false;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException ex) {
            productWarn(ex);

        } finally {
            if (ps != null) {
                try {
                    isSuccess = ps.execute();
                    ps.close();

                } catch (SQLException ex) {
                    productWarn(ex);
                }
            }
            return isSuccess;
        }

    }

    public ResultSet query(String sql) {
        f=false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

        } catch (SQLException ex) {
             f=true;
            productWarn(ex);

        } finally {
           return rs;

        }
    }

    private List getMeta(ResultSet rs) {
        List list = new ArrayList();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int c = meta.getColumnCount();

            for (int i = 1; i <= c; i++) {
                list.add(meta.getColumnName(i));
            }
        } catch (SQLException ex) {
            productWarn(ex);
        } finally {
            return list;
        }

    }

    public int getNumber(String sql) {
        int i = 0;
        if ((i = sql.indexOf("order by")) >= 0) {
            sql = sql.substring(0, i);
        }
        String countSql = "select count(*) C from (" + sql + ") countTab ";
        Map m = getFirstRecord(countSql);
        return NumberHelper.string2Int(m.get("C"), 0);
    }

    public Map getFirstRecord(String sql) {
        Map map = new HashMap();
        try {
            ResultSet rs = query(sql);
            List list = getMeta(rs);

            if (rs.next()) {
                for (int i = 0, len = list.size(); i < len; i++) {
                    String t = list.get(i).toString();
                    map.put(t, rs.getString(t));
                }
            }
        } catch (SQLException ex) {
            productWarn(ex);
        } finally {

            return map;
        }

    }

    public List getRecord(String sql) {
        List r = new ArrayList();
        try {
            ResultSet rs = query(sql);
            List m = getMeta(rs);
            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 0, l = m.size(); i < l; i++) {
                    String t = m.get(i).toString();
                    map.put(t, rs.getString(t));
                }
                r.add(m);
            }
        } catch (SQLException ex) {
            productWarn(ex);
        } finally {
            return r;
        }
    }

    public Map<String, Vector> getRecordTabModel(String sql) {
        Map<String, Vector> r = new HashMap<String, Vector>();
        Vector header;
        Vector d = new Vector();
        try {
            ResultSet rs = query(sql);
            List m = getMeta(rs);
            header = new Vector(m);
            while (rs.next()) {
                Vector v = new Vector();
                for (int i = 0, l = m.size(); i < l; i++) {
                    String t = m.get(i).toString();
                    v.add(rs.getString(t));
                }
                d.add(v);
            }
            r.put("header", header);
            r.put("data", d);
        } catch (SQLException ex) {
            productWarn(ex);
        } finally {
            return r;
        }
    }

    public boolean test() {
        String sql = "select count(*) from  SYSIBM.SYSCOLUMNS";
        return executeSql(sql);

    }

    public void closeConn() {

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                productWarn(ex);
            }
        }
    }

    public boolean exeError(){

        return f;
    }

    public static void main(String args[]){

        String username = "APOISR2";
        String pass = "itociti1";
        String database = "ST06DBTG";
        String host = "tdcdbtg006.appc.sg.citicorp.com";
        int port = 8000;
        String className = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2://" + host + ":" + port + "/" + database;
        DBConn con  = new DBConn(className, url, username, pass);
        con.initConn();

        String sql="SELECT * FROM GNXFOIAX.application_txn where CTRL_1=18 and CTRL_3='13' and application_id>'0000000009999' and application_id<'0000000012000'  order by application_id ";
        int c=con.getNumber(sql);
        System.out.println("count number:"+c);
    }
}
