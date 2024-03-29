/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChatClient.java
 *
 * Created on Oct 4, 2009, 12:42:46 AM
 */
package cititool.chat.client.UI;

import cititool.chat.client.com.dialog.ExitDialog;
import cititool.chat.model.SystemConstants;
import cititool.chat.protocol.TransProtocol;
import cititool.util.ArrayHelper;
import cititool.util.ComponentHelper;
import cititool.util.EncryptHelper;
import cititool.util.NumberHelper;
import cititool.util.StringHelper;
import cititool.util.WindowHelper;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
public class LoginFrame extends javax.swing.JFrame {

    /** Creates new form ChatClient */
    public LoginFrame() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JButton();
        registerBtn = new javax.swing.JButton();
        isSaveUser = new javax.swing.JCheckBox();
        isSavePass = new javax.swing.JCheckBox();
        password = new javax.swing.JPasswordField();
        username = new javax.swing.JComboBox();
        star = new javax.swing.JLabel();
        star1 = new javax.swing.JLabel();
        optionBtn = new javax.swing.JButton();
        clientSetting = new javax.swing.JTabbedPane();
        settingPane = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        serverHostValue = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        serverPortValue = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        hostList = new javax.swing.JList();
        testServer = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        loadDataPane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        loginLog = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cititool.MainApp.class).getContext().getResourceMap(LoginFrame.class);
        setTitle(resourceMap.getString("LoginForm.title")); // NOI18N
        setName("LoginForm"); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), resourceMap.getColor("jPanel1.border.titleColor"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        loginBtn.setText(resourceMap.getString("loginBtn.text")); // NOI18N
        loginBtn.setName("loginBtn"); // NOI18N
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        registerBtn.setText(resourceMap.getString("registerBtn.text")); // NOI18N
        registerBtn.setName("registerBtn"); // NOI18N
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        isSaveUser.setSelected(true);
        isSaveUser.setText(resourceMap.getString("isSaveUser.text")); // NOI18N
        isSaveUser.setName("isSaveUser"); // NOI18N

        isSavePass.setText(resourceMap.getString("isSavePass.text")); // NOI18N
        isSavePass.setName("isSavePass"); // NOI18N
        isSavePass.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                isSavePassItemStateChanged(evt);
            }
        });

        password.setText(resourceMap.getString("password.text")); // NOI18N
        password.setName("password"); // NOI18N

        username.setEditable(true);
        username.setName("username"); // NOI18N
        username.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                usernameItemStateChanged(evt);
            }
        });

        star.setFont(resourceMap.getFont("star.font")); // NOI18N
        star.setForeground(resourceMap.getColor("star.foreground")); // NOI18N
        star.setText(resourceMap.getString("star.text")); // NOI18N
        star.setName("star"); // NOI18N

        star1.setFont(resourceMap.getFont("star1.font")); // NOI18N
        star1.setForeground(resourceMap.getColor("star1.foreground")); // NOI18N
        star1.setText(resourceMap.getString("star1.text")); // NOI18N
        star1.setName("star1"); // NOI18N

        optionBtn.setText(resourceMap.getString("optionBtn.text")); // NOI18N
        optionBtn.setName("optionBtn"); // NOI18N
        optionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(isSaveUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(isSavePass))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(star1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(star))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(loginBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(optionBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(registerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(star))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(star1))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isSaveUser)
                    .addComponent(isSavePass))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerBtn)
                    .addComponent(loginBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(optionBtn))
        );

        clientSetting.setName("clientSetting"); // NOI18N

        settingPane.setName("settingPane"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        serverHostValue.setText(resourceMap.getString("serverHostValue.text")); // NOI18N
        serverHostValue.setName("serverHostValue"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        serverPortValue.setText(resourceMap.getString("serverPortValue.text")); // NOI18N
        serverPortValue.setName("serverPortValue"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        hostList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        hostList.setFixedCellWidth(150);
        hostList.setName("hostList"); // NOI18N
        hostList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hostListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(hostList);

        testServer.setText(resourceMap.getString("testServer.text")); // NOI18N
        testServer.setName("testServer"); // NOI18N
        testServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testServerActionPerformed(evt);
            }
        });

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settingPaneLayout = new javax.swing.GroupLayout(settingPane);
        settingPane.setLayout(settingPaneLayout);
        settingPaneLayout.setHorizontalGroup(
            settingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingPaneLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(settingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(testServer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(serverPortValue)
                    .addComponent(serverHostValue, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        settingPaneLayout.setVerticalGroup(
            settingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingPaneLayout.createSequentialGroup()
                .addGroup(settingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingPaneLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(settingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(serverHostValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(settingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(serverPortValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)
                        .addComponent(testServer))
                    .addGroup(settingPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        clientSetting.addTab("setting", settingPane);

        loadDataPane.setName("loadDataPane"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        loginLog.setColumns(20);
        loginLog.setEditable(false);
        loginLog.setFont(resourceMap.getFont("仿宋"));
        loginLog.setForeground(resourceMap.getColor("loginLog.foreground")); // NOI18N
        loginLog.setLineWrap(true);
        loginLog.setRows(5);
        loginLog.setWrapStyleWord(true);
        loginLog.setName("loginLog"); // NOI18N
        jScrollPane1.setViewportView(loginLog);

        javax.swing.GroupLayout loadDataPaneLayout = new javax.swing.GroupLayout(loadDataPane);
        loadDataPane.setLayout(loadDataPaneLayout);
        loadDataPaneLayout.setHorizontalGroup(
            loadDataPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadDataPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addContainerGap())
        );
        loadDataPaneLayout.setVerticalGroup(
            loadDataPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadDataPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addContainerGap())
        );

        clientSetting.addTab(resourceMap.getString("loadDataPane.TabConstraints.tabTitle"), loadDataPane); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientSetting, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clientSetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        // TODO add your handling code here:

        rDlg = new RegisterDlg(this, true);
        rDlg.pack();
        rDlg.setHost(serverHostValue.getText(), NumberHelper.string2Int(serverPortValue.getText(), 8888));
        WindowHelper.showCenter(rDlg);

    }//GEN-LAST:event_registerBtnActionPerformed

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        // TODO add your handling code here:
        final String username = this.username.getSelectedItem().toString();
        String p = String.valueOf(this.password.getPassword());
        String ep = "";
        if (StringHelper.isEmpty(username) || StringHelper.isEmpty(p)) {
            ComponentHelper.jtaAppendLine(loginLog, "username or password should not be empty!");
            JOptionPane.showMessageDialog(rootPane, "username or password should not be empty!");
            return;
        }

        if (oldPass != null) {
            if (ArrayHelper.isArrayHave(oldPass, p)) {
                ep = p;
            } else {
                ep = EncryptHelper.encodeMd5(p);
            }
        } else {
            ep = EncryptHelper.encodeMd5(p);
        }



        prefs.put("isSaveUser", isSaveUser.isSelected() ? "1" : "0");
        prefs.put("isSavePass", isSavePass.isSelected() ? "1" : "0");

        password.setEnabled(false);
        final String userpass = ep;
        this.password.setText(userpass);

        try {
            final Socket s = new Socket(serverHostValue.getText(), NumberHelper.string2Int(serverPortValue.getText(), 8888));
            TransProtocol.requestLogin(username, userpass, s);

            String rstr = TransProtocol.getObject(s).toString();


            if (rstr.equals(SystemConstants.LOGON + "")) {
                boolean f = false;
                if (isSaveUser.isSelected() || isSavePass.isSelected()) {
                    if (oldUser != null) {
                        for (int i = 0; i < oldUser.length; i++) {
                            if (username.equals(oldUser[i])) {
                                f = true;
                            }
                        }
                    }
                    if (!f) {
                        if (oldUser != null) {
                            String str = StringHelper.Array2String(oldUser, ",");
                            String str1 = StringHelper.Array2String(oldPass, ",");
                            prefs.put("username", str + "," + username);
                            if (isSavePass.isSelected()) {
                                prefs.put("pass", str1 + "," + userpass);
                            } else {
                                prefs.put("pass", str1 + ",");
                            }
                        } else {
                            prefs.put("username", username);
                            prefs.put("pass", userpass);
                        }
                    }
                    prefs.put("lastuser", username);

                }
                this.setVisible(false);
                EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        final JFrame frame = new UserInfoFrame(username, s);
                        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        frame.addWindowListener(new java.awt.event.WindowAdapter() {

                            public void windowClosing(java.awt.event.WindowEvent e) {

//                                ExitDialog dlg=new ExitDialog(frame, true);

                                if (JOptionPane.showConfirmDialog(rootPane, "are your sure to exit?",
                                        "exit chatroom", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                                    try {
                                        TransProtocol.writeStr(TransProtocol.OFFLINE_H + username, s);
                                    } catch (IOException ex) {
                                        Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    frame.dispose();
                                }
                            }
                        });
                        WindowHelper.showCenter(frame);
                    }
                });

            } else if (rstr.equals(SystemConstants.EXISTS + "")) {
                ComponentHelper.jtaAppendLine(loginLog, "user:" + username + "already exists!");
                if (JOptionPane.showConfirmDialog(rootPane, "user is online ,are you sure to login?") == JOptionPane.YES_OPTION) {
                    this.setVisible(false);
                    EventQueue.invokeLater(new Runnable() {

                        public void run() {
                            JFrame frame = new UserInfoFrame(username, s);
                            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            WindowHelper.showCenter(frame);
                        }
                    });
                }
                password.setEnabled(true);
                return;
            } else if (rstr.equals(SystemConstants.NOAUTHORIZE + "")) {
                ComponentHelper.jtaAppendLine(loginLog, "user:" + username + "account does't exists!");
                JOptionPane.showMessageDialog(this, "user input username or password is not correct!");
                password.setEnabled(true);
                return;
            }
        } catch (IOException ex) {
            ComponentHelper.jtaAppendLine(loginLog, ex.getMessage());
            JOptionPane.showMessageDialog(this, "login fail..check system connection");
            password.setText(p);
            password.setEnabled(true);
            return;
        } catch (ClassNotFoundException ex) {
            ComponentHelper.jtaAppendLine(loginLog, ex.getMessage());
            JOptionPane.showMessageDialog(this, "login fail..check system connection");
            password.setText(p);
            password.setEnabled(true);
            return;
        }
    }//GEN-LAST:event_loginBtnActionPerformed

    private void hostListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hostListMouseClicked
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) hostList.getModel();
        if (evt.getClickCount() == 2) {
            String str = model.getElementAt(hostList.getSelectedIndex()).toString().trim();
            String[] s = str.split(":");
            serverHostValue.setText(s[0]);
            serverPortValue.setText(s[1]);

            prefs.put("lasthost", str);
        }

    }//GEN-LAST:event_hostListMouseClicked

    private void testServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testServerActionPerformed
        try {
            // TODO add your handling code here:
            Socket s = new Socket(serverHostValue.getText(), NumberHelper.string2Int(serverPortValue.getText(), 8888));
            TransProtocol.requestTestConn(s);
            String echo = TransProtocol.getObject(s).toString();
            if (echo.equals(SystemConstants.TESTSUC + "")) {
                JOptionPane.showMessageDialog(this, "connection success..");
            } else {
                JOptionPane.showMessageDialog(this, "connection fail..");
            }

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "connection fail..");
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(this, "connection fail..");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "connection fail..");
        }
    }//GEN-LAST:event_testServerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String host = serverHostValue.getText();
        String port = serverPortValue.getText();
        String v = host + ":" + port;
        DefaultListModel model = (DefaultListModel) hostList.getModel();
        for (int i = 0, l = model.getSize(); i < l; i++) {
            String str = model.get(i).toString();
            if (str.equals(v)) {
                JOptionPane.showMessageDialog(rootPane, "the net address exists!");
                return;
            }
        }
        model.addElement(v);
        String t = prefs.get("hostlist", "") + "," + v;
        t = StringHelper.removeComma(t);
        prefs.put("hostlist", t);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void usernameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_usernameItemStateChanged
        // TODO add your handling code here:
        String c = username.getSelectedItem().toString();

        if (oldUser != null && oldUser.length > 0) {
            for (int i = 0; i < oldUser.length; i++) {
                if (c.equals(oldUser[i])) {
                    if (isSavePass.isSelected()) {
                        password.setText(oldPass[i]);
                    }
                    return;
                }
            }
        }
        password.setEnabled(true);
        password.setText("");
    }//GEN-LAST:event_usernameItemStateChanged

    private void optionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionBtnActionPerformed
        // TODO add your handling code here:
        if (clientSetting.isVisible()) {
            clientSetting.setVisible(false);
            this.setSize(WIDTH, this.HEIGHT - clientSetting.HEIGHT);
        } else {
            clientSetting.setVisible(true);
            this.setSize(WIDTH, this.HEIGHT);
        }
        this.pack();

    }//GEN-LAST:event_optionBtnActionPerformed

    private void isSavePassItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_isSavePassItemStateChanged
        // TODO add your handling code here:
        if (oldUser != null) {
            password.setEnabled(!isSavePass.isSelected());
        }

    }//GEN-LAST:event_isSavePassItemStateChanged
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                LoginFrame dialog = new LoginFrame();
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane clientSetting;
    private javax.swing.JList hostList;
    private javax.swing.JCheckBox isSavePass;
    private javax.swing.JCheckBox isSaveUser;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel loadDataPane;
    private javax.swing.JButton loginBtn;
    private javax.swing.JTextArea loginLog;
    private javax.swing.JButton optionBtn;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField serverHostValue;
    private javax.swing.JTextField serverPortValue;
    private javax.swing.JPanel settingPane;
    private javax.swing.JLabel star;
    private javax.swing.JLabel star1;
    private javax.swing.JButton testServer;
    private javax.swing.JComboBox username;
    // End of variables declaration//GEN-END:variables
    private RegisterDlg rDlg;
    private Preferences prefs;
    private String[] oldUser;
    private String[] oldPass;

    public JTextArea getLog() {

        return loginLog;
    }

    private void initdata() {


        clientSetting.setVisible(false);
        this.setSize(WIDTH, this.HEIGHT - clientSetting.HEIGHT);
        this.pack();
        prefs = Preferences.userRoot().node("/com/cititoolkit/loginframe");
        String host = prefs.get("host", "");
        String lasthost = prefs.get("lasthost", "");

        String lastuser = prefs.get("lastuser", "");

        if (!host.equals("")) {
            String[] s = host.split(":");
            serverHostValue.setText(s[0]);
            serverPortValue.setText(s[1]);
        }

        if (!StringHelper.isEmpty(lasthost)) {
            String[] s = lasthost.split(":");
            serverHostValue.setText(s[0]);
            serverPortValue.setText(s[1]);
        }

        String hl = prefs.get("hostlist", "");
        String[] str = null;
        if (!hl.equals("")) {
            str = hl.split(",");
        }

        int r = -1;

        String t = prefs.get("username", "");
        if (!t.equals("")) {
            try {
                oldUser = StringHelper.split(t, ",");
            } catch (Exception ex) {
                ComponentHelper.jtaAppendLine(loginLog, ex.getMessage());
            }
            for (int i = 0; i < oldUser.length; i++) {
                if (lastuser.equals(oldUser[i])) {
                    r = i;
                }
                this.username.addItem(oldUser[i]);
            }
            this.username.setSelectedItem(lastuser);
        }



        t = prefs.get("pass", "");
        if (!t.equals("")) {
            try {
                oldPass = StringHelper.split(t, ",");

            } catch (Exception ex) {
                ComponentHelper.jtaAppendLine(loginLog, ex.getMessage());
            }
            this.password.setText(oldPass[r]);
        }


        String saveUser = prefs.get("isSaveUser", "");
        String savepass = prefs.get("isSavePass", "");
        if (savepass.equals("1")) {
            this.isSavePass.setSelected(true);
        }

        if (saveUser.equals("1")) {
            this.isSaveUser.setSelected(true);
        }

        this.getRootPane().setDefaultButton(loginBtn);
        DefaultListModel model = new DefaultListModel();
        if (str != null && str.length > 0) {
            for (int i = 0; i < str.length; i++) {
                model.addElement(str[i]);
            }
        }

        hostList.setModel(model);
    }
}
