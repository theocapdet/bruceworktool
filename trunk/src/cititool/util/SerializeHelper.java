/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zx04741
 */
public class SerializeHelper {

    public static Object deepClone(Object o){
        Object copy=null;
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();
            ObjectInputStream obj = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            copy= obj.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SerializeHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SerializeHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
                
            } catch (IOException ex) {
                Logger.getLogger(SerializeHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            return copy;
        }
    }
    
    static class A implements  Serializable{
    
        String id;
        String val;
        
    }

    public static void main(String args[]){

        A a=new A();
        a.id="gg";
        A b=(A)deepClone(a);
        System.out.println(b.id);
    }

}
