/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.db;

import cititool.chat.server.ServerContext;
import cititool.model.UserInfo;
import cititool.util.StringHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author zx04741
 */
public class ServerDB {

    private final static String DBPATH = "C:\\xmldb\\";
    private static String dbpath = DBPATH;
    private Map<String, Document> docMap;
    private Map<String, List> allRecord;
    private static SAXBuilder sb = new SAXBuilder();

    private Preferences pref= Preferences.userRoot().node("/com/cititoolkit");

    private ServerDB() {
        allRecord = new HashMap<String, List>();
        docMap = new HashMap<String, Document>();
    }
    private static ServerDB db = null;

    public static ServerDB createInstance() {

        if (db == null) {
            db = new ServerDB();
        }
        return db;
    }



    public void startDB() throws IOException, JDOMException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ServerContext.productDBLog("database store folder==>"+dbpath, null);
        File p = new File(dbpath);
        if (!p.exists()) {
            p.mkdirs();
        }
        loadXML(p);
    }

    public List getTableList(Class clz) {
        return allRecord.get(clz.getName());
    }


    public void loadXML(File f) throws JDOMException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (f.exists()  ) {
            if (f.isFile() && f.getName().endsWith(".xml")) {
                Document doc = sb.build(f);
                String className=doc.getRootElement().getAttributeValue("className");
                ServerContext.warnDBLog("load file===>"+f.getPath()+",class==>"+className, null);
                loadXML2Bean(doc, className);
               
            } else if(f.isDirectory()) {
                File[] list = f.listFiles();
                for (int i = 0; i < list.length; i++) {
                    loadXML(list[i]);
                }
            }
        }
    }

    public void loadXML2Bean(Document doc, String clzName) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class clz = Class.forName(clzName);
        List record = new ArrayList();
        List<Element> list = doc.getRootElement().getChildren();

        for (Element e : list) {
            List<Attribute> atts = e.getAttributes();
            Object o = clz.getConstructor().newInstance();
            for (Attribute a : atts) {
                String attname = a.getName();
                String attval = a.getValue();
                Method m = clz.getDeclaredMethod(StringHelper.getFieldMethod("set", attname), String.class);
                m.invoke(o, attval);
            }
            record.add(o);
        }
        docMap.put(clzName, doc);
        allRecord.put(clzName, record);
    }

    private int containsUser(UserInfo user,List container){
        for(int i=0;i<container.size();i++){
            UserInfo o=(UserInfo)container.get(i);
            if(o.getUsername().equals(user.getUsername()))
                return i;
        }
        return -1;
    }

    public void flushXML(Document doc) throws IOException{
        File f=new File(doc.getBaseURI().substring(6));
        XMLOutputter out=new XMLOutputter();
        out.output(doc, new FileOutputStream(f));
    }

    public int addorUpdateRecord(UserInfo user) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        
        Class clz = UserInfo.class;
        List list = (List) allRecord.get(clz.getName());
        int index=containsUser(user, list);
        
        if(index==-1){
            list.add(user);
        }else{
            list.set(index, user);
        }
        allRecord.put(clz.getName(), list);

        Document doc = docMap.get(clz.getName());
        System.out.println(doc.getRootElement().getName());
        Field[] fields = clz.getDeclaredFields();
        Element e =null;
        if(index==-1)
            e= new Element("record");
        else
            e= (Element)doc.getRootElement().getChildren().get(index);
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            Method m = clz.getDeclaredMethod(StringHelper.getFieldMethod("get", f.getName()), null);
            String v = StringHelper.null2String(m.invoke(user, null));
            Attribute a = new Attribute(f.getName(), v);
            e.setAttribute(a);
        }
        if(index==-1)
        doc.getRootElement().addContent(e);        
        flushXML(doc);
        docMap.put(clz.getName(), doc);

        return index;
    }


    /**
     * @return the dbfile
     */
    public String getDbpath() {
        return dbpath;
    }

    /**
     * @param dbfile the dbfile to set
     */
    public void setDbpath(String dbfile) {
        this.dbpath = dbfile;
    }
}
