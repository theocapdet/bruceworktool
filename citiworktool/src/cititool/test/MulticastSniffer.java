/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.test;

/**
 *
 * @author zx04741
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MulticastSniffer {

  public static void main(String[] args) {

    InetAddress ia = null;
    byte[] buffer = new byte[65509];
    DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
    int port = 0;

    try {
      try {
        ia = InetAddress.getByName("10.107.16.3");
      }
      catch (UnknownHostException e)  {
        //
      }
      port = 9080;
    }  // end try
    catch (Exception e) {
      System.err.println(e);
      System.err.println("Usage: java MulticastSniffer MulticastAddress port");
      System.exit(1);
    }

    try {
      MulticastSocket ms = new MulticastSocket(port);
      ms.joinGroup(ia);
      while (true) {
        ms.receive(dp);
        String s = new String(dp.getData(), 0, 0, dp.getLength());
        System.out.println(s);
      }
    }
    catch (SocketException se) {
      System.err.println(se);
    }
    catch (IOException ie) {
      System.err.println(ie);
    }

  }

}