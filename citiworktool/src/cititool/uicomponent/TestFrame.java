/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.uicomponent;

import cititool.util.WindowHelper;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author zx04741
 */
public class TestFrame extends JFrame {

    public TestFrame(String t) {

        super(t);


        
        JTabbedPane tab = new JTabbedPane();
        JPanel newPanel=new JPanel();
        //this.getClass().getResource("/")
        String url=this.getClass().getResource("/").toString();
        url=url.substring(6)+"cititool/UI/resources/UI/Button/tabcloseinactive.gif";

        JLabel label=new JLabel((new ImageIcon(url)));

        label.setName("title");

        ImageIcon icon=new ImageIcon("tabcloseinactive.gif");
        //JButton button=new JButton(icon);
        
       

        tab.addTab("haha", icon, newPanel);
//        JButton b=new JButton();
//        b.setText("ddd");

        this.getContentPane().add(tab);
    }

    public static void main(String args[]){

        JFrame frame=new TestFrame("tabclose");
        frame.setPreferredSize(new Dimension(200, 200));
        frame.pack();
        WindowHelper.showCenter(frame);
    }
}
