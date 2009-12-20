/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.uicomponent;

import cititool.MainApp;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author zx04741
 */
public class SqlTabDef {

    private JTabbedPane tabbedPane;
    private JButton addTabButton;
    private ImageIcon incloseIcon;
    private ImageIcon closeIcon;
    private int tabCounter = 0;

    public SqlTabDef(JTabbedPane tabbedPane, JButton addBtn) {

        this.tabbedPane = tabbedPane;
        this.addTabButton = addBtn;

        ResourceMap resourceMap = Application.getInstance(MainApp.class).getContext().getResourceMap(this.getClass());
        incloseIcon = resourceMap.getImageIcon("tabinactive.icon");
        closeIcon = resourceMap.getImageIcon("tabactive.icon");

    }

    private void removeEvent(JPanel newPanel, String title) {
        JScrollPane sp = (JScrollPane) newPanel.getComponent(0);
        JTextArea ta = (JTextArea) sp.getViewport().getComponent(0);
        if (!ta.getText().trim().equals("")) {
            int ok = JOptionPane.showConfirmDialog(newPanel, "save text " + title + " ?");
            if (ok == JOptionPane.OK_OPTION) {
                JFileChooser fc = new JFileChooser();
                fc.setSelectedFile(new File(fc.getCurrentDirectory().getPath() + File.separator + title + ".txt"));
                if (fc.showSaveDialog(newPanel) == JFileChooser.SAVE_DIALOG) {
                    FileOutputStream fos = null;
                    try {
                        File f = fc.getSelectedFile();
                        fos = new FileOutputStream(f);
                        fos.write(ta.getText().trim().getBytes());
                        fos.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(SqlTabDef.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(SqlTabDef.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } else if (ok == JOptionPane.NO_OPTION) {
                int curCom = tabbedPane.getSelectedIndex();
                tabbedPane.removeTabAt(curCom);
                tabbedPane.setSelectedIndex(curCom - 1);
            }
        } else {
            int curCom = tabbedPane.getSelectedIndex();
            tabbedPane.removeTabAt(curCom);
            tabbedPane.setSelectedIndex(curCom - 1);
        }

    }

    public void addPanel(final JPanel newPanel) {

        /**
         * jdk6 GroupLayout.Group can't support the serizable ,so i can't use the deepclone in layout...
         */
//            final JPanel clonepanel = (JPanel) SerializeHelper.deepClone(panel);
        //tabclosebuttonactive.icon
        JPanel tab = new JPanel();
        tab.setLayout(new GridBagLayout());
        tab.setOpaque(false);
        final JLabel tablabel = new JLabel("sql" + (++tabCounter)+"  ");
        final JLabel iconlabel = new JLabel(incloseIcon);
        iconlabel.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                removeEvent(newPanel, tablabel.getText().trim());
            }

            public void mouseEntered(MouseEvent e) {
                 iconlabel.setBorder(new LineBorder(Color.GRAY));
                iconlabel.setIcon(closeIcon);
            }

            public void mouseExited(MouseEvent e) {
                iconlabel.setBorder(null);
                iconlabel.setIcon(incloseIcon);
            }
        });
        tab.add(tablabel);
        tab.add(iconlabel);
//        tab.addMouseListener(new MouseAdapter() {
//
//            /**
//             * add double click tab listener
//             */
//            public void mouseClicked(MouseEvent e) {
//                tabbedPane.setSelectedComponent(newPanel);
//                if (e.getClickCount() == 2) {
//                    removeEvent(newPanel, tablabel.getText());
//                }
//            }
//        });
        tabbedPane.addTab(null, newPanel);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tab);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }
}
