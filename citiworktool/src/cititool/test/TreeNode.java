/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.test;

/**
 *
 * @author zx04741
 */
public class TreeNode{
  private String id;
  private String name;
  private String link;
  public TreeNode(String id,String name,String link){
    this.id=id;
    this.name=name;
    this.link=link;
  }
  public String getId(){
    return id;
  }
  public void setId(String Id){
    this.id=Id;
  }
  public void  setName(String Name){
    this.name=Name;
  }

  public String getName(){
    return name;
  }

  public String toString(){
    return  name;
  }
  public String getLink(){
    return link;
  }
  public void setLink(String link){
    this.link=link;
  }


}

