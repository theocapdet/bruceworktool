/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * userpanel.java
 *
 * Created on Nov 9, 2009, 5:36:27 PM
 */
package cititool.chat.client.UI;

import cititool.chat.client.ClientContext;
import cititool.chat.protocol.TransProtocol;
import cititool.model.UserInfo;
import cititool.util.ComponentHelper;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;

/**
 *
 * @author zx04741
 */
public class UserPanel extends javax.swing.JPanel {

    /** Creates new form userpanel */
    public UserPanel() {
        initComponents();        
    }

    public UserPanel(UserInfo user) {
        this();
        this.user = user;       
        initdata();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane4 = new javax.swing.JSplitPane();
        inputArea1 = new javax.swing.JPanel();
        inputsplitpane1 = new javax.swing.JSplitPane();
        inputarea1 = new javax.swing.JPanel();
        sendBtn1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        inputtext = new javax.swing.JTextPane();
        facearea1 = new javax.swing.JPanel();
        userlabel1 = new javax.swing.JLabel();
        jSplitPane5 = new javax.swing.JSplitPane();
        chatinfoarea1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatpane = new javax.swing.JTextPane();
        userinfo1 = new javax.swing.JPanel();
        userinfolabel1 = new javax.swing.JLabel();
        photolabel1 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        jSplitPane4.setDividerLocation(400);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane4.setName("jSplitPane4"); // NOI18N

        inputArea1.setName("inputArea1"); // NOI18N

        inputsplitpane1.setDividerLocation(520);
        inputsplitpane1.setName("inputsplitpane1"); // NOI18N
        inputsplitpane1.setOneTouchExpandable(true);

        inputarea1.setAutoscrolls(true);
        inputarea1.setName("inputarea1"); // NOI18N
        inputarea1.setPreferredSize(new java.awt.Dimension(416, 99));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cititool.MainApp.class).getContext().getResourceMap(UserPanel.class);
        sendBtn1.setText(resourceMap.getString("sendBtn1.text")); // NOI18N
        sendBtn1.setName("sendBtn1"); // NOI18N
        sendBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBtn1ActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        inputtext.setName("inputtext"); // NOI18N
        jScrollPane2.setViewportView(inputtext);

        javax.swing.GroupLayout inputarea1Layout = new javax.swing.GroupLayout(inputarea1);
        inputarea1.setLayout(inputarea1Layout);
        inputarea1Layout.setHorizontalGroup(
            inputarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputarea1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sendBtn1))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
        );
        inputarea1Layout.setVerticalGroup(
            inputarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputarea1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sendBtn1)
                .addContainerGap())
        );

        inputsplitpane1.setLeftComponent(inputarea1);

        facearea1.setName("facearea1"); // NOI18N
        facearea1.setPreferredSize(new java.awt.Dimension(190, 99));

        userlabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        userlabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        userlabel1.setName("userlabel1"); // NOI18N

        javax.swing.GroupLayout facearea1Layout = new javax.swing.GroupLayout(facearea1);
        facearea1.setLayout(facearea1Layout);
        facearea1Layout.setHorizontalGroup(
            facearea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(userlabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );
        facearea1Layout.setVerticalGroup(
            facearea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(facearea1Layout.createSequentialGroup()
                .addComponent(userlabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );

        inputsplitpane1.setRightComponent(facearea1);

        javax.swing.GroupLayout inputArea1Layout = new javax.swing.GroupLayout(inputArea1);
        inputArea1.setLayout(inputArea1Layout);
        inputArea1Layout.setHorizontalGroup(
            inputArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputsplitpane1, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
        );
        inputArea1Layout.setVerticalGroup(
            inputArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputsplitpane1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        jSplitPane4.setRightComponent(inputArea1);

        jSplitPane5.setDividerLocation(520);
        jSplitPane5.setName("jSplitPane5"); // NOI18N
        jSplitPane5.setOneTouchExpandable(true);

        chatinfoarea1.setName("chatinfoarea1"); // NOI18N
        chatinfoarea1.setPreferredSize(new java.awt.Dimension(416, 336));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        chatpane.setName("chatpane"); // NOI18N
        jScrollPane1.setViewportView(chatpane);

        javax.swing.GroupLayout chatinfoarea1Layout = new javax.swing.GroupLayout(chatinfoarea1);
        chatinfoarea1.setLayout(chatinfoarea1Layout);
        chatinfoarea1Layout.setHorizontalGroup(
            chatinfoarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
        );
        chatinfoarea1Layout.setVerticalGroup(
            chatinfoarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );

        jSplitPane5.setLeftComponent(chatinfoarea1);

        userinfo1.setMinimumSize(new java.awt.Dimension(5, 5));
        userinfo1.setName("userinfo1"); // NOI18N
        userinfo1.setPreferredSize(new java.awt.Dimension(190, 336));

        userinfolabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        userinfolabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        userinfolabel1.setName("userinfolabel1"); // NOI18N

        photolabel1.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("photolabel1.border.lineColor"), 2, true)); // NOI18N
        photolabel1.setName("photolabel1"); // NOI18N
        photolabel1.setPreferredSize(new java.awt.Dimension(180, 200));

        javax.swing.GroupLayout userinfo1Layout = new javax.swing.GroupLayout(userinfo1);
        userinfo1.setLayout(userinfo1Layout);
        userinfo1Layout.setHorizontalGroup(
            userinfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(userinfolabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
            .addComponent(photolabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );
        userinfo1Layout.setVerticalGroup(
            userinfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userinfo1Layout.createSequentialGroup()
                .addComponent(photolabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userinfolabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane5.setRightComponent(userinfo1);

        jSplitPane4.setTopComponent(jSplitPane5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 758, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sendBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtn1ActionPerformed
        try {
            ComponentHelper.jtpAppendLine(chatpane,inputtext.getText());
            TransProtocol.writeTalk(inputtext.getText(),user.getUsername(), socket);
            inputtext.setText("");
        } catch (BadLocationException ex) {
            ClientContext.warnLog("userpanel::"+user.getUsername(), ex);
        } catch (IOException ex) {
            ClientContext.warnLog("userpanel::"+user.getUsername(), ex);
        }

}//GEN-LAST:event_sendBtn1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatinfoarea1;
    private javax.swing.JTextPane chatpane;
    private javax.swing.JPanel facearea1;
    private javax.swing.JPanel inputArea1;
    private javax.swing.JPanel inputarea1;
    private javax.swing.JSplitPane inputsplitpane1;
    private javax.swing.JTextPane inputtext;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JSplitPane jSplitPane5;
    private javax.swing.JLabel photolabel1;
    private javax.swing.JButton sendBtn1;
    private javax.swing.JPanel userinfo1;
    private javax.swing.JLabel userinfolabel1;
    private javax.swing.JLabel userlabel1;
    // End of variables declaration//GEN-END:variables
    private UserInfo user;
    private Socket socket;


    private void initdata() {
        final int width = (int)photolabel1.getBounds().getSize().getWidth();
        final int height = (int)photolabel1.getBounds().getSize().getHeight();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Image image = ImageIO.read(new File(user.getPhotopath()));
                    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    Graphics g = bi.getGraphics();                    
                    g.drawImage(image, 0, 0, width, height, null);
                    photolabel1.setIcon(new ImageIcon(bi));
                } catch (IOException ex) {
                    Logger.getLogger(UserInfoFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body>");
        sb.append("UserId:" + user.getId() + "<br>");
        sb.append("UserName:" + user.getUsername() + "<br>");
        sb.append("birth:" + user.getBirthday() + "<br>");
        sb.append("address:" + user.getAddress());
        sb.append("</body></html>");
        userinfolabel1.setText(sb.toString());
    }
}
