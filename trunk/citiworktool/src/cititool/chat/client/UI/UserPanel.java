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
import cititool.chat.client.com.panel.JobPanel;
import cititool.chat.client.handler.SenderFileJob;
import cititool.chat.model.SystemConstants.SystemBlank;
import cititool.chat.protocol.TransProtocol;
import cititool.model.UserInfo;
import cititool.util.ComponentHelper;
import cititool.util.FileHelper.FileCounter;
import cititool.util.ImageHelper;
import cititool.util.StringHelper;
import cititool.util.SwingThreadPool;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import static cititool.chat.model.SystemConstants.SystemColor;

/**
 *
 * @author zx04741
 */
public class UserPanel extends javax.swing.JPanel {

    /** Creates new form userpanel */
    public UserPanel() {
        initComponents();        
        workarea=new JobPanel();
        workarea.setName("workarea");
        jobView.setViewportView(workarea);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        inputtext = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        toolkitTab = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        facepane = new javax.swing.JTextPane();
        jSplitPane5 = new javax.swing.JSplitPane();
        chatinfoarea1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatpane = new javax.swing.JTextPane();
        userTab = new javax.swing.JTabbedPane();
        userView = new javax.swing.JScrollPane();
        userinfo = new javax.swing.JPanel();
        photolabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        userinfopane = new javax.swing.JTextPane();
        jobView = new javax.swing.JScrollPane();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cititool.MainApp.class).getContext().getResourceMap(UserPanel.class);
        setBackground(resourceMap.getColor("Form.background")); // NOI18N
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(700, 540));

        jSplitPane4.setBorder(null);
        jSplitPane4.setDividerLocation(360);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane4.setName("jSplitPane4"); // NOI18N
        jSplitPane4.setPreferredSize(new java.awt.Dimension(700, 550));

        inputArea1.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("inputArea1.border.lineColor"), 1, true)); // NOI18N
        inputArea1.setName("inputArea1"); // NOI18N

        inputsplitpane1.setBorder(null);
        inputsplitpane1.setDividerLocation(460);
        inputsplitpane1.setName("inputsplitpane1"); // NOI18N
        inputsplitpane1.setOneTouchExpandable(true);

        inputarea1.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("inputarea1.border.lineColor"), 1, true)); // NOI18N
        inputarea1.setAutoscrolls(true);
        inputarea1.setName("inputarea1"); // NOI18N
        inputarea1.setOpaque(false);
        inputarea1.setPreferredSize(new java.awt.Dimension(416, 99));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        inputtext.setBackground(resourceMap.getColor("inputtext.background")); // NOI18N
        inputtext.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("inputtext.border.lineColor"), 1, true)); // NOI18N
        inputtext.setMargin(new java.awt.Insets(0, 0, 0, 0));
        inputtext.setName("inputtext"); // NOI18N
        inputtext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputtextKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(inputtext);

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setToolTipText(resourceMap.getString("jButton1.toolTipText")); // NOI18N
        jButton1.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("jButton1.border.lineColor"), 1, true)); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputarea1Layout = new javax.swing.GroupLayout(inputarea1);
        inputarea1.setLayout(inputarea1Layout);
        inputarea1Layout.setHorizontalGroup(
            inputarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputarea1Layout.createSequentialGroup()
                .addContainerGap(405, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        inputarea1Layout.setVerticalGroup(
            inputarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputarea1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(jButton1))
        );

        inputsplitpane1.setLeftComponent(inputarea1);

        toolkitTab.setName("toolkitTab"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        facepane.setEditable(false);
        facepane.setName("facepane"); // NOI18N
        jScrollPane4.setViewportView(facepane);

        toolkitTab.addTab(resourceMap.getString("jScrollPane4.TabConstraints.tabTitle"), jScrollPane4); // NOI18N

        inputsplitpane1.setRightComponent(toolkitTab);

        javax.swing.GroupLayout inputArea1Layout = new javax.swing.GroupLayout(inputArea1);
        inputArea1.setLayout(inputArea1Layout);
        inputArea1Layout.setHorizontalGroup(
            inputArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputsplitpane1, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
        );
        inputArea1Layout.setVerticalGroup(
            inputArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputsplitpane1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
        );

        jSplitPane4.setRightComponent(inputArea1);

        jSplitPane5.setBorder(null);
        jSplitPane5.setDividerLocation(460);
        jSplitPane5.setName("jSplitPane5"); // NOI18N
        jSplitPane5.setOneTouchExpandable(true);
        jSplitPane5.setPreferredSize(new java.awt.Dimension(700, 200));

        chatinfoarea1.setName("chatinfoarea1"); // NOI18N
        chatinfoarea1.setPreferredSize(new java.awt.Dimension(416, 336));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        chatpane.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("chatpane.border.lineColor"), 1, true)); // NOI18N
        chatpane.setEditable(false);
        chatpane.setName("chatpane"); // NOI18N
        jScrollPane1.setViewportView(chatpane);

        javax.swing.GroupLayout chatinfoarea1Layout = new javax.swing.GroupLayout(chatinfoarea1);
        chatinfoarea1.setLayout(chatinfoarea1Layout);
        chatinfoarea1Layout.setHorizontalGroup(
            chatinfoarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );
        chatinfoarea1Layout.setVerticalGroup(
            chatinfoarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
        );

        jSplitPane5.setLeftComponent(chatinfoarea1);

        userTab.setName("userTab"); // NOI18N

        userView.setName("userView"); // NOI18N

        userinfo.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("userinfo.border.lineColor"), 1, true)); // NOI18N
        userinfo.setAutoscrolls(true);
        userinfo.setMinimumSize(new java.awt.Dimension(5, 5));
        userinfo.setName("userinfo"); // NOI18N
        userinfo.setPreferredSize(new java.awt.Dimension(190, 330));

        photolabel1.setBackground(resourceMap.getColor("photolabel1.background")); // NOI18N
        photolabel1.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("photolabel1.border.lineColor"), 2, true)); // NOI18N
        photolabel1.setName("photolabel1"); // NOI18N
        photolabel1.setPreferredSize(new java.awt.Dimension(198, 214));

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        userinfopane.setEditable(false);
        userinfopane.setForeground(resourceMap.getColor("userinfopane.foreground")); // NOI18N
        userinfopane.setName("userinfopane"); // NOI18N
        jScrollPane3.setViewportView(userinfopane);

        javax.swing.GroupLayout userinfoLayout = new javax.swing.GroupLayout(userinfo);
        userinfo.setLayout(userinfoLayout);
        userinfoLayout.setHorizontalGroup(
            userinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userinfoLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(photolabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        userinfoLayout.setVerticalGroup(
            userinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userinfoLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(photolabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
        );

        userView.setViewportView(userinfo);

        userTab.addTab(resourceMap.getString("userView.TabConstraints.tabTitle"), userView); // NOI18N

        jobView.setName("jobView"); // NOI18N
        jobView.setPreferredSize(new java.awt.Dimension(202, 173));
        userTab.addTab(resourceMap.getString("jobView.TabConstraints.tabTitle"), jobView); // NOI18N

        jSplitPane5.setRightComponent(userTab);

        jSplitPane4.setTopComponent(jSplitPane5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void inputtextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputtextKeyPressed
        // TODO add your handling code here:
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER && evt.isControlDown()) || (evt.getKeyCode() == KeyEvent.VK_S && evt.isAltDown())) {
            sendMsg();
        }
    }//GEN-LAST:event_inputtextKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        sendMsg();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void sendMsg() {
        try {
            Socket socket = ClientContext.getCurrentSocket();
            UserInfo curuser = ClientContext.getCurrentUserInfo();
            ComponentHelper.chatDefined(chatpane, curuser.getUsername(), SystemColor.BROWN_GREEN, true);
            ComponentHelper.chatDefined(chatpane, SystemBlank.CONTENT_START + inputtext.getText(), SystemColor.DEFAULT, false);
            TransProtocol.sendTalk(inputtext.getText() + "\r\n", user.getUsername(), socket);
            inputtext.setText("");
        } catch (BadLocationException ex) {
            ex.printStackTrace();
            ClientContext.warnLog("userpanel::" + user.getUsername(), ex);
        } catch (IOException ex) {
            ClientContext.warnLog("userpanel::" + user.getUsername(), ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatinfoarea1;
    private javax.swing.JTextPane chatpane;
    private javax.swing.JTextPane facepane;
    private javax.swing.JPanel inputArea1;
    private javax.swing.JPanel inputarea1;
    private javax.swing.JSplitPane inputsplitpane1;
    private javax.swing.JTextPane inputtext;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JSplitPane jSplitPane5;
    private javax.swing.JScrollPane jobView;
    private javax.swing.JLabel photolabel1;
    private javax.swing.JTabbedPane toolkitTab;
    private javax.swing.JTabbedPane userTab;
    private javax.swing.JScrollPane userView;
    private javax.swing.JPanel userinfo;
    private javax.swing.JTextPane userinfopane;
    // End of variables declaration//GEN-END:variables
    private UserInfo user;
    private DropTarget dropTarget;
    private ExecutorService uploadpool;
    private JobPanel workarea;

    private void initdata() {
        uploadpool = Executors.newFixedThreadPool(20);
        SwingThreadPool.execute(new Runnable() {

            public void run() {
                photolabel1.setText("loading picuture...");
                userinfopane.setText("loading user data...");

                try {
                    String photo = ClientContext.getDefaultPath();
                    if (!StringHelper.isEmpty(user.getPhotopath())) {
                        photo = ClientContext.getUserFolder(user) + File.separator + StringHelper.getFileName(user.getPhotopath());
                    }
                    photolabel1.setText(null);
                    ImageHelper.paintImg(photolabel1, new File(photo));
                } catch (IOException ex) {
                    ClientContext.warnLog("userpanel::initdata() " + user.getUsername(), ex);
                }
                StringBuffer sb = new StringBuffer();

                sb.append("UserId:" + user.getId() + "\r\n");
                sb.append("UserName:" + user.getUsername() + "\r\n");
                sb.append("birth:" + user.getBirthday() + "\r\n");
                sb.append("address:" + user.getAddress());
                userinfopane.setText(sb.toString());

                //drop and drag
                dropTarget = new DropTarget(inputtext, new DropTargetAdapter() {

                    public boolean isDragAcceptable(DropTargetDragEvent event) {
                        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
                    }

                    public boolean isDropAcceptable(DropTargetDropEvent event) {
                        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
                    }

                    public void drop(DropTargetDropEvent event) {
                        if (!isDropAcceptable(event)) {
                            event.rejectDrop();
                            JOptionPane.showMessageDialog(getRootPane(), "can't drop acceptable");
                            ClientContext.productLog("can't drop acceptable");
                            return;
                        }
                        event.acceptDrop(DnDConstants.ACTION_COPY);
                        Transferable transferable = event.getTransferable();
                        DataFlavor[] flavors = transferable.getTransferDataFlavors();
                        FileCounter counter = null;
                        try {
                            for (int i = 0; i < flavors.length; i++) {
                                DataFlavor d = flavors[i];
                                if (d.equals(DataFlavor.javaFileListFlavor)) {
                                    List fileList = new ArrayList();
                                    fileList = (List) transferable.getTransferData(d);
                                    counter = new FileCounter(fileList);
                                    counter.search();
                                    List<File> result = counter.getTotalFiles();
                                    ComponentHelper.chatDefined(chatpane, "total file number : " + result.size(), SystemColor.BROWN_GREEN, true);
                                    FocusOnJobTab();
                                    for (final File f : result) {
                                        SenderFileJob job = new SenderFileJob(f, workarea, user.getUsername());
                                        job.prepared();                                        
                                    }
                                }
                            }

                        } catch (BadLocationException ex) {
                            ClientContext.warnLog("product msg  error:", ex);
                        } catch (UnsupportedFlavorException e) {
                            // TODO Auto-generated catch block
                            ClientContext.warnLog("drop drag error:", e);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            ClientContext.warnLog("drop drag error:", e);
                        }
                    }
                });
            }
        });
    }

    public void FocusOnJobTab(){

        userTab.setSelectedComponent(jobView);
    }
}