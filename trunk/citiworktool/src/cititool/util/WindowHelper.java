/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.util;

import java.awt.Component;
import java.awt.Dimension;

/**
 *
 * @author zx04741
 */
public class WindowHelper {

    public static void showCenter(Component com){



        Dimension d = com.getToolkit().getScreenSize();
        com.setLocation((d.width - com.getWidth()) / 2,
                (d.height - com.getHeight()) / 2);
        if(com.isVisible())
            com.setVisible(false);
        else
            com.setVisible(true);

    }

    

    


}
