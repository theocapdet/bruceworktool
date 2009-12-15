/*
 * Desktop1View.java
 */
package cititool;

import cititool.com.ProcessWorker;
import cititool.chat.client.UI.LoginFrame;
import cititool.chat.server.UI.ChatServerFrame;
import cititool.model.MyJTreeNode;
import cititool.model.StrTree;
import cititool.sqlquery.SqlFrame;
import cititool.util.ComponentHelper;
import cititool.util.FileHelper;
import cititool.util.WindowHelper;
import cititool.util.JarNodeHelper;
import cititool.util.StringHelper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * The application's main frame.
 */
public class MainView extends FrameView {

    public MainView(SingleFrameApplication app) {
        super(app);

        initComponents();
        initdata();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = MainApp.getApplication().getMainFrame();
            aboutBox = new MainAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        MainApp.getApplication().show(aboutBox);
    }

    private void initdata() {
        pref = Preferences.userRoot().node("/com/cititoolkit");
        jt1.setText(pref.get("lastpath1", ""));
        jt2.setText(pref.get("lastpath2", ""));
        jt3.setText(pref.get("lastpath3", ""));
        jt5.setText(pref.get("lastpath5", ""));
        jt6.setText(pref.get("lastpath6", ""));
        jarpath.setText(pref.get("jarpath", ""));
        jarname.setText(pref.get("jarname", "app"));
        txtFolder.setText(pref.get("txtfolder", ""));
        classTree.setModel(new DefaultTreeModel(new MyJTreeNode("/")));
        jarJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        fileTree.setModel(null);
        DefaultListModel model = new DefaultListModel();
        jarJList.setModel(model);
    }

    private void jarCmd(String srcPath, String[] desPath, String saveName) {

        if (!saveName.endsWith(".jar")) {
            saveName = saveName.concat(".jar");
        }
        if (!srcPath.endsWith(File.separator)) {
            srcPath = srcPath.concat(File.separator);
        }
        final List<String> cmds = new ArrayList<String>();
        for (int i = 0; i < desPath.length; i++) {
            if (!StringHelper.isEmpty(desPath[i])) {
                if (!desPath[i].endsWith(File.separator)) {
                    desPath[i] = desPath[i].concat(File.separator);
                }
                cmds.add(" copy " + srcPath + saveName + " " + desPath[i]);
            }
        }

        final String dir = srcPath;
        final String jarCreateCmd = "jar -cvf " + srcPath + saveName + " *";
        final String delCmd = " del " + srcPath + saveName;

        final Thread t = new Thread(new Runnable() {

            public void run() {
                ProcessWorker pw = new ProcessWorker(jta1);
                pw.setDebug(true);
                jta1.append("jar package is running...\r\n");
                pw.setCmd(jarCreateCmd);
                pw.exeCmd(true, null, dir);
                for (String s : cmds) {
                    pw.setCmd(s);
                    pw.exeCmd();
                }
                pw.setCmd(delCmd);
                pw.exeCmd();
                jta1.append("jar package over... ");

            }
        });
        t.start();
    }

    private void setJarTree(String filepath) {

        //classTree.setModel(new DefaultTreeModel(new MyJTreeNode("/")));
        StrTree tree = JarNodeHelper.jarFileToModel(filepath);

        root = new MyJTreeNode(tree.getRootNode());
        DefaultTreeModel tModel = new DefaultTreeModel(root);
        tModel.setRoot(root);
        loadNode(tree, root);
//        //MyJTreeNode root=new MyJTreeNode(tree.getRootNode());
        classTree.setModel(tModel);
        classTree.repaint();
    }

    private void loadNode(StrTree tree, MyJTreeNode node) {
        String id = node.getUserObject().toString();
        List list = tree.getChildren(id);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String s = (String) list.get(i);
                MyJTreeNode folder = new MyJTreeNode(s);
                node.add(folder);
                loadNode(tree, folder);
            }
        }
    }

    private void searchClass() {
        final String className = classNameValue.getText();
        searchJarResult.setText("searching class ...\r\n");
        searchBtn.setEnabled(false);
        searchResult = new ArrayList<String>();
        searchThread = new Thread(new Runnable() {

            public void run() {
                Object[] str = jarJList.getSelectedValues();
                for (int i = 0; i < str.length; i++) {
                    int old = searchResult.size();
                    JarNodeHelper.searchClassName(className, str[i].toString(), searchResult);
                    if (searchResult.size() != old) {
                        searchJarResult.append("exsit in:" + str[i] + "\r\n");
                    }
                }
                searchJarResult.append("search over! keyword:" + className + " have " + searchResult.size() + "  results\r\n");
                searchBtn.setEnabled(true);

            }
        });
        searchThread.start();

    }

    private void addJarList(List data) {
        for (int i = 0; i < data.size(); i++) {
            addJarStr(data.get(i));
        }
    }

    private void addJarStr(Object data) {
        DefaultListModel model = (DefaultListModel) jarJList.getModel();
        if (model.getSize() == 0) {
            model.addElement(data);
        }
        boolean isExists = false;
        for (int i = 0; i < model.getSize(); i++) {
            if (model.get(i).toString().equals(data.toString())) {
                isExists = true;
            }
        }
        if (!isExists) {
            model.addElement(data);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mainhorizeSplitPane = new javax.swing.JSplitPane();
        leftSplitPane = new javax.swing.JSplitPane();
        catalogTabbedPane = new javax.swing.JTabbedPane();
        projectPane = new javax.swing.JPanel();
        filePane = new javax.swing.JPanel();
        detailTabbedPane = new javax.swing.JTabbedPane();
        classViewPane = new javax.swing.JPanel();
        comViewPane = new javax.swing.JPanel();
        rightSplitPane = new javax.swing.JSplitPane();
        workTabbedPane = new javax.swing.JTabbedPane();
        debugTabbedPane = new javax.swing.JTabbedPane();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        buildMenu = new javax.swing.JMenu();
        chatServerMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        viewMenu = new javax.swing.JMenu();
        tools = new javax.swing.JMenu();
        textToolMenu = new javax.swing.JMenu();
        textViewMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jartool = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        dbtoolmenu = new javax.swing.JMenu();
        dbquery = new javax.swing.JMenuItem();
        windowMenu = new javax.swing.JMenu();
        chatroomMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jarPackageFrame = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jt1 = new javax.swing.JTextField();
        btn1 = new javax.swing.JButton();
        desMainPanel = new javax.swing.JPanel();
        firstPanel4 = new javax.swing.JPanel();
        btn6 = new javax.swing.JButton();
        jt6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        clear1Btn = new javax.swing.JButton();
        firstPanel = new javax.swing.JPanel();
        btn2 = new javax.swing.JButton();
        jt2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        clear1Btn1 = new javax.swing.JButton();
        firstPanel1 = new javax.swing.JPanel();
        btn3 = new javax.swing.JButton();
        jt3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        clear1Btn2 = new javax.swing.JButton();
        firstPanel3 = new javax.swing.JPanel();
        btn5 = new javax.swing.JButton();
        jt5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        clear1Btn3 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jarname = new javax.swing.JTextField();
        jarBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jta1 = new javax.swing.JTextArea();
        textviewFrame = new javax.swing.JFrame();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        opentxtBtn = new javax.swing.JButton();
        txtFolder = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        fileTree = new javax.swing.JTree();
        showFileBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        encodingCombox = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        jarsearchFrame = new javax.swing.JFrame();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jarListScrollPane = new javax.swing.JScrollPane();
        jarJList = new javax.swing.JList();
        saveList = new javax.swing.JButton();
        openList = new javax.swing.JButton();
        clearList = new javax.swing.JButton();
        removeAllBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        classNameValue = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        stopSearchBtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        searchJarResult = new javax.swing.JTextArea();
        isalljar = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        curjar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        addjarBtn = new javax.swing.JButton();
        jarpath = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        classTree = new javax.swing.JTree();
        chooserJar = new javax.swing.JButton();

        mainPanel.setName("mainPanel"); // NOI18N

        mainhorizeSplitPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        mainhorizeSplitPane.setDividerLocation(330);
        mainhorizeSplitPane.setMinimumSize(new java.awt.Dimension(215, 29));
        mainhorizeSplitPane.setName("mainhorizeSplitPane"); // NOI18N
        mainhorizeSplitPane.setOneTouchExpandable(true);

        leftSplitPane.setDividerLocation(250);
        leftSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        leftSplitPane.setName("leftSplitPane"); // NOI18N
        leftSplitPane.setOneTouchExpandable(true);

        catalogTabbedPane.setName("catalogTabbedPane"); // NOI18N
        catalogTabbedPane.setPreferredSize(new java.awt.Dimension(330, 250));

        projectPane.setName("projectPane"); // NOI18N

        javax.swing.GroupLayout projectPaneLayout = new javax.swing.GroupLayout(projectPane);
        projectPane.setLayout(projectPaneLayout);
        projectPaneLayout.setHorizontalGroup(
            projectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        projectPaneLayout.setVerticalGroup(
            projectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cititool.MainApp.class).getContext().getResourceMap(MainView.class);
        catalogTabbedPane.addTab(resourceMap.getString("projectPane.TabConstraints.tabTitle"), projectPane); // NOI18N

        filePane.setName("filePane"); // NOI18N

        javax.swing.GroupLayout filePaneLayout = new javax.swing.GroupLayout(filePane);
        filePane.setLayout(filePaneLayout);
        filePaneLayout.setHorizontalGroup(
            filePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        filePaneLayout.setVerticalGroup(
            filePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );

        catalogTabbedPane.addTab(resourceMap.getString("filePane.TabConstraints.tabTitle"), filePane); // NOI18N

        leftSplitPane.setTopComponent(catalogTabbedPane);

        detailTabbedPane.setName("detailTabbedPane"); // NOI18N
        detailTabbedPane.setPreferredSize(new java.awt.Dimension(330, 250));

        classViewPane.setName("classViewPane"); // NOI18N

        javax.swing.GroupLayout classViewPaneLayout = new javax.swing.GroupLayout(classViewPane);
        classViewPane.setLayout(classViewPaneLayout);
        classViewPaneLayout.setHorizontalGroup(
            classViewPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        classViewPaneLayout.setVerticalGroup(
            classViewPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        detailTabbedPane.addTab(resourceMap.getString("classViewPane.TabConstraints.tabTitle"), classViewPane); // NOI18N

        comViewPane.setName("comViewPane"); // NOI18N

        javax.swing.GroupLayout comViewPaneLayout = new javax.swing.GroupLayout(comViewPane);
        comViewPane.setLayout(comViewPaneLayout);
        comViewPaneLayout.setHorizontalGroup(
            comViewPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        comViewPaneLayout.setVerticalGroup(
            comViewPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        detailTabbedPane.addTab(resourceMap.getString("comViewPane.TabConstraints.tabTitle"), comViewPane); // NOI18N

        leftSplitPane.setRightComponent(detailTabbedPane);

        mainhorizeSplitPane.setLeftComponent(leftSplitPane);

        rightSplitPane.setDividerLocation(350);
        rightSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        rightSplitPane.setName("rightSplitPane"); // NOI18N
        rightSplitPane.setOneTouchExpandable(true);

        workTabbedPane.setName("workTabbedPane"); // NOI18N
        workTabbedPane.setPreferredSize(new java.awt.Dimension(750, 350));
        rightSplitPane.setTopComponent(workTabbedPane);

        debugTabbedPane.setName("debugTabbedPane"); // NOI18N
        debugTabbedPane.setPreferredSize(new java.awt.Dimension(750, 150));
        rightSplitPane.setRightComponent(debugTabbedPane);

        mainhorizeSplitPane.setRightComponent(rightSplitPane);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainhorizeSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1193, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainhorizeSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        buildMenu.setText(resourceMap.getString("buildMenu.text")); // NOI18N
        buildMenu.setName("buildMenu"); // NOI18N
        fileMenu.add(buildMenu);

        chatServerMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK));
        chatServerMenuItem.setText(resourceMap.getString("chatServerMenuItem.text")); // NOI18N
        chatServerMenuItem.setName("chatServerMenuItem"); // NOI18N
        chatServerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatServerMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(chatServerMenuItem);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(cititool.MainApp.class).getContext().getActionMap(MainView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N
        menuBar.add(editMenu);

        viewMenu.setText(resourceMap.getString("viewMenu.text")); // NOI18N
        viewMenu.setName("viewMenu"); // NOI18N
        menuBar.add(viewMenu);

        tools.setText(resourceMap.getString("tools.text")); // NOI18N
        tools.setName("tools"); // NOI18N

        textToolMenu.setText(resourceMap.getString("textToolMenu.text")); // NOI18N
        textToolMenu.setName("textToolMenu"); // NOI18N

        textViewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        textViewMenuItem.setText(resourceMap.getString("textViewMenuItem.text")); // NOI18N
        textViewMenuItem.setName("textViewMenuItem"); // NOI18N
        textViewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textViewMenuItemActionPerformed(evt);
            }
        });
        textToolMenu.add(textViewMenuItem);

        tools.add(textToolMenu);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jartool.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, java.awt.event.InputEvent.CTRL_MASK));
        jartool.setText(resourceMap.getString("jartool.text")); // NOI18N
        jartool.setName("jartool"); // NOI18N
        jartool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jartoolActionPerformed(evt);
            }
        });
        jMenu1.add(jartool);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        tools.add(jMenu1);

        dbtoolmenu.setText(resourceMap.getString("dbtoolmenu.text")); // NOI18N
        dbtoolmenu.setName("dbtoolmenu"); // NOI18N

        dbquery.setText(resourceMap.getString("dbquery.text")); // NOI18N
        dbquery.setName("dbquery"); // NOI18N
        dbquery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dbqueryActionPerformed(evt);
            }
        });
        dbtoolmenu.add(dbquery);

        tools.add(dbtoolmenu);

        menuBar.add(tools);

        windowMenu.setText(resourceMap.getString("windowMenu.text")); // NOI18N
        windowMenu.setName("windowMenu"); // NOI18N

        chatroomMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        chatroomMenuItem.setText(resourceMap.getString("chatroomMenuItem.text")); // NOI18N
        chatroomMenuItem.setName("chatroomMenuItem"); // NOI18N
        chatroomMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatroomMenuItemActionPerformed(evt);
            }
        });
        windowMenu.add(chatroomMenuItem);

        menuBar.add(windowMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1193, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1019, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jarPackageFrame.setTitle(resourceMap.getString("jarPackageFrame.title")); // NOI18N
        jarPackageFrame.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        jarPackageFrame.setName("jarPackageFrame"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jt1.setText(resourceMap.getString("jt1.text")); // NOI18N
        jt1.setName("jt1"); // NOI18N

        btn1.setText(resourceMap.getString("btn1.text")); // NOI18N
        btn1.setName("btn1"); // NOI18N
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        desMainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("desMainPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), resourceMap.getColor("desMainPanel.border.titleColor"))); // NOI18N
        desMainPanel.setName("desMainPanel"); // NOI18N

        firstPanel4.setName("firstPanel4"); // NOI18N

        btn6.setText(resourceMap.getString("btn6.text")); // NOI18N
        btn6.setName("btn6"); // NOI18N
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });

        jt6.setName("jt6"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        clear1Btn.setText(resourceMap.getString("clear1Btn.text")); // NOI18N
        clear1Btn.setName("clear1Btn"); // NOI18N
        clear1Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear1BtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout firstPanel4Layout = new javax.swing.GroupLayout(firstPanel4);
        firstPanel4.setLayout(firstPanel4Layout);
        firstPanel4Layout.setHorizontalGroup(
            firstPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, firstPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jt6, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn6, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clear1Btn)
                .addGap(10, 10, 10))
        );
        firstPanel4Layout.setVerticalGroup(
            firstPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn6)
                .addComponent(jt6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(clear1Btn)
                .addComponent(jLabel10))
        );

        firstPanel.setName("firstPanel"); // NOI18N

        btn2.setText(resourceMap.getString("btn2.text")); // NOI18N
        btn2.setName("btn2"); // NOI18N
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        jt2.setText(resourceMap.getString("jt2.text")); // NOI18N
        jt2.setName("jt2"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        clear1Btn1.setText(resourceMap.getString("clear1Btn1.text")); // NOI18N
        clear1Btn1.setName("clear1Btn1"); // NOI18N
        clear1Btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear1Btn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout firstPanelLayout = new javax.swing.GroupLayout(firstPanel);
        firstPanel.setLayout(firstPanelLayout);
        firstPanelLayout.setHorizontalGroup(
            firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, firstPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jt2, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clear1Btn1))
        );
        firstPanelLayout.setVerticalGroup(
            firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn2)
                .addComponent(jt2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(clear1Btn1)
                .addComponent(jLabel2))
        );

        firstPanel1.setName("firstPanel1"); // NOI18N

        btn3.setText(resourceMap.getString("btn3.text")); // NOI18N
        btn3.setName("btn3"); // NOI18N
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        jt3.setName("jt3"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        clear1Btn2.setText(resourceMap.getString("clear1Btn2.text")); // NOI18N
        clear1Btn2.setName("clear1Btn2"); // NOI18N
        clear1Btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear1Btn2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout firstPanel1Layout = new javax.swing.GroupLayout(firstPanel1);
        firstPanel1.setLayout(firstPanel1Layout);
        firstPanel1Layout.setHorizontalGroup(
            firstPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, firstPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jt3, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn3, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clear1Btn2)
                .addGap(10, 10, 10))
        );
        firstPanel1Layout.setVerticalGroup(
            firstPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn3)
                .addComponent(jt3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(clear1Btn2)
                .addComponent(jLabel7))
        );

        firstPanel3.setName("firstPanel3"); // NOI18N

        btn5.setText(resourceMap.getString("btn5.text")); // NOI18N
        btn5.setName("btn5"); // NOI18N
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });

        jt5.setName("jt5"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        clear1Btn3.setText(resourceMap.getString("clear1Btn3.text")); // NOI18N
        clear1Btn3.setName("clear1Btn3"); // NOI18N
        clear1Btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear1Btn3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout firstPanel3Layout = new javax.swing.GroupLayout(firstPanel3);
        firstPanel3.setLayout(firstPanel3Layout);
        firstPanel3Layout.setHorizontalGroup(
            firstPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, firstPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jt5, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn5, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clear1Btn3)
                .addGap(10, 10, 10))
        );
        firstPanel3Layout.setVerticalGroup(
            firstPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn5)
                .addComponent(jt5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9)
                .addComponent(clear1Btn3))
        );

        javax.swing.GroupLayout desMainPanelLayout = new javax.swing.GroupLayout(desMainPanel);
        desMainPanel.setLayout(desMainPanelLayout);
        desMainPanelLayout.setHorizontalGroup(
            desMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desMainPanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(desMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(firstPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(desMainPanelLayout.createSequentialGroup()
                        .addComponent(firstPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addContainerGap(224, Short.MAX_VALUE))
        );
        desMainPanelLayout.setVerticalGroup(
            desMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desMainPanelLayout.createSequentialGroup()
                .addComponent(firstPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(firstPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(firstPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(firstPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel7.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Microsoft Sans Serif", 0, 11), resourceMap.getColor("jPanel7.border.titleColor"))); // NOI18N
        jPanel7.setName("jPanel7"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jarname.setText(resourceMap.getString("jarname.text")); // NOI18N
        jarname.setName("jarname"); // NOI18N

        jarBtn.setText(resourceMap.getString("jarBtn.text")); // NOI18N
        jarBtn.setName("jarBtn"); // NOI18N
        jarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jarBtnActionPerformed(evt);
            }
        });

        clearBtn.setText(resourceMap.getString("clearBtn.text")); // NOI18N
        clearBtn.setName("clearBtn"); // NOI18N
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jta1.setColumns(20);
        jta1.setEditable(false);
        jta1.setFont(resourceMap.getFont("jta1.font")); // NOI18N
        jta1.setRows(5);
        jta1.setToolTipText(resourceMap.getString("jta1.toolTipText")); // NOI18N
        jta1.setWrapStyleWord(true);
        jta1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jta1.border.title"))); // NOI18N
        jta1.setName("jta1"); // NOI18N
        jScrollPane1.setViewportView(jta1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jarname, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(clearBtn))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jarname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jarBtn)
                    .addComponent(clearBtn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jarPackageFrameLayout = new javax.swing.GroupLayout(jarPackageFrame.getContentPane());
        jarPackageFrame.getContentPane().setLayout(jarPackageFrameLayout);
        jarPackageFrameLayout.setHorizontalGroup(
            jarPackageFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jarPackageFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jarPackageFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(desMainPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jarPackageFrameLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44)
                        .addComponent(jt1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        jarPackageFrameLayout.setVerticalGroup(
            jarPackageFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jarPackageFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jarPackageFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btn1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desMainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        textviewFrame.setAlwaysOnTop(true);
        textviewFrame.setName("textviewFrame"); // NOI18N

        jSplitPane3.setDividerLocation(210);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setName("jSplitPane3"); // NOI18N

        jPanel5.setName("jPanel5"); // NOI18N

        opentxtBtn.setText(resourceMap.getString("opentxtBtn.text")); // NOI18N
        opentxtBtn.setName("opentxtBtn"); // NOI18N
        opentxtBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opentxtBtnActionPerformed(evt);
            }
        });

        txtFolder.setText(resourceMap.getString("txtFolder.text")); // NOI18N
        txtFolder.setName("txtFolder"); // NOI18N
        txtFolder.setToolTipText(txtFolder.getText());

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        fileTree.setEditable(true);
        fileTree.setName("fileTree"); // NOI18N
        fileTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fileTreeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(fileTree);

        showFileBtn.setText(resourceMap.getString("showFileBtn.text")); // NOI18N
        showFileBtn.setName("showFileBtn"); // NOI18N
        showFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFileBtnActionPerformed(evt);
            }
        });

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        encodingCombox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ISO-8859-1", "UTF-8", "GBK", "GB2312" }));
        encodingCombox.setName("encodingCombox"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(opentxtBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showFileBtn))
                    .addComponent(txtFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(encodingCombox, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(showFileBtn)
                            .addComponent(opentxtBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(encodingCombox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane3.setLeftComponent(jPanel5);

        jPanel6.setName("jPanel6"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("仿宋", 0, 13));
        txtArea.setForeground(resourceMap.getColor("txtArea.foreground")); // NOI18N
        txtArea.setRows(8);
        txtArea.setWrapStyleWord(true);
        txtArea.setName("txtArea"); // NOI18N
        jScrollPane6.setViewportView(txtArea);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
        );

        jSplitPane3.setRightComponent(jPanel6);

        javax.swing.GroupLayout textviewFrameLayout = new javax.swing.GroupLayout(textviewFrame.getContentPane());
        textviewFrame.getContentPane().setLayout(textviewFrameLayout);
        textviewFrameLayout.setHorizontalGroup(
            textviewFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
        );
        textviewFrameLayout.setVerticalGroup(
            textviewFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );

        jarsearchFrame.setTitle(resourceMap.getString("jarsearchFrame.title")); // NOI18N
        jarsearchFrame.setName("jarsearchFrame"); // NOI18N

        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jarListScrollPane.setName("jarListScrollPane"); // NOI18N

        jarJList.setToolTipText(resourceMap.getString("jarJList.toolTipText")); // NOI18N
        jarJList.setName("jarJList"); // NOI18N
        jarJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jarJListMouseClicked(evt);
            }
        });
        jarJList.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jarJListMouseMoved(evt);
            }
        });
        jarListScrollPane.setViewportView(jarJList);

        jScrollPane3.setViewportView(jarListScrollPane);

        saveList.setText(resourceMap.getString("saveList.text")); // NOI18N
        saveList.setName("saveList"); // NOI18N
        saveList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveListActionPerformed(evt);
            }
        });

        openList.setText(resourceMap.getString("openList.text")); // NOI18N
        openList.setName("openList"); // NOI18N
        openList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openListActionPerformed(evt);
            }
        });

        clearList.setText(resourceMap.getString("clearList.text")); // NOI18N
        clearList.setName("clearList"); // NOI18N
        clearList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearListActionPerformed(evt);
            }
        });

        removeAllBtn.setText(resourceMap.getString("removeAllBtn.text")); // NOI18N
        removeAllBtn.setName("removeAllBtn"); // NOI18N
        removeAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllBtnActionPerformed(evt);
            }
        });

        removeBtn.setText(resourceMap.getString("removeBtn.text")); // NOI18N
        removeBtn.setName("removeBtn"); // NOI18N
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(saveList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clearList)
                .addGap(85, 85, 85))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(removeAllBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeBtn)
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeAllBtn)
                    .addComponent(removeBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveList)
                    .addComponent(openList)
                    .addComponent(clearList)))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel3.setName("jPanel3"); // NOI18N

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setAutoscrolls(true);
        jSplitPane2.setName("jSplitPane2"); // NOI18N

        jPanel4.setName("jPanel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        classNameValue.setText(resourceMap.getString("classNameValue.text")); // NOI18N
        classNameValue.setName("classNameValue"); // NOI18N

        searchBtn.setText(resourceMap.getString("searchBtn.text")); // NOI18N
        searchBtn.setName("searchBtn"); // NOI18N
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        stopSearchBtn.setText(resourceMap.getString("stopSearchBtn.text")); // NOI18N
        stopSearchBtn.setName("stopSearchBtn"); // NOI18N
        stopSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopSearchBtnActionPerformed(evt);
            }
        });

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        searchJarResult.setColumns(20);
        searchJarResult.setEditable(false);
        searchJarResult.setRows(5);
        searchJarResult.setName("searchJarResult"); // NOI18N
        jScrollPane5.setViewportView(searchJarResult);

        isalljar.setText(resourceMap.getString("isalljar.text")); // NOI18N
        isalljar.setName("isalljar"); // NOI18N
        isalljar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isalljarActionPerformed(evt);
            }
        });

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        curjar.setEditable(false);
        curjar.setText(resourceMap.getString("curjar.text")); // NOI18N
        curjar.setName("curjar"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(isalljar)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(curjar, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(10, 10, 10)
                        .addComponent(classNameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(searchBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stopSearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(curjar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isalljar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classNameValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn)
                    .addComponent(stopSearchBtn)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanel4);

        jPanel2.setName("jPanel2"); // NOI18N

        addjarBtn.setText(resourceMap.getString("addjarBtn.text")); // NOI18N
        addjarBtn.setName("addjarBtn"); // NOI18N
        addjarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addjarBtnActionPerformed(evt);
            }
        });

        jarpath.setText(resourceMap.getString("jarpath.text")); // NOI18N
        jarpath.setName("jarpath"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        classTree.setAutoscrolls(true);
        classTree.setName("classTree"); // NOI18N
        jScrollPane4.setViewportView(classTree);

        chooserJar.setText(resourceMap.getString("chooserJar.text")); // NOI18N
        chooserJar.setName("chooserJar"); // NOI18N
        chooserJar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserJarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(addjarBtn)
                        .addGap(42, 42, 42)
                        .addComponent(jarpath, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(chooserJar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chooserJar)
                    .addComponent(addjarBtn)
                    .addComponent(jarpath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel3);

        javax.swing.GroupLayout jarsearchFrameLayout = new javax.swing.GroupLayout(jarsearchFrame.getContentPane());
        jarsearchFrame.getContentPane().setLayout(jarsearchFrameLayout);
        jarsearchFrameLayout.setHorizontalGroup(
            jarsearchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jarsearchFrameLayout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
                .addContainerGap())
        );
        jarsearchFrameLayout.setVerticalGroup(
            jarsearchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jarsearchFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                .addContainerGap())
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        // TODO add your handling code here:

        String lastpath = jt1.getText();

        JFileChooser jc = null;
        if (!lastpath.equals("")) {
            jc = new JFileChooser(lastpath);
        } else {
            jc = new JFileChooser();
        }

        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (jc.showOpenDialog(jarPackageFrame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = jc.getSelectedFile();
        jt1.setText(f.getPath());
        pref.put("lastpath1", f.getPath());
        //  jta1.append("保存注册表;["+f.getPath()+"]成功...");

    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        // TODO add your handling code here:

        String lastpath = jt2.getText();

        JFileChooser jc = null;
        if (!lastpath.equals("")) {
            jc = new JFileChooser(lastpath);
        } else {
            jc = new JFileChooser();
        }

        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jc.setMultiSelectionEnabled(true);
        jc.setDragEnabled(true);

        if (jc.showOpenDialog(jarPackageFrame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = jc.getSelectedFile();
        jt2.setText(f.getPath());
        pref.put("lastpath2", f.getPath());
        // jta1.append("保存注册表;["+f.getPath()+"]成功...");
    }//GEN-LAST:event_btn2ActionPerformed

    private void jarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jarBtnActionPerformed
        // TODO add your handling code here:
        String src = jt1.getText();
        String des1 = jt2.getText().trim();
        String des2 = jt3.getText().trim();
        String des3 = jt5.getText().trim();
        String des4 = jt6.getText().trim();

        if (src.trim().equals("") || (des1.equals("") && des2.equals("") && des3.equals("") && des4.equals(""))) {
            JOptionPane.showMessageDialog(jarPackageFrame, "文件夹不能为空");
            return;
        }
        pref.put("jarname", jarname.getText());
        String[] desArr = {des1, des2, des3, des4};
        jarCmd(src, desArr, jarname.getText());

    }//GEN-LAST:event_jarBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        // TODO add your handling code here:
        jta1.setText("");
        jta1.repaint();
    }//GEN-LAST:event_clearBtnActionPerformed

    private void jartoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jartoolActionPerformed
        // TODO add your handling code here:
        jarPackageFrame.pack();
        WindowHelper.showCenter(jarPackageFrame);
}//GEN-LAST:event_jartoolActionPerformed

    private void textViewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textViewMenuItemActionPerformed
        // TODO add your handling code here:
        textviewFrame.pack();
        WindowHelper.showCenter(textviewFrame);
    }//GEN-LAST:event_textViewMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        jarsearchFrame.pack();
        WindowHelper.showCenter(jarsearchFrame);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void addjarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addjarBtnActionPerformed
        // TODO add your handling code here:
        FileHelper helper = new FileHelper();
        helper.setSuffix(".jar");
        jarList = helper.searchFile(new File(jarpath.getText()));
        addJarList(jarList);
    }//GEN-LAST:event_addjarBtnActionPerformed

    private void chooserJarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooserJarActionPerformed
        // TODO add your handling code here:

        String lastpath = jarpath.getText();

        JFileChooser chooserjar = new JFileChooser(lastpath);
        chooserjar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (chooserjar.showOpenDialog(jarsearchFrame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = chooserjar.getSelectedFile();
        pref.put("jarpath", f.getAbsolutePath());
        jarpath.setText(f.getAbsolutePath());

    }//GEN-LAST:event_chooserJarActionPerformed

    private void jarJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jarJListMouseClicked
        // TODO add your handling code here:\
        if (jarJList.getSelectedIndex() > -1) {
            String curjarpath = jarJList.getSelectedValue().toString();
            curjar.setText(curjarpath);
            curjar.setToolTipText(curjarpath);
            if (evt.getClickCount() == 2) {
                setJarTree(curjarpath);
            }
        }
    }//GEN-LAST:event_jarJListMouseClicked

    private void jarJListMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jarJListMouseMoved
        // TODO add your handling code here:
        int index = jarJList.locationToIndex(evt.getPoint());
        if (index > -1) {
            jarJList.setToolTipText(StringHelper.obj2Str(jarJList.getModel().getElementAt(index)));
        }

    }//GEN-LAST:event_jarJListMouseMoved

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
        searchClass();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void isalljarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isalljarActionPerformed
        // TODO add your handling code here:

        ListSelectionModel list = jarJList.getSelectionModel();
        ListModel lm = jarJList.getModel();
        list.clearSelection();
        if (isalljar.isSelected() && lm.getSize() > 0) {
            list.addSelectionInterval(0, lm.getSize() - 1);
        }

    }//GEN-LAST:event_isalljarActionPerformed

    private void stopSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopSearchBtnActionPerformed
        // TODO add your handling code here:
        searchThread.interrupt();
        searchJarResult.append("search interrupt...\r\n");
    }//GEN-LAST:event_stopSearchBtnActionPerformed

    private void saveListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveListActionPerformed
        // TODO add your handling code here:
        Object[] savedList = null;
        if ((savedList = jarJList.getSelectedValues()).length > 0) {
            try {
                String filename = System.getProperty("java.io.tmpdir") + "opendfile";
                System.out.println(filename);
                File file = new File(filename);

                if (!file.exists()) {
                    file.createNewFile();
                }

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                for (int i = 0; i < savedList.length; i++) {
                    bw.write(savedList[i].toString() + "\r\n");
                    bw.flush();
                }
                searchJarResult.append("choiced jar file save successful,total " + savedList.length + " files");

            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(jarsearchFrame, "haven't chose any items!");
            return;
        }
    }//GEN-LAST:event_saveListActionPerformed

    private void openListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openListActionPerformed
        // TODO add your handling code here:
        File file = new File(System.getProperty("java.io.tmpdir") + "/opendfile");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(jarsearchFrame, "haven't save yet");
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s = "";

            while ((s = br.readLine()) != null) {
                addJarStr(s);
            }

        } catch (IOException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_openListActionPerformed

    private void clearListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearListActionPerformed
        // TODO add your handling code here:
        ListSelectionModel model = jarJList.getSelectionModel();
        for (int i = model.getMinSelectionIndex(); i <= model.getMaxSelectionIndex(); i++) {
            if (model.isSelectedIndex(i)) {
                model.removeSelectionInterval(i, i);
            }
        }

    }//GEN-LAST:event_clearListActionPerformed

    private void chatroomMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatroomMenuItemActionPerformed
        // TODO add your handling code here:
        if (chatroom == null) {
            chatroom = new LoginFrame();
        }
        WindowHelper.showCenter(chatroom);

    }//GEN-LAST:event_chatroomMenuItemActionPerformed

    private void chatServerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatServerMenuItemActionPerformed
        // TODO add your handling code here:
        if (chatServer == null) {
            chatServer = new ChatServerFrame();
        }
        WindowHelper.showCenter(chatServer);
    }//GEN-LAST:event_chatServerMenuItemActionPerformed

    private void opentxtBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opentxtBtnActionPerformed
        // TODO add your handling code here:
        String path = txtFolder.getText();

        JFileChooser chooserjar = new JFileChooser(path);
        chooserjar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (chooserjar.showOpenDialog(textviewFrame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File f = chooserjar.getSelectedFile();

        pref.put("txtfolder", f.getPath());
        txtFolder.setText(f.getPath());

    }//GEN-LAST:event_opentxtBtnActionPerformed

    private void showFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFileBtnActionPerformed
        // TODO add your handling code here:

        File f = new File(txtFolder.getText());
        fileTree.setModel(ComponentHelper.File2FileSystemTreeView(f));
        fileTree.updateUI();
    }//GEN-LAST:event_showFileBtnActionPerformed

    private void fileTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileTreeMouseClicked
        // TODO add your handling code here:
//        int selRow = fileTree.getRowForLocation(evt.getX(), evt.getY());
        TreePath path = fileTree.getPathForLocation(evt.getX(), evt.getY());
        if (path != null) {
            File f = (File) path.getLastPathComponent();

            if (evt.getClickCount() == 2 && f.isFile()) {
                txtArea.setText("");
                BufferedReader br = null;
                try {
                    String encoding = (String) encodingCombox.getSelectedItem();
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(f),encoding));
                    String tmp = "";
                    while ((tmp = br.readLine()) != null) {
                        //tmp=new String(tmp.getBytes("ISO-8859-1"),encoding);
                       // tmp=StringHelper.str2Unicode(tmp);
                        txtArea.append( tmp+ "\r\n");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }//GEN-LAST:event_fileTreeMouseClicked

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        // TODO add your handling code here:
        String lastpath = jt3.getText();

        JFileChooser jc = null;
        if (!lastpath.equals("")) {
            jc = new JFileChooser(lastpath);
        } else {
            jc = new JFileChooser();
        }

        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jc.showOpenDialog(jarPackageFrame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = jc.getSelectedFile();
        jt3.setText(f.getPath());
        pref.put("lastpath3", f.getPath());
    }//GEN-LAST:event_btn3ActionPerformed

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
        // TODO add your handling code here:
        String lastpath = jt5.getText();

        JFileChooser jc = null;
        if (!lastpath.equals("")) {
            jc = new JFileChooser(lastpath);
        } else {
            jc = new JFileChooser();
        }

        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jc.showOpenDialog(jarPackageFrame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = jc.getSelectedFile();
        jt5.setText(f.getPath());
        pref.put("lastpath5", f.getPath());
    }//GEN-LAST:event_btn5ActionPerformed

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
        // TODO add your handling code here:
        String lastpath = jt6.getText();

        JFileChooser jc = null;
        if (!lastpath.equals("")) {
            jc = new JFileChooser(lastpath);
        } else {
            jc = new JFileChooser();
        }

        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (jc.showOpenDialog(jarPackageFrame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = jc.getSelectedFile();
        jt6.setText(f.getPath());
        pref.put("lastpath6", f.getPath());
    }//GEN-LAST:event_btn6ActionPerformed

    private void clear1BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear1BtnActionPerformed
        // TODO add your handling code here:
        jt6.setText("");
        pref.put("lastpath6", "");
    }//GEN-LAST:event_clear1BtnActionPerformed

    private void clear1Btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear1Btn1ActionPerformed
        // TODO add your handling code here:
        jt2.setText("");
        pref.put("lastpath2", "");
    }//GEN-LAST:event_clear1Btn1ActionPerformed

    private void clear1Btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear1Btn2ActionPerformed
        // TODO add your handling code here:
        jt3.setText("");
        pref.put("lastpath3", "");
    }//GEN-LAST:event_clear1Btn2ActionPerformed

    private void clear1Btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear1Btn3ActionPerformed
        // TODO add your handling code here:
        jt5.setText("");
        pref.put("lastpath5", "");
    }//GEN-LAST:event_clear1Btn3ActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
        // TODO add your handling code here:
        ListSelectionModel model = jarJList.getSelectionModel();
        for (int i = model.getMinSelectionIndex(); i <= model.getMaxSelectionIndex(); i++) {
            if (model.isSelectedIndex(i)) {
                ((DefaultListModel) jarJList.getModel()).remove(i);
            }
        }
    }//GEN-LAST:event_removeBtnActionPerformed

    private void removeAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAllBtnActionPerformed
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) jarJList.getModel();
        model.removeAllElements();
    }//GEN-LAST:event_removeAllBtnActionPerformed

    private void dbqueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbqueryActionPerformed
        // TODO add your handling code here:
        SqlFrame frame=new SqlFrame();
        WindowHelper.showCenter(frame);
    }//GEN-LAST:event_dbqueryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addjarBtn;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JMenu buildMenu;
    private javax.swing.JTabbedPane catalogTabbedPane;
    private javax.swing.JMenuItem chatServerMenuItem;
    private javax.swing.JMenuItem chatroomMenuItem;
    private javax.swing.JButton chooserJar;
    private javax.swing.JTextField classNameValue;
    private javax.swing.JTree classTree;
    private javax.swing.JPanel classViewPane;
    private javax.swing.JButton clear1Btn;
    private javax.swing.JButton clear1Btn1;
    private javax.swing.JButton clear1Btn2;
    private javax.swing.JButton clear1Btn3;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton clearList;
    private javax.swing.JPanel comViewPane;
    private javax.swing.JTextField curjar;
    private javax.swing.JMenuItem dbquery;
    private javax.swing.JMenu dbtoolmenu;
    private javax.swing.JTabbedPane debugTabbedPane;
    private javax.swing.JPanel desMainPanel;
    private javax.swing.JTabbedPane detailTabbedPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JComboBox encodingCombox;
    private javax.swing.JPanel filePane;
    private javax.swing.JTree fileTree;
    private javax.swing.JPanel firstPanel;
    private javax.swing.JPanel firstPanel1;
    private javax.swing.JPanel firstPanel3;
    private javax.swing.JPanel firstPanel4;
    private javax.swing.JCheckBox isalljar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JButton jarBtn;
    private javax.swing.JList jarJList;
    private javax.swing.JScrollPane jarListScrollPane;
    private javax.swing.JFrame jarPackageFrame;
    private javax.swing.JTextField jarname;
    private javax.swing.JTextField jarpath;
    private javax.swing.JFrame jarsearchFrame;
    private javax.swing.JMenuItem jartool;
    private javax.swing.JTextField jt1;
    private javax.swing.JTextField jt2;
    private javax.swing.JTextField jt3;
    private javax.swing.JTextField jt5;
    private javax.swing.JTextField jt6;
    private javax.swing.JTextArea jta1;
    private javax.swing.JSplitPane leftSplitPane;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSplitPane mainhorizeSplitPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton openList;
    private javax.swing.JButton opentxtBtn;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JPanel projectPane;
    private javax.swing.JButton removeAllBtn;
    private javax.swing.JButton removeBtn;
    private javax.swing.JSplitPane rightSplitPane;
    private javax.swing.JButton saveList;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextArea searchJarResult;
    private javax.swing.JButton showFileBtn;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton stopSearchBtn;
    private javax.swing.JMenu textToolMenu;
    private javax.swing.JMenuItem textViewMenuItem;
    private javax.swing.JFrame textviewFrame;
    private javax.swing.JMenu tools;
    private javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtFolder;
    private javax.swing.JMenu viewMenu;
    private javax.swing.JMenu windowMenu;
    private javax.swing.JTabbedPane workTabbedPane;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    /**
     * system registry table
     */
    private Preferences pref;
    private List<File> jarList;
    private MyJTreeNode root;
    private Thread searchThread;
    private List<String> searchResult;
    private LoginFrame chatroom;
    private ChatServerFrame chatServer;
}
