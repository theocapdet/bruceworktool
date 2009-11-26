/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.test;

/**
 *
 * @author zx04741
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.tree.*;

import javax.swing.tree.DefaultTreeCellRenderer;

class IconRender
        extends DefaultTreeCellRenderer {

//你需要替换成你的icon
    public static final Icon leafSelectedIcon = new ImageIcon("greeball.JPG");
    public static final Icon leafUnSelectedIcon = new ImageIcon("greyball.JPG");
    public static final Icon folderOpen = new ImageIcon("folderopen.JPG");
    public static final Icon folderClose = new ImageIcon("folderclose.JPG");

    public Component getTreeCellRendererComponent(JTree tree,
            Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded,
                leaf, row, hasFocus);

        if (leaf && selected) {
            setIcon(IconRender.leafSelectedIcon);
        } else if (leaf) {
            setIcon(IconRender.leafUnSelectedIcon);
        }

        return this;
    }

    public IconRender() {
        super();
        this.setLeafIcon(leafUnSelectedIcon);
        this.setOpenIcon(folderOpen);
        this.setClosedIcon(folderClose);

    }
}

