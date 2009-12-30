/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cititool.chat.client.listener;

import cititool.chat.protocol.TransProtocol;
import cititool.chat.server.handler.SessionServer;
import cititool.util.StringHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author zx04741
 */
public class FileProcesser {

    private volatile long p = 0;
    private volatile long total = 0;
    private int bz = 1024 * 4;
    private JProgressBar bar;
    private JLabel processLabel;
    private JLabel speedLabel;
    private boolean stop = false;
    private Semaphore gate;
    private boolean readfolder = true;
    private long t;
    private long oldt;
    private DecimalFormat df = new DecimalFormat("0.00");

    public void setProcessBar(JProgressBar bar) {
        bar.setMinimum(0);
        bar.setMaximum(10000);
        this.bar = bar;
    }

    public void setSpeedLabel(JLabel label) {
        speedLabel = label;
    }

    public void setProcessLabel(JLabel label) {
        processLabel = label;
    }

    public void addLock() {
        gate = new Semaphore(1);
    }

    public void setProcessBarSet(List<JProgressBar> bars) {
        for (JProgressBar bar : bars) {
            bar.setMinimum(0);
            bar.setMaximum(10000);
            bars.add(bar);
        }
    }

    public int getCurrentPercent() {
        if (total == 0) {
            return 0;
        } else {
            return (int) (p * 10000 / total);
        }
    }

    private void showSpeed() {
    }

    public void updateUI() {
        if (bar != null) {
            bar.setValue(getCurrentPercent());
        }
        if (processLabel != null) {
            processLabel.setText(shortSize(p) + "/" + shortSize(total));
        }
    }

    public String getSpeed(long detatime, int bytes) {

        if (detatime <= 0) {
            return "0 byte/s";
        } else {
            return shortSize((float) (bytes / detatime)) + "/s";
        }
    }

    protected String shortSize(float s) {
        if (s < 1024L) {
            return new StringBuilder(String.valueOf(s)).append(" byte").toString();
        }
        if (s < 0x100000L) {
            return new StringBuilder(String.valueOf(df.format(s / 1024F))).append(" kb").toString();
        } else {
            return new StringBuilder(String.valueOf(df.format(s / (1024 * 1024F)))).append(" mb").toString();
        }

    }

    public void setBufferSize(int bz) {
        this.bz = bz;
    }

    public void stop() {
        stop = true;
    }

    public void retore() {
        stop = false;
    }

    public void isReadInFolder(boolean f) {
        this.readfolder = f;
    }

    public void transferFile(Socket sender, Socket receiver, String savepath) throws IOException, ClassNotFoundException {



        //notify the sender and the recv start to start to transfer file

//            TransProtocol.writeStr(TransProtocol.START_TRANSFER_FH, receiver);
        ObjectInputStream ois = new ObjectInputStream(sender.getInputStream());
        String header = (String) ois.readObject();
        if (header.equals(TransProtocol.FILE_H)) {
            String filepath = (String) ois.readObject();
            total = ois.readLong();
            long count = ois.readLong();
            byte[] buffer;
            ObjectOutputStream recvOs = new ObjectOutputStream(receiver.getOutputStream());
            recvOs.writeObject(TransProtocol.FILE_H);
            recvOs.writeObject(savepath);
            recvOs.writeLong(total);
            recvOs.writeLong(count);
//                 receiver.writeObject(TransProtocol.FILE_H);
//                 receiver.writeObject(savepath);
//                 receiver.writeLong(total);
//                 receiver.writeLong(count);
            for (long i = 0; i < count; i++) {
                buffer = new byte[bz];
                buffer = (byte[]) ois.readObject();
//                    receiver.writeObject(buffer);
                recvOs.writeObject(buffer);
            }
            int lastsize = ois.readInt();
            recvOs.writeInt(lastsize);
            buffer = new byte[bz];
            buffer = (byte[]) ois.readObject();
            if (lastsize > -1) {
                recvOs.writeObject(buffer);
            }
        }

    }

    public void writeFile(File f, Socket socket) throws IOException, InterruptedException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(TransProtocol.FILE_H);
        oos.writeObject(f.getPath());
        p = 0;
        total = f.length();
        long count = total / bz;
        oos.writeLong(total);
        oos.writeLong(count);
        byte[] buffer;
        FileInputStream fis = new FileInputStream(f);
        if (speedLabel != null) {
            t = System.currentTimeMillis();
            oldt = t;
        }
        int flg = 0;
        for (long i = 0; i < count; i++) {
            if (gate != null) {
                if (stop) {
                    gate.acquire();
                } else {
                    gate.release();
                }
            }
            buffer = new byte[bz];
            fis.read(buffer);
            oos.writeObject(buffer);
            p += bz;
            flg += bz;
            if (speedLabel != null) {
                if (i % 10 == 0 || i == count - 1) {
                    t = System.currentTimeMillis();
                    speedLabel.setText(getSpeed(t - oldt, flg));
                    flg = 0;
                    oldt = t;
                    updateUI();
                }
            }
        }
        buffer = new byte[bz];
        int t = fis.read(buffer);
        oos.writeInt(t);
        oos.writeObject(buffer);
        p += t;
        updateUI();
        fis.close();
        System.out.println("write over...");
    }

    public void readFile(Socket socket) throws IOException, ClassNotFoundException {
        isReadInFolder(false);
        readFileInFolder(null, socket);
    }

    public void readFileInFolder(String filefolder, Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        String header = (String) ois.readObject();
        if (header.equals(TransProtocol.FILE_H)) {
            String filepath = (String) ois.readObject();
            String filename = StringHelper.getFileName(filepath);
            p = 0;
            total = ois.readLong();
            long count = ois.readLong();
            
            if (!StringHelper.isEmpty(filefolder)) {
                File folder = new File(filefolder);
                if (!(folder.exists() && folder.isDirectory())) {
                    folder.mkdirs();
                }
            }
            byte[] buffer;
            String t = "";
            if (!readfolder) {
                t = filepath;
            } else {
                t = filefolder + File.separator + filename;
            }
            System.out.println("read file==>" + t + ",total" + total + ",count==>" + count);
            FileOutputStream fos = new FileOutputStream(new File(t));
            if (speedLabel != null) {
                this.t = System.currentTimeMillis();
                oldt = this.t;
            }
            int flg = 0;
            for (long i = 0; i < count; i++) {
                buffer = new byte[bz];
                buffer = (byte[]) ois.readObject();
                fos.write(buffer);
                fos.flush();
                p += bz;
                if (speedLabel != null) {
                    if (i % 10 == 0 || i == count - 1) {
                        this.t = System.currentTimeMillis();
                        if (speedLabel != null) {
                            speedLabel.setText(getSpeed(this.t - oldt, flg));
                        }
                        flg = 0;
                        oldt = this.t;
                        updateUI();
                    }
                }
            }
            int lastsize = ois.readInt();
            p += lastsize;
            buffer = new byte[bz];
            buffer = (byte[]) ois.readObject();
            updateUI();
            if (lastsize > -1) {
                fos.write(buffer, 0, lastsize);
                fos.flush();
            }
            fos.close();
            System.out.println("read over...");
        }

    }
}
