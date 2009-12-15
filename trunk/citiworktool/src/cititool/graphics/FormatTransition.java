/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FormatTransition.java
 *
 * Created on 2009-10-26, 21:53:18
 */
package cititool.graphics;

import cititool.util.StringHelper;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class FormatTransition extends javax.swing.JDialog {

    /** Creates new form FormatTransition */
    public FormatTransition(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        intiEvnt();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        picpreview = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        openFile = new javax.swing.JButton();
        srcfile = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        destinefolder = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        alliasName = new javax.swing.JTextField();
        converseBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        descombox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cititool.MainApp.class).getContext().getResourceMap(FormatTransition.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        picpreview.setText(resourceMap.getString("picpreview.text")); // NOI18N
        picpreview.setBorder(new javax.swing.border.LineBorder(resourceMap.getColor("picpreview.border.lineColor"), 1, true)); // NOI18N
        picpreview.setName("picpreview"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        openFile.setText(resourceMap.getString("openFile.text")); // NOI18N
        openFile.setName("openFile"); // NOI18N
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileActionPerformed(evt);
            }
        });

        srcfile.setText(resourceMap.getString("srcfile.text")); // NOI18N
        srcfile.setName("srcfile"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        saveBtn.setText(resourceMap.getString("saveBtn.text")); // NOI18N
        saveBtn.setName("saveBtn"); // NOI18N
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        destinefolder.setText(resourceMap.getString("destinefolder.text")); // NOI18N
        destinefolder.setName("destinefolder"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        alliasName.setText(resourceMap.getString("alliasName.text")); // NOI18N
        alliasName.setName("alliasName"); // NOI18N

        converseBtn.setText(resourceMap.getString("converseBtn.text")); // NOI18N
        converseBtn.setName("converseBtn"); // NOI18N
        converseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                converseBtnActionPerformed(evt);
            }
        });

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        descombox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ".bmp", ".png", ".jpeg", ".gif" }));
        descombox.setName("descombox"); // NOI18N
        descombox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                descomboxItemStateChanged(evt);
            }
        });

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jProgressBar1.setName("jProgressBar1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(srcfile, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(openFile))
                            .addComponent(converseBtn)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(destinefolder, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(saveBtn))
                                    .addComponent(descombox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(alliasName, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(picpreview, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(picpreview, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(openFile)
                            .addComponent(srcfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(descombox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(saveBtn)
                            .addComponent(destinefolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(alliasName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(26, 26, 26)
                        .addComponent(converseBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser(srcfile.getText());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String selectedFormat =(String)descombox.getSelectedItem();

        final File f = chooser.getSelectedFile();
        if (f.getName().endsWith(".bmp")) {
            srcfile.setText(f.getPath());
            String fpath = f.getPath();
            String fname = fpath.substring(fpath.lastIndexOf(File.separator) + 1);
            fname = fname.substring(0, fname.lastIndexOf(".")) + selectedFormat;
            alliasName.setText(fname);
            EventQueue.invokeLater(new Runnable() {

                public void run() {
                    try {
                        Image img = ImageIO.read(f);
                        int width = picpreview.getWidth();
                        int height = picpreview.getHeight();
                        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                        Graphics2D g = bi.createGraphics();
                        g.drawImage(img, 0, 0, width, height, picpreview);
                        picpreview.setIcon(new ImageIcon(bi));
                    } catch (IOException ex) {
                        Logger.getLogger(FormatTransition.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "the file is not the right format");
            return;
        }
    }//GEN-LAST:event_openFileActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);

        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        destinefolder.setText(chooser.getSelectedFile().getPath());
    }//GEN-LAST:event_saveBtnActionPerformed

    private void converseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_converseBtnActionPerformed

        String selectedFormat =(String)descombox.getSelectedItem();
        BufferedInputStream bis = null;
        Image image = null;
        String fpath = srcfile.getText();
        String despath = destinefolder.getText();
        if (StringHelper.isEmpty(fpath) || StringHelper.isEmpty(despath)) {

            JOptionPane.showMessageDialog(this, "folder can't be empty");
            return;
        }

        String fname = alliasName.getText();
        File src = new File(fpath);
        try {
            BufferedImage bi = ImageIO.read(src);
            ImageIO.write(bi,selectedFormat.substring(1) , new File(despath + File.separator + fname));
        } catch (IOException e) {
            e.printStackTrace();
        }

       

    }//GEN-LAST:event_converseBtnActionPerformed

    private void descomboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_descomboxItemStateChanged
        // TODO add your handling code here:
                if(!StringHelper.isEmpty(alliasName.getText()) ){

            String oldv=alliasName.getText();
            oldv=oldv.substring(0,oldv.lastIndexOf("."));
            alliasName.setText(oldv+descombox.getSelectedItem());
        }
    }//GEN-LAST:event_descomboxItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                FormatTransition dialog = new FormatTransition(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField alliasName;
    private javax.swing.JButton converseBtn;
    private javax.swing.JComboBox descombox;
    private javax.swing.JTextField destinefolder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JButton openFile;
    private javax.swing.JLabel picpreview;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTextField srcfile;
    // End of variables declaration//GEN-END:variables

    private void intiEvnt() {
    }

    private void bmp2jpg(String bmpfile) {
    }
}
