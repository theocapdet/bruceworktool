/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.protocol;

import cititool.model.UserInfo;
import cititool.util.ImageHelper;
import cititool.util.StringHelper;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author zx04741
 */
public class TransProtocol {

    private final static char S = 0x0000;
    private final static char E = 0xffff;
    public final static String OBJ_H = S + "o" + S;
    public final static String STR_H = S + "s" + S;
    public final static String IMG_H = S + "i" + S;
    public final static String IMG_EH = E + "i" + E;
    public final static String FILE_H = S + "f" + S;
    public final static String FILE_EH = E + "f" + E;
    public final static String SPLIT = ((char) 0xffe0) + "";
    // operate str header
    public final static String TESTH = S + "t" + S;
    public final static String LOGIN_HEADER = ((char) 0xff00) + "";
    public final static String CTALK_SEND_H = ((char) 0xff01) + "";
    public final static String TALK_REC_H =   ((char) 0xff03)+"";


    public final static String USERNAME_HEADER = ((char) 0xff31) + "";
    public final static String USERLIST_HEADER = ((char) 0xff32) + "";

    public final static String REG_HEADER = ((char) 0xff51) + "";

    public final static String REQUEST_FILE_H=((char) 0xff52) + "";
    public final static String USERPIC_H=((char) 0xff53) + "";
    public final static String ISFILE_H=((char) 0xff54) + "";

    public final static String ERROR = (char) 0xffff + "";

    /**
     * basic write
     * @param header
     * @param o
     * @param socket
     * @throws IOException
     */
    private static void write(String header, Object o, Socket socket) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(header);
        oos.writeObject(o);
        oos.flush();
    }

    public static void writeStr(String str, Socket socket) throws IOException {
        write(STR_H, str, socket);
    }

    public static void writeObj(Object obj, Socket socket) throws IOException {
        write(OBJ_H, obj, socket);
    }

    public static void writeFile(File f, Socket socket) throws IOException {
         ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
         oos.writeObject(FILE_H);
         oos.writeObject(f.getPath());
         int bz=1024*4;
         long fsize=f.length();
         long count=fsize/bz;
         oos.writeLong(fsize);
         oos.writeInt(bz);
         oos.writeLong(count);
         System.out.println("file size==>"+fsize);
         System.out.println("count==>"+count);
         byte[] buffer;
         FileInputStream fis=new FileInputStream(f);
         for(long i=0;i<count;i++){
             buffer=new byte[bz];
             fis.read(buffer);
             oos.writeObject(buffer);
         }
         buffer=new byte[bz];
         int t=fis.read(buffer);
         oos.writeInt(t);
         oos.writeObject(buffer);
         
         fis.close();
    }

    public static void readFile(String filefolder,Socket socket) throws IOException, ClassNotFoundException{

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        String header = (String) ois.readObject();
        String filepath=(String) ois.readObject();
        String filename=filepath.substring(filepath.lastIndexOf(File.separator)+1);
        long fsize=ois.readLong();
       
        int bz=ois.readInt();
        long count=ois.readLong();
        System.out.println("file size===>"+fsize);
        System.out.println("count==>"+count);
        byte[] buffer;
        FileOutputStream fos=new FileOutputStream(new File(filefolder+filename));
        if(header.equals(FILE_H)){
            System.out.println("filepath===>"+filepath);
            for(long i=0;i<count;i++){
                buffer=new byte[bz];
                buffer=(byte[])ois.readObject();
                fos.write(buffer);
                fos.flush();
            }
            int  lastsize=ois.readInt();
            buffer=new byte[bz];
            buffer=(byte[])ois.readObject();
            if(lastsize>-1){
                fos.write(buffer,0,lastsize);
                fos.flush();
            }
        }
    }

    public static void writeImage(Image image, String format, Socket socket) throws IOException, InterruptedException {
       
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(IMG_H);
        if (image != null) {
            long s=System.currentTimeMillis();
            byte[] imgdata = ImageHelper.getFormatData(image, format);

            long e=System.currentTimeMillis();
            System.out.println(" write image byte cost time===>"+(e-s)+"ms");
            oos.writeInt(imgdata.length);
            oos.writeObject(imgdata);
        }else{
            oos.writeInt(0);
        }
        oos.flush();
      
    }

    public static BufferedImage readImage(Socket socket) throws IOException, ClassNotFoundException {
        
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        String header = (String) ois.readObject();
        BufferedImage bi=null;
        if (header.equals(IMG_H)) {
            int size = ois.readInt();
            if(size>0){
                byte[] data = (byte[]) ois.readObject();
                long s=System.currentTimeMillis();
                bi=ImageHelper.loadImage(data);
                long e=System.currentTimeMillis();
                System.out.println("read image cost time===>"+(e-s)+"ms");
            }else
                return null;
        }

        return bi;
    }

    public static void requestUserInfo(String username, Socket s) throws IOException {

        writeStr(USERNAME_HEADER + username, s);
    }

    public static void requestLogin(String user, String pass, Socket socket) throws IOException {
        writeStr(LOGIN_HEADER + user + SPLIT + pass, socket);
    }


    public static void requestTestConn(Socket socket) throws IOException {
        writeStr(TESTH, socket);
    }

    public static void sendTalk(String talk, String toperson, Socket socket) throws IOException {

        writeStr(CTALK_SEND_H + toperson + SPLIT + talk, socket);

    }


    public static void recvTalk(String talk,String fromperson,Socket socket) throws IOException{

        writeStr(TALK_REC_H + fromperson + SPLIT + talk, socket);
    }



    public static void requestUserList(Socket s) throws IOException {
        writeStr(USERLIST_HEADER, s);
    }

    public static Object getObject(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        String header = (String) ois.readObject();
        Object o = ois.readObject();
        return o;
    }


}
