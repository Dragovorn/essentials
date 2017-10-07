package com.dragovorn.dragonbot.essentials.listener.gui;

import com.dragovorn.dragonbot.essentials.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * Used to allow the user to add quotes to the list manually, instead of the moderators.
 */
public class AddQuoteListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        if (Main.getInstance().getPanel().getQuote().getText().equals("") || !Main.getInstance().getQuotes().contains(Main.getInstance().getPanel().getQuote().getText())) {
            Main.getInstance().getQuotes().add(Main.getInstance().getPanel().getQuote().getText());

            Main.getInstance().getLogger().info("Added \'" + Main.getInstance().getPanel().getQuote().getText() + "\' to the quote list!");

            Main.getInstance().getPanel().getQuote().setText("");
        }
    }
}