/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.chat.client.model;

import cititool.chat.model.*;
import cititool.model.UserInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zx04741
 */
public class UserTreeNode {

   
    private List<UserTreeNode> childNodes=new ArrayList<UserTreeNode>();
    private UserInfo node;
    private UserGroup group;


    public UserTreeNode(UserInfo node,UserGroup group){
        this.node=node;
        this.group=group;

    }

    public UserTreeNode(){
        this(null,null);
    }

    public UserTreeNode(UserGroup node){
        this(null,node);
    }


    public List<UserTreeNode> getChildren(){

        return childNodes;
    }

    public int getChildCount(){

        return childNodes.size();
    }

    public void addChild(UserTreeNode client){

        childNodes.add(client);
    }

    public Object getValue(){
        if(group!=null && node==null)
           return group;
        else
            return node;
    }

    public String toString(){
        Object v=getValue();

        if(v instanceof  UserGroup){

            UserGroup gp=(UserGroup)v;
            return gp.getGroupName()+"["+gp.getGroupId()+"]";

        }else if(v instanceof UserInfo){

            UserInfo user=(UserInfo)v;
            return user.getNickname()+"["+user.getUsername()+"]";
        }
        else{

            return "";
        }
    }
}
