/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.uicomponent;

import cititool.MainApp;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author Administrator
 */
public class ButtonUI {

    private static ResourceMap resources= Application.getInstance(MainApp.class).getContext().getResourceMap(ButtonUI.class);

    public static ResourceMap getButtonResource(){

        return resources;
    }

    public static class CalendarButton extends JButton{

        public CalendarButton(){
            super();
            Dimension d=this.getPreferredSize().getSize();
            ImageIcon imageicon=resources.getImageIcon("calendar.icon");
           Image image=imageicon.getImage();
            BufferedImage buf=new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
            buf.getGraphics().drawImage(image,0,0, d.width, d.height, this);
            this.setIcon(new ImageIcon(buf));
        }
    }

}
