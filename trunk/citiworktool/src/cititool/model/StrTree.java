/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author zx04741
 */
public class StrTree {

    private Map<String, StrNode> nodeTree;
    
    private StrNode root;

    private String curNode;

    private final static String ROOT="r";

    public StrTree(){
        nodeTree =new  TreeMap<String, StrNode>();
        root=new StrNode("");
        nodeTree.put("", root);
    }

    public String getRootNode(){
        return root.id;
    }
    

    private StrNode getNode(String id) {
         return nodeTree.get(id);
    }

    public String getParentNode(String id){
        StrNode p=nodeTree.get(id).parent;
        if(p==null)
            return ROOT;
        else
        return  p.id;
    }

    public boolean isNodeExsits(String id){
        return nodeTree.get(id)!=null;
    }

    public void addNode(String id){

        String[] folder=id.split("/");       
        StringBuilder sb=new StringBuilder();
        StrNode pNode=root;
        for(int i=0;i<folder.length;i++){
           if(i<folder.length && i>0){
                sb.append("/");
           }
            sb.append(folder[i]);
            StrNode node;
            String curStr=sb.toString();
            if((node=nodeTree.get(curStr))==null){
                node=new StrNode(curStr);
                nodeTree.put(curStr, node);
                pNode.data.add(node);
                node.parent=pNode;
            }
            pNode=node;
        }
    }

    public List getChildren(String id){

        List<String> list=new ArrayList<String>();
        StrNode node=nodeTree.get(id);
        List<StrNode> data=node.data;
        for(int i=0;i<data.size();i++){
            list.add(data.get(i).id);
        }
        return list;
    }


    private static class StrNode {

        private List<StrNode> data;
        private String id;
        private StrNode parent;

        private StrNode(String id) {
            this.id=id;
            data = new ArrayList<StrNode>();
        }
    }
}



