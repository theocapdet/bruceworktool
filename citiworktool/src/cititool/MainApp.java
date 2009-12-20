/*
 * Desktop1App.java
 */
package cititool;

import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class MainApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new MainView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of Desktop1App
     */
    public static MainApp getApplication() {
        return Application.getInstance(MainApp.class);
    }

    /**
     * Main method launching the application.
     * javax.swing.plaf.motif.MotifLookAndFeel
     */
    public static void main(String[] args) {
        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.windowsLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Font f = new Font("Tahoma", Font.PLAIN, 11);

           
            UIManager.put("TextField.font", f);
            UIManager.put("Label.font", f);
            UIManager.put("ComboBox.font", f);
            UIManager.put("MenuBar.font", f);
            UIManager.put("MenuItem.font", f);
            UIManager.put("Menu.font", f);
            UIManager.put("ToolTip.font", f);
            UIManager.put("TextArea.font", f);
            UIManager.put("Panel.font", f);
            UIManager.put("Button.font", f);
            UIManager.put("List.font", f);
            UIManager.put("TabbedPane.font", f);
            UIManager.put("CheckBox.font", f);
            UIManager.put("RadioButton.font", f);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        launch(MainApp.class, args);
    }
}
