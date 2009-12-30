/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.chat.client.com.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 *
 * @author zx04741
 */
public class JobPanel extends JPanel {

    public JobPanel(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public Dimension getPreferredSize() {
        JViewport viewport = (JViewport) getParent();
        if (viewport != null) {
            int wid = viewport.getWidth();
            FlowLayout layout = (FlowLayout) getLayout();
            int hei = layout.getVgap();
            for (int i = 0; i < getComponentCount(); i++) {
                Component c = getComponent(i);
                hei = Math.max(hei, c.getY() + c.getHeight() + layout.getVgap());
            }

            return new Dimension(wid, hei);
        }
        return super.getPreferredSize();
    }
}
