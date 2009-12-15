/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.util;

import cititool.model.StrTree;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zx04741
 */
public class JarNodeHelper {

   public static StrTree jarFileToModel(String filepath){
       StrTree tree=new StrTree();
        try {
            JarFile jarfile = new JarFile(filepath);
            for (Enumeration<JarEntry> enums = jarfile.entries(); enums.hasMoreElements();) {
                JarEntry entry = enums.nextElement();
                tree.addNode(entry.getName());
            }
        } catch (IOException ex) {
            Logger.getLogger(JarNodeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            return tree;
        }
       
   }

   public static void searchClassName(String className,String filepath,List jarFile){
       
        try {
            JarFile jarfile = new JarFile(filepath);
            int index=0;
            if( (index=className.lastIndexOf(".class"))>-1)
                className=className.substring(0,index);
            for (Enumeration<JarEntry> enums = jarfile.entries(); enums.hasMoreElements();) {
                JarEntry entry = enums.nextElement();
                String path=entry.getName();
                if(path.endsWith(".class")){
                    path=path.substring(0,path.indexOf(".class"));
                    path=path.replaceAll("/", ".");
                    String clzName=path.substring(path.lastIndexOf(".")+1);
                   
                    if(className.equals(path) || clzName.equals(className)){
                        jarFile.add(filepath);
                        return;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(JarNodeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
   }



   private static void test(StrTree tree,String id){
        List list=tree.getChildren(id);
        if(list.size()>0){        
            for(int i=0;i<list.size();i++){
                String s=(String)list.get(i);
                test(tree,s);
            }

        }else{
           System.out.print(id+"$"+tree.getParentNode(id)+"\r\n");
        }
   }
 

    public static void main(String args[]){

         StrTree t=jarFileToModel("G:/server/apache-tomcat-6.0.18/lib/catalina-ant.jar");
         test(t,t.getRootNode());
    }

}
