/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.test;

/**
 *
 * @author zx04741
 */
import cititool.global.AppContext;
import javax.swing.event.*;
import java.applet.*;
import javax.swing.tree.*;
import org.jdom.*;
import org.jdom.input.*;
import java.io.*;
import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;

public class AppletTree extends Applet implements TreeSelectionListener {

    private JTree tree;
    private TreePath path;
    private Panel topPanel;
    private DefaultMutableTreeNode top;
    private DefaultMutableTreeNode clicknode;
    private String link;

    public AppletTree() {
    }

    public void init() {
        try {
            super.init();
            this.setLayout(new GridLayout(1, 1));
            tree = createTree(new FileInputStream(AppContext.getServerConfigPath()));
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            tree.putClientProperty("JTree.lineStyle", "Angled");

            tree.setShowsRootHandles(true);
            tree.setEditable(false);
            tree.addTreeSelectionListener(this);
            IconRender render = new IconRender();
            tree.setCellRenderer(render);


            topPanel = new Panel(new BorderLayout());
            topPanel.add(tree);
            this.add(topPanel);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public JTree createTree(InputStream is) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(is);
            Element root = doc.getRootElement();
            TreeNode rootNode = new TreeNode(root.getAttributeValue("id"), root.getAttributeValue("name"), root.getAttributeValue("link"));
            top = new DefaultMutableTreeNode(rootNode);
            addNode(root, top);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //你可以在这里改变jtree中连线的颜色，我请教国外的高手才找到的，很酷的哦:)
        UIManager.put("Tree.hash", new ColorUIResource(Color.red));
        return new JTree(top);

    }

    private void addNode(Element e, DefaultMutableTreeNode rootNode) {

        String id = e.getAttributeValue("id");
        String name = e.getAttributeValue("name");
        String link = e.getAttributeValue("link");
        TreeNode node = new TreeNode(id, name, link);
        //如有父节点
        Element father = (Element) e.getParent();
        if (father != null) {
            String fid = father.getAttributeValue("id");
            DefaultMutableTreeNode fatherNode = getTreeNode(fid, rootNode);
            if (fatherNode != null) {
                fatherNode.add(new DefaultMutableTreeNode(node));
            }
        }
        //如有子节点
        Iterator it = e.getChildren().iterator();
        while (it.hasNext()) {
            Element child = (Element) it.next();
            addNode(child, rootNode);
        }

    }

    private DefaultMutableTreeNode getTreeNode(String id, DefaultMutableTreeNode rootNode) {
        DefaultMutableTreeNode returnNode = null;
        if (rootNode != null) {
            Enumeration enums = rootNode.breadthFirstEnumeration();
            while (enums.hasMoreElements()) {
                DefaultMutableTreeNode temp = (DefaultMutableTreeNode) enums.nextElement();
                TreeNode node = (TreeNode) temp.getUserObject();
                if (node.getId().equals(id)) {
                    returnNode = temp;
                    break;
                }
            }
        }
        return returnNode;

    }

    public void valueChanged(TreeSelectionEvent event) {
        if (event.getSource() == tree) {
            path = event.getPath();
            clicknode = (DefaultMutableTreeNode) path.getLastPathComponent();
            Object uo = clicknode.getUserObject();
            if (uo instanceof TreeNode) {
                TreeNode nd = (TreeNode) clicknode.getUserObject();
                link = nd.getLink();
            }
            //调用一个javascript函数；　
//      JSObject.getWindow (this). (""+link+"')")


        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        AppletTree tree = new AppletTree();
        tree.init();
        frame.getContentPane().add(tree);
        frame.setSize(600, 600);

        frame.show();
    }
}
