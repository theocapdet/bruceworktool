/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client.listener;

import cititool.chat.protocol.TransProtocol;
import cititool.util.SwingThreadPool;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JProgressBar;

/**
 *
 * @author zx04741
 */
public class FileProcesser  {

    private  volatile long p = 0;
    private  volatile long total = 0;

    private  int bz=1024 * 4;
    private  Set<JProgressBar> bars=new HashSet<JProgressBar>();

    public void setProcessBar(JProgressBar bar){
        bar.setMinimum(0);
        bar.setMaximum(10000);
        bars.add(bar);
    }




    public int getCurrentPercent() {
        if(total==0)
            return 0;
        else
         return (int)(p * 10000/total);
    }

    public void updateUI(){
        for(Iterator<JProgressBar> iter=bars.iterator();iter.hasNext();){
           JProgressBar bar=iter.next();
           bar.setValue(getCurrentPercent());
        }
    }

    public void setBufferSize(int bz){
        this.bz=bz;
    }
    

    public  void writeFile(File f,Socket socket) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(TransProtocol.FILE_H);
        oos.writeObject(f.getPath());
        p=0;
        total = f.length();
        long count = total / bz;
        oos.writeLong(total);
        oos.writeInt(bz);
        oos.writeLong(count);
        System.out.println("file size==>" + total);
        System.out.println("count==>" + count);
        byte[] buffer;
        FileInputStream fis = new FileInputStream(f);

        for (long i = 0; i < count; i++) {
            buffer = new byte[bz];
            fis.read(buffer);
            oos.writeObject(buffer);
            p+=bz;
            updateUI();
        }
        buffer = new byte[bz];
        int t = fis.read(buffer);
        oos.writeInt(t);
        oos.writeObject(buffer);
        p+=t;
        updateUI();
        fis.close();
    }

    public  void readFile(String filefolder, Socket socket) throws IOException, ClassNotFoundException {

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        String header = (String) ois.readObject();
        String filepath = (String) ois.readObject();
        String filename = filepath.substring(filepath.lastIndexOf(File.separator) + 1);
        p=0;
        total  = ois.readLong();
        long count = ois.readLong();
        System.out.println("file size===>" + total);
        System.out.println("count==>" + count);
        byte[] buffer;
        FileOutputStream fos = new FileOutputStream(new File(filefolder + filename));
        if (header.equals(TransProtocol.FILE_H)) {
            System.out.println("filepath===>" + filepath);
            for (long i = 0; i < count; i++) {
                buffer = new byte[bz];
                buffer = (byte[]) ois.readObject();
                updateUI();
                p+=bz;
                fos.write(buffer);
                fos.flush();
            }
            int lastsize = ois.readInt();
            p+=lastsize;
            buffer = new byte[bz];
            buffer = (byte[]) ois.readObject();
            updateUI();
            if (lastsize > -1) {
                fos.write(buffer, 0, lastsize);
                fos.flush();
            }
        }
    }
}
