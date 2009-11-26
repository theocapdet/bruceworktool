/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.model;

import java.io.Serializable;
/**
 *
 * @author Administrator
 */
public class UserInfo implements Serializable {

    private String id;
    private String username;
    private String pass;
    private String gender;
    private String phoneNo;
    private String nickname;
    private String address;
    private String country;
    private String birthday;
    private String photopath;

    public UserInfo() {
    }

    public UserInfo(String id) {
        this.id = id;
    }

    public UserInfo(String id, String username, String pass) {
        this.id = id;
        this.username = username;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserInfo)) {
            return false;
        }
        UserInfo other = (UserInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cititool.conn.Userinfo[id=" + id + "]";
    }

    /**
     * @return the photopath
     */
    public String getPhotopath() {
        return photopath;
    }

    /**
     * @param photopath the photopath to set
     */
    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }
}
