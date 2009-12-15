/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.adapter;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author zx04741
 */
public class FileTreeModel implements Serializable, TreeModel {

    private File root;
    private Vector listeners = new Vector();

    public FileTreeModel(File root) {
        this.root = root;
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        File file = (File) parent;
        String[] files = file.list();
        return new MyFile(file,files[index]);
        
    }

    public int getChildCount(Object parent) {

        File file = (File) parent;
        if (file.isDirectory()) {
            String[] files = file.list();
            return files.length;
        }
        return 0;
    }

    public boolean isLeaf(Object node) {
       File f=(File)node;
       return f.isFile();
    }

    public void valueForPathChanged(TreePath path, Object newValue) {

        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) newValue;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = {getIndexOfChild(parent, targetFile)};
        Object[] changedChildren = {targetFile};
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);
    }

    private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        Iterator iterator = listeners.iterator();
        TreeModelListener listener = null;
        while (iterator.hasNext()) {
            listener = (TreeModelListener) iterator.next();
            listener.treeNodesChanged(event);
        }
    }

    public int getIndexOfChild(Object parent, Object child) {

        File p=(File)parent;
        File cf=(File)child;

        String[] list=p.list();
        for(int i=0;i<list.length;i++){

            if(list[i].equals(cf.getName())){

                return i;
            }
        }
        return 0;
    }

    public void addTreeModelListener(TreeModelListener l) {

        listeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

    static class MyFile extends File{


       private MyFile(File f,String name){
            super(f,name);
       }

        @Override
       public String toString(){

           return this.getName();
       }
    }
}
