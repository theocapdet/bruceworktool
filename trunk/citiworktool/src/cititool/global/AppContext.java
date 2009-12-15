/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.global;

import java.io.File;

/**
 *
 * @author zx04741
 */
public class AppContext {

    private AppContext(){
    }

      
    
    private static String appPath;




    public final static String getServerConfigPath(){

        return System.getProperty("java.io.tmpdir") +"citiworktool"+File.separator+"server.xml";
    }

    public final static String getSystemFolderPath(){

        return System.getProperty("java.io.tmpdir")+"citiworktool";
    }


    

}
