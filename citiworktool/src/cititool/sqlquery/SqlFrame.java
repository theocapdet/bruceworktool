/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SqlFrame.java
 *
 * Created on Oct 23, 2009, 9:36:37 AM
 */
package cititool.sqlquery;

import cititool.chat.model.SystemConstants.SystemColor;
import cititool.uicomponent.SqlTabDef;
import cititool.com.DBConn;
import cititool.util.ComponentHelper;
import cititool.util.NumberHelper;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author zx04741
 */
public class SqlFrame extends javax.swing.JFrame {

    /** Creates new form SqlFrame */
    public SqlFrame() {
        initComponents();
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

        jDialog1 = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        host = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        port = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        database = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        className = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        schema = new javax.swing.JTextField();
        testBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        sqlTabPanel = new javax.swing.JTabbedPane();
        sqlPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        newBtn = new javax.swing.JButton();
        execBtn = new javax.swing.JButton();
        configBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        resultTab = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cititool.MainApp.class).getContext().getResourceMap(SqlFrame.class);
        jDialog1.setTitle(resourceMap.getString("jDialog1.title")); // NOI18N
        jDialog1.setName("jDialog1"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel2.border.titleFont"), resourceMap.getColor("jPanel2.border.titleColor"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 169));

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        host.setText(resourceMap.getString("host.text")); // NOI18N
        host.setName("host"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        port.setText(resourceMap.getString("port.text")); // NOI18N
        port.setName("port"); // NOI18N

        username.setText(resourceMap.getString("username.text")); // NOI18N
        username.setName("username"); // NOI18N

        pass.setText(resourceMap.getString("pass.text")); // NOI18N
        pass.setName("pass"); // NOI18N

        database.setText(resourceMap.getString("database.text")); // NOI18N
        database.setName("database"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        className.setText(resourceMap.getString("className.text")); // NOI18N
        className.setToolTipText(className.getText());
        className.setName("className"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        schema.setText(resourceMap.getString("schema.text")); // NOI18N
        schema.setName("schema"); // NOI18N
        schema.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                schemaPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(host, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(className, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                            .addComponent(pass, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(database, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGap(43, 43, 43)))
                        .addGap(194, 194, 194))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(schema, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(database, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(className, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(schema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        testBtn.setText(resourceMap.getString("testBtn.text")); // NOI18N
        testBtn.setName("testBtn"); // NOI18N
        testBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testBtnActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        logArea.setColumns(20);
        logArea.setLineWrap(true);
        logArea.setRows(5);
        logArea.setWrapStyleWord(true);
        logArea.setName("logArea"); // NOI18N
        jScrollPane2.setViewportView(logArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(testBtn)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(testBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel1.border.titleFont"), resourceMap.getColor("jPanel1.border.titleColor"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        sqlTabPanel.setName("sqlTabPanel"); // NOI18N

        sqlPanel.setName("sqlPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextPane1.setName("jTextPane1"); // NOI18N
        jTextPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextPane1MouseClicked(evt);
            }
        });
        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextPane1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout sqlPanelLayout = new javax.swing.GroupLayout(sqlPanel);
        sqlPanel.setLayout(sqlPanelLayout);
        sqlPanelLayout.setHorizontalGroup(
            sqlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE)
        );
        sqlPanelLayout.setVerticalGroup(
            sqlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
        );

        sqlTabPanel.addTab(resourceMap.getString("sqlPanel.TabConstraints.tabTitle"), sqlPanel); // NOI18N

        newBtn.setForeground(resourceMap.getColor("newBtn.foreground")); // NOI18N
        newBtn.setText(resourceMap.getString("newBtn.text")); // NOI18N
        newBtn.setName("newBtn"); // NOI18N
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        execBtn.setForeground(resourceMap.getColor("execBtn.foreground")); // NOI18N
        execBtn.setText(resourceMap.getString("execBtn.text")); // NOI18N
        execBtn.setName("execBtn"); // NOI18N
        execBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                execBtnActionPerformed(evt);
            }
        });

        configBtn.setForeground(resourceMap.getColor("configBtn.foreground")); // NOI18N
        configBtn.setText(resourceMap.getString("configBtn.text")); // NOI18N
        configBtn.setName("configBtn"); // NOI18N
        configBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configBtnActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel5.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel5.border.titleFont"), resourceMap.getColor("jPanel5.border.titleColor"))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N

        jSplitPane1.setDividerLocation(100);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        log.setColumns(20);
        log.setEditable(false);
        log.setLineWrap(true);
        log.setRows(3);
        log.setWrapStyleWord(true);
        log.setName("log"); // NOI18N
        log.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(log);

        jSplitPane1.setTopComponent(jScrollPane4);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        resultTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        resultTab.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        resultTab.setName("resultTab"); // NOI18N
        jScrollPane3.setViewportView(resultTab);

        jSplitPane1.setBottomComponent(jScrollPane3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(execBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(configBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(28, 28, 28)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(433, Short.MAX_VALUE))
            .addComponent(sqlTabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newBtn)
                    .addComponent(execBtn)
                    .addComponent(configBtn)
                    .addComponent(jButton1)
                    .addComponent(jLabel7))
                .addGap(8, 8, 8)
                .addComponent(sqlTabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void testBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testBtnActionPerformed

        // TODO add your handling code here:

        logArea.setForeground(Color.red);
//      DB2Pool conn = new DB2Pool(host, port, database, username, pass);
        conn.setLog(logArea);
        conn.initConn();
        ComponentHelper.jtaAppendLine(logArea, "connecting..");
        if (conn.test()) {
            ComponentHelper.jtaAppendLine(logArea, "test ok..");
        } else {
            ComponentHelper.jtaAppendLine(logArea, "test fail..");
        }

    }//GEN-LAST:event_testBtnActionPerformed

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        // TODO add your handling code here:
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridLayout(1, 1));
        JScrollPane sp = new JScrollPane();
        final JTextPane ta = new JTextPane();
        ta.setFont(new Font("Georgia", Font.BOLD, 12)); // NOI18N
        ta.setForeground(new Color(0, 23, 255)); // NOI18N
        ta.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                ComponentHelper.clearPopup(evt, ta);
            }
        });
        sp.setViewportView(ta);
        newPanel.add(sp);
        close.addPanel(newPanel);

    }//GEN-LAST:event_newBtnActionPerformed

    private void configBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configBtnActionPerformed
        // TODO add your handling code here:
        jDialog1.pack();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_configBtnActionPerformed

    private void execBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_execBtnActionPerformed
        // TODO add your handling code here:
        JPanel panel = (JPanel) sqlTabPanel.getSelectedComponent();
        JScrollPane sp = (JScrollPane) panel.getComponent(0);
        JTextPane ta = (JTextPane) sp.getViewport().getComponent(0);
        String t = "";
        if (ta.getSelectedText() == null) {
            t = ta.getText();
        } else {
            t = ta.getSelectedText();
        }
        final String sql = t.toLowerCase();
        conn.setLog(log);
        ComponentHelper.jtaAppendLine(log, "connecting..");
        conn.initConn();
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                if (sql.startsWith("select")) {
                    query(sql);
                } else {
                    exeSql(sql);
                }
            }
        });

    }//GEN-LAST:event_execBtnActionPerformed

    private void logMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logMouseClicked
        // TODO add your handling code here:
        ComponentHelper.clearPopup(evt, log);
    }//GEN-LAST:event_logMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextPane1MouseClicked
        // TODO add your handling code here:
        ComponentHelper.clearPopup(evt, jTextPane1);
    }//GEN-LAST:event_jTextPane1MouseClicked

    private void jTextPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyReleased
        // TODO add your handling code here:

        DefaultStyledDocument doc = (DefaultStyledDocument) jTextPane1.getDocument();
        int len = doc.getLength();
        String str = "";
        try {
            str = doc.getText(0, len).toLowerCase();
        } catch (BadLocationException ex) {
            Logger.getLogger(SqlFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, Color.red);
        StyleConstants.setBold(set, true);
        for (Iterator<String> iter=keyword.iterator();iter.hasNext(); ) {
            String substr = str;
            String kword=iter.next();
            System.out.println(kword);
            int kl = kword.length();
            int offset = -1;
            int count = 0;
            while ((offset = substr.indexOf(kword)) > -1) {
                doc.setCharacterAttributes(offset + count * kl, kl, set, true);
                substr = substr.substring(kl);
                count++;
            }
        }

        SimpleAttributeSet set1 = new SimpleAttributeSet();
        StyleConstants.setForeground(set, Color.blue);
        StyleConstants.setBold(set1, true);
        
        SimpleAttributeSet set2= new SimpleAttributeSet();
        StyleConstants.setForeground(set, SystemColor.BROWN_GREEN);
        StyleConstants.setBold(set2, true);
        int s = str.indexOf("from ")+5;

        

    }//GEN-LAST:event_jTextPane1KeyReleased
    


    private void schemaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_schemaPropertyChange
        // TODO add your handling code here:'
        pref.put("schema", schema.getText());
    }//GEN-LAST:event_schemaPropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SqlFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField className;
    private javax.swing.JButton configBtn;
    private javax.swing.JTextField database;
    private javax.swing.JButton execBtn;
    private javax.swing.JTextField host;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextArea log;
    private javax.swing.JTextArea logArea;
    private javax.swing.JButton newBtn;
    private javax.swing.JPasswordField pass;
    private javax.swing.JTextField port;
    private javax.swing.JTable resultTab;
    private javax.swing.JTextField schema;
    private javax.swing.JPanel sqlPanel;
    private javax.swing.JTabbedPane sqlTabPanel;
    private javax.swing.JButton testBtn;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
    private SqlTabDef close;
    private DBConn conn;
    private Preferences pref;
    private static List<String> keyword = new Vector<String>();
    private static String tablebef[] ={"set","from","into"};


    private void initdata() {
        close = new SqlTabDef(sqlTabPanel, newBtn);
        pref = Preferences.userRoot().node("/com/cititoolkit/sql");
        if (!pref.get("schema", "").equals("")) {
            schema.setText(pref.get("schema", ""));
        }
        jTextPane1.setFont(new Font("Georgia", Font.BOLD, 12)); // NOI18N
        jTextPane1.setForeground(new Color(0, 23, 255)); // NOI18N

        String[] def = {" select ", " update ", " delete ", " insert ",
            " from ", " values ", " exists ", " in ", " or "};
        keyword.addAll(Arrays.asList(def));

        log.setForeground(Color.blue);

        String username = this.username.getText();
        String pass = String.valueOf(this.pass.getPassword());
        String database = this.database.getText().trim();
        String host = this.host.getText().trim();
        int port = NumberHelper.string2Int(this.port.getText().trim(), 8000);
        String className = this.className.getText().trim();
        String url = "jdbc:db2://" + host + ":" + port + "/" + database;
        conn = new DBConn(className, url, username, pass);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                conn.closeConn();
            }
        });
    }

    private void query(final String sql) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                int count = conn.getNumber(sql);
                ComponentHelper.jtaAppendLine(log, "total number :" + count);
                System.out.println(conn.exeError());
                if (!conn.exeError()) {
                    DefaultTableModel m = (DefaultTableModel) resultTab.getModel();
                    Map<String, Vector> mm = conn.getRecordTabModel(sql);
                    m.setDataVector(mm.get("data"), mm.get("header"));
                    resultTab.updateUI();
                }
            }
        });


    }

    private void exeSql(String sql) {
        if (conn.executeSql(sql)) {
            ComponentHelper.jtaAppendLine(log, "execute successful...");
        } else {
            log.setForeground(Color.red);
            ComponentHelper.jtaAppendLine(log, "execute failure...");
            log.setForeground(Color.blue);
        }

    }
}
