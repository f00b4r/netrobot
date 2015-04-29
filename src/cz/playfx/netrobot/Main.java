package cz.playfx.netrobot;

import cz.playfx.netrobot.app.Application;
import javax.swing.SwingUtilities;

/**
 *
 * @author Felix
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Application.launch(Application.class, args);
        });
    }
}
