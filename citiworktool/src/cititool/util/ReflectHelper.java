/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zx04741
 */
public class ReflectHelper {

    public static String getBeanStr(Object o) {
        StringBuilder sb = new StringBuilder();
        try {
            Class clz = o.getClass();
            Field[] fs = clz.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                Method m = clz.getDeclaredMethod(StringHelper.getFieldMethod("get", f.getName()), null);
                sb.append( f.getName()+":" +StringHelper.null2String(m.invoke(o, null)));
                if (i != fs.length - 1) {
                    sb.append(",");
                }

            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflectHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ReflectHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ReflectHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ReflectHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }
}
