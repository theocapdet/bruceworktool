/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.test;

import cititool.global.AppContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author zx04741
 */
public class XMLTest {

    public static void main(String args[]){
        //jdom
        Document doc =null;
        try {
            SAXBuilder sb = new SAXBuilder();

            sb.build(new File(AppContext.getServerConfigPath()));

            Element root=doc.getRootElement();
            List list=root.getChildren();
            for(int i=0;i<list.size();i++){
                Element ele=(Element)list.get(i);
                System.out.println(ele.getName());

            }


        } catch (JDOMException ex) {
            Logger.getLogger(XMLTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        //dom4j

//        SAXReader reader=new SAXReader();
//       org.dom4j.Document document;
//        try {
//            document = reader.read(new File(AppContext.getServerConfigPath()));
//             List list1=document.selectNodes("//server/servername");
//            for(int i=0;i<list1.size();i++){
//
//                org.dom4j.Element ele=(org.dom4j.Element)list1.get(i);
//                System.out.println(ele.getName());
//            }
//        } catch (DocumentException ex) {
//            Logger.getLogger(XMLTest.class.getName()).log(Level.SEVERE, null, ex);
//        }



    }

}
