package jungui;

import javax.swing.SwingUtilities;

/**
 * The Main class allows the JunGUI to be accessed and called.
 * It initializes the GUI using Swing.
 */
public class Main {

    /**
     * The main method to enter the program.
     * Creates and display the GUI.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JunGUI gui = new JunGUI();
            gui.setVisible(true);
        });
    }
}
