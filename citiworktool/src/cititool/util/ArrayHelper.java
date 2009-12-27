/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.util;

/**
 *
 * @author zx04741
 */
public class ArrayHelper {

    public static boolean isArrayContains(String[] str, String keyword) {

        for (int i = 0; i < str.length; i++) {
            if (str[i].contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArrayHave(String[] str, String keyword) {
        for (int i = 0; i < str.length; i++) {
            if (str[i].equalsIgnoreCase(keyword)) {
                return true;
            }
        }
        return false;
    }
}
