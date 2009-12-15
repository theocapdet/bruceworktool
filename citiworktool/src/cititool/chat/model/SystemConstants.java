/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.chat.model;

import java.awt.Color;

/**
 *
 * @author Administrator
 */
public class SystemConstants {

  /* code
 *
 *
 * 665 no authorize
 * 666 login success
 * 667 test connection success
 * 668 test connection failure
   */
   public final static int NOAUTHORIZE=665;
   public final static int LOGON=666;
   public final static int TESTSUC=667;
   public final static int TESTFAIL=668;
   public final static int REG_SUC=669;
   public final static int REG_FAIL=670;
   public final static int REG_REP=671;
   public final static class  Status{

        public final static int PREPARED=0;
        public final static int STARTED=1;
        public final static int STOPING=2;
        public final static int STOPED=3;


        public final static String toString(int type){
            switch(type){
                case PREPARED:
                    return "PREPARED";
                case STARTED:
                    return "STARTED";
                case STOPING:
                    return "STOPING";
                case STOPED:
                    return "STOPED";
                default:
                    return "PREPARED";
            }
        }
    }

   public final static class SystemColor{

       public final static Color BROWN_GREEN=new Color(0, 128, 64);

       public final static Color DEFAULT=Color.BLACK;
   }


   public final static class SystemBlank{

       private static final String blank=" ";

       public final static String CONTENT_START=blank+blank+blank+blank;
   }

}
