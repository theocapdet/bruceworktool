/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.util;

import cititool.adapter.FileTreeModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 *
 * @author Administrator
 */
public class ComponentHelper {

    private static JPopupMenu menu;

    /**
     * jtextarea output string..
     * @param jta
     * @param str
     */
    public static void jtaAppendLine(JTextArea jta, String str) {

        jta.append(DateHelper.getCurDateTime() + ":" + str + "\n");
        jta.setCaretPosition(jta.getText().length());

    }

    public static void jtpAppendLine(JTextPane jtp, String str) throws BadLocationException {
        Document doc = jtp.getDocument();
        doc.insertString(doc.getLength(), DateHelper.getCurDateTime() + ":" + str + "\n", null);
        jtp.setCaretPosition(doc.getLength());
    }

    public static void chatDefined(JTextPane jtp, String str, Color color, boolean appendTime) throws BadLocationException {
        Document doc = jtp.getDocument();
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, color);
        if (appendTime) {
            doc.insertString(doc.getLength(), str + "  " + DateHelper.getCurDateTime() + "\n", set);
        } else {
            doc.insertString(doc.getLength(), str + "\n", set);
        }
        jtp.setCaretPosition(doc.getLength());

    }

    public static void jtpAppendLine(JTextPane jtp, String str, Color color) throws BadLocationException {
        Document doc = jtp.getDocument();
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, color);
        doc.insertString(doc.getLength(), DateHelper.getCurDateTime() + ":" + str + "\n", set);
        jtp.setCaretPosition(doc.getLength());
    }

    public static void labelOutput(JLabel l, String str) {
        l.setText(str);
    }

    public static boolean checkNull(Component parent, JTextComponent com) {
        if (com.getText().equals("")) {
            JOptionPane.showMessageDialog(parent, com.getName() + " can't be null");
            return true;
        }
        return false;

    }

    public static boolean checkArrayNull(Component parent, List txtCom) {


        for (int i = 0; i < txtCom.size(); i++) {
            JTextComponent com = (JTextComponent) txtCom.get(i);
            if (!checkNull(parent, com)) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    public static TreeModel File2FileSystemTreeView(File root) {

        return new FileTreeModel(root);

    }

    public static TreeModel File2Tree(File root) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root.getName());
        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            for (int i = 0; i < files.length; i++) {
                traveseFile(files[i], rootNode);
            }
        }
        return model;
    }

    private static void traveseFile(File p, DefaultMutableTreeNode pNode) {


        DefaultMutableTreeNode node = new DefaultMutableTreeNode(p.getName());
        if (p.isDirectory()) {
            File[] list = p.listFiles();
            if (list.length == 0) {

                DefaultMutableTreeNode empty = new DefaultMutableTreeNode();
                node.add(empty);

            }

            for (int i = 0; i < list.length; i++) {
                File t = list[i];
                traveseFile(t, node);
            }

        }
        pNode.add(node);
    }

    public static void clearPopup(MouseEvent evt, final JTextComponent com) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            if (menu == null) {
                menu = new JPopupMenu();
                JMenuItem item = new JMenuItem("clear");
                item.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        com.setText("");
                        menu.setVisible(false);
                    }
                });
                menu.add(item);
            }
            menu.setLocation(evt.getLocationOnScreen());
            menu.setVisible(true);
        } else {
            if (menu != null) {
                menu.setVisible(false);
            }
        }
    }

    public static void filePopup(JTextComponent text, String regKey, Preferences pref, Container container) {

        JFileChooser jc = new JFileChooser(text.getText());
        jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (jc.showOpenDialog(container) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = jc.getSelectedFile();
        text.setText(f.getPath());
        pref.put(regKey, text.getText());

    }

    public static boolean checkEmpty(JTextComponent com, Container container) {
        boolean f = false;
        if (f = StringHelper.isEmpty(com.getText())) {
            JOptionPane.showMessageDialog(container, com.getName() + " should not be empty!");
        }
        return f;
    }

    public static boolean checkEmpty(JTextComponent[] coms, Container container) {
        for (int i = 0; i < coms.length; i++) {
            boolean f = checkEmpty(coms[i], container);
            if (f) {
                return f;
            }
        }
        return false;
    }

    public static class TreeNodeSearcher {

        private DefaultMutableTreeNode node;
        private String kword;

        public TreeNodeSearcher(String kword) {
            this.kword = kword;
        }

        public DefaultMutableTreeNode searchTree(DefaultMutableTreeNode root) {
            searchInTree(root);
            return node;
        }

        private void searchInTree(DefaultMutableTreeNode p) {
            if (p.isLeaf() && p.toString().equals(kword)) {
                node = p;
                return;
            } else {
                for (int i = 0, len = p.getChildCount(); i < len; i++) {
                    DefaultMutableTreeNode n = (DefaultMutableTreeNode) p.getChildAt(i);
                    searchInTree(n);
                }
            }
        }
    }

    public static Component getSubComByName(ComSearcher searcher, JComponent p, String name) {
        if (p == null) {
            return null;
        }
        Component o = searcher.getComByName(p,name);
        if (o == null) {
            return null;
        }      
        return o;
    }

    public static class ComSearcher {

        private Component root;
        private Component result;
        private String name;

        public Component getComByName(JComponent root, String name) {
            this.root = root;
            this.name = name;
            result = null;
            search(root);
            return result;
        }

        private void search(Component com) {
            if (com != null) {
                if (com.getName() != null && com.getName().equals(name)) {                   
                    result = com;
                    return;
                }
            }
            if (com instanceof Container) {
                Container p = (Container) com;
                Component[] child =  p.getComponents();
                for (int i = 0; i < child.length; i++) {
                    search(child[i]);
                }
            }
        }
    }
}
