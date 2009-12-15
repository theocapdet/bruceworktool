/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.model;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author zx04741
 */
public class MyJTreeNode extends  DefaultMutableTreeNode{

    public MyJTreeNode(Object userObject){
        super(userObject);
    }


     public String toString() {
        
	if (userObject == null) {
	    return null;
	} else {
            String s=userObject.toString();
	    return s.substring(s.lastIndexOf("/")+1)  ;
	}
    }
}
