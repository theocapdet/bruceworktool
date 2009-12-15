/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.chat.model;

import cititool.model.UserInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zx04741
 */
public class UserGroup {

    private List<UserInfo> user=new ArrayList<UserInfo>();
    private long groupId;
    private String groupName;
    private List<String> adminList=new ArrayList<String>();

    public UserGroup(List list,long groupId,List adminList){

        this.user=list;
        this.groupId=groupId;
        this.adminList=adminList;       
    }

    public UserGroup(List list,long groupId,String adminId){
        this.user=list;
        this.groupId=groupId;
        if(adminId!=null)
        this.adminList.add(adminId);
    }

    /**
     * @return the user
     */
    public List<UserInfo> getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(List<UserInfo> user) {
        this.user = user;
    }



    /**
     * @return the adminList
     */
    public List<String> getAdminList() {
        return adminList;
    }

    /**
     * @param adminList the adminList to set
     */
    public void setAdminList(List<String> adminList) {
        this.adminList = adminList;
    }

    /**
     * @return the groupId
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
