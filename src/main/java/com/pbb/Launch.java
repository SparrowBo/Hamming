package com.pbb;

import com.pbb.frame.MainJFrame;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class Launch {
    public static void main( String[] args ) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainJFrame frame = new MainJFrame();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
