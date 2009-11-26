/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client.UI;

import cititool.uicomponent.*;
import cititool.MainApp;
import cititool.model.UserInfo;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author zx04741
 */
public class ChatUserinfoTabDef {

    private JTabbedPane tabbedPane;
    private ImageIcon incloseIcon;
    private ImageIcon closeIcon;

    public ChatUserinfoTabDef(JTabbedPane tabbedPane) {

        this.tabbedPane = tabbedPane;
        ResourceMap resourceMap = Application.getInstance(MainApp.class).getContext().getResourceMap(SqlTabDef.class);
        incloseIcon = resourceMap.getImageIcon("tabinactive.icon");
        closeIcon = resourceMap.getImageIcon("tabactive.icon");
    }

    private void removeEvent(JPanel newPanel, String title) {

        int curCom = tabbedPane.getSelectedIndex();
        tabbedPane.removeTabAt(curCom);
        tabbedPane.setSelectedIndex(curCom - 1);
    }

    private int getTabByUser(UserInfo user) {


        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (i != 0) {
                JPanel tab = (JPanel) tabbedPane.getTabComponentAt(i);
                JLabel userlabel = (JLabel) tab.getComponent(0);
                if (userlabel.getText().trim().equals(user.getUsername())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void addPanel(UserInfo user) {
        int index = getTabByUser(user);
        if (index != -1) {
            tabbedPane.setSelectedIndex(index);
            return;
        }
        final UserPanel newpanel = new UserPanel(user);
        JPanel tab = new JPanel();
        tab.setOpaque(false);
        final JLabel tablabel = new JLabel(user.getUsername());
        final JLabel iconlabel = new JLabel();
        tablabel.setFont(new Font(null, Font.PLAIN, 10));
        iconlabel.setIcon(incloseIcon);
        iconlabel.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                removeEvent(newpanel, tablabel.getText());
            }

            public void mouseEntered(MouseEvent e) {
                iconlabel.setIcon(closeIcon);
            }

            public void mouseExited(MouseEvent e) {
                iconlabel.setIcon(incloseIcon);
            }
        });
        tab.add(tablabel);
        tab.add(iconlabel);
        tab.addMouseListener(new MouseAdapter() {

            /**
             * add double click tab listener
             */
            public void mouseClicked(MouseEvent e) {
                tabbedPane.setSelectedComponent(newpanel);
                if (e.getClickCount() == 2) {
                    removeEvent(newpanel, tablabel.getText());
                }
            }
        });
        tabbedPane.addTab(null, newpanel);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tab);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }
}
