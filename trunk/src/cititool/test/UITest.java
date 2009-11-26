/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.test;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author zx04741
 */
public class UITest extends JFrame{


    public UITest(String title){

        super(title);
        this.setPreferredSize(new Dimension(200, 200));
        final JPanel p=new JPanel();
        p.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        //p.setBounds(0, 0, 200, 200);
        final JButton b=new JButton("add");
        b.setPreferredSize(new Dimension(50, 30));
        
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JButton d=new JButton("del");
                p.add(d);
                p.remove(b);
                p.updateUI();
            }
        });
        p.add(b);
        this.getContentPane().add(p);
    }


 


    public static void main(String args[]){

        EventQueue.invokeLater(new Runnable() {

            public void run() {
               JFrame frame=new UITest("hi..");
               frame.pack();
               frame.setVisible(true);
//               frame.addWindowListener(new WindowAdapter() {
//
//               });
            }
        });
    }
}
