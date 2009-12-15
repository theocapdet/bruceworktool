/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.chat.client.model;

import cititool.model.UserInfo;
import java.util.List;
import java.util.Vector;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author zx04741
 */
public class UserInfoTreeModel implements  TreeModel {

    private UserTreeNode root;
    private Vector listeners = new Vector();

    public UserInfoTreeModel(UserTreeNode root){

        this.root=root;
    }

    public Object getRoot() {
      
        return root;
    }

    public Object getChild(Object parent, int index) {
       
        UserTreeNode node=(UserTreeNode)parent;
        // child=node.getChildren().get(index);
        return node.getChildren().get(index);
    }

    public int getChildCount(Object parent) {
        UserTreeNode node=(UserTreeNode)parent;
        return node.getChildren().size();
    }

    public boolean isLeaf(Object node) {
        UserTreeNode userinfo=(UserTreeNode)node;
        return userinfo.getChildCount()==0;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        return;
    }

    public int getIndexOfChild(Object parent, Object child) {
        UserTreeNode node=(UserTreeNode)parent;
        List<UserTreeNode> list=node.getChildren();
        for(int i=0;i<list.size();i++){
            if(list.get(i)==child)
                return i;
        }
        return -1;
    }

    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

    

}
