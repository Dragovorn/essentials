package com.dragovorn.dragonbot.essentials.listener.gui;

import com.dragovorn.dragonbot.gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * Used to switch from the Essentials GUI back to the Dragon Bot's GUI.
 */
public class BackListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        MainWindow.getInstance().setContentPane(MainWindow.getInstance().getPanel());
        MainWindow.getInstance().pack();
    }
}