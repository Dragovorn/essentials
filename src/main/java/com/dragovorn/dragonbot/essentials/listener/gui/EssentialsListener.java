package com.dragovorn.dragonbot.essentials.listener.gui;

import com.dragovorn.dragonbot.essentials.Main;
import com.dragovorn.dragonbot.gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * This is preformed when the Essentials button is pushed in the GUI
 * it switches the bot's GUI to the essentials GUI
 */
public class EssentialsListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        MainWindow.getInstance().setContentPane(Main.getInstance().getPanel());
        MainWindow.getInstance().pack();
    }
}