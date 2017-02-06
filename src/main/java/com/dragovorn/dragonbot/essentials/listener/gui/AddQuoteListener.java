package com.dragovorn.dragonbot.essentials.listener.gui;

import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.essentials.EssentialsCore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dragovorn
 * @since 1.00a
 *
 * Used to allow the user to add quotes to the list manually, instead of the moderators.
 */
public class AddQuoteListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        if (EssentialsCore.getInstance().getPanel().getQuote().getText().equals("") || !EssentialsCore.getInstance().getQuotes().contains(EssentialsCore.getInstance().getPanel().getQuote().getText())) {
            EssentialsCore.getInstance().getQuotes().add(EssentialsCore.getInstance().getPanel().getQuote().getText());

            Bot.getInstance().getLogger().info(EssentialsCore.PREFIX + "Added \'" + EssentialsCore.getInstance().getPanel().getQuote().getText() + "\' to the quote list!");

            EssentialsCore.getInstance().getPanel().getQuote().setText("");
        }
    }
}