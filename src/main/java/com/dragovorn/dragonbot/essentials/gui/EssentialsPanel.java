package com.dragovorn.dragonbot.essentials.gui;

import com.dragovorn.dragonbot.essentials.Main;
import com.dragovorn.dragonbot.essentials.listener.gui.AddQuoteListener;
import com.dragovorn.dragonbot.essentials.listener.gui.BackListener;
import com.dragovorn.dragonbot.gui.TextPrompt;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * This is the main GUI for Essentials, allows users to change the settings of the plugin
 */
public class EssentialsPanel extends JPanel {

    private JTextField quote;
    private JTextField discord;

    public EssentialsPanel() {
        Dimension size = new Dimension(500, 500);

        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));

        JButton viewQuotes = new JButton("View Quotes");
        viewQuotes.setEnabled(false);
        viewQuotes.setToolTipText("Coming soon L M A O");

        JPanel addQuote = new JPanel();
        addQuote.setLayout(new BoxLayout(addQuote, BoxLayout.X_AXIS));

        this.quote = new JTextField(9);
        this.quote.setMaximumSize(this.quote.getPreferredSize());
        this.quote.addActionListener(new AddQuoteListener());
        new TextPrompt("Quote", this.quote);

        JButton add = new JButton("Add Quote");
        add.addActionListener(new AddQuoteListener());

        addQuote.add(this.quote);
        addQuote.add(add);

        this.discord = new JTextField(9);
        this.discord.setMaximumSize(this.discord.getPreferredSize());
        this.discord.setText(Main.getInstance().getConfiguration().getDiscord());
        new TextPrompt("Discord Link", this.discord);

        JButton back = new JButton("Back");
        back.addActionListener(new BackListener());

        options.add(viewQuotes);
        options.add(addQuote);
        options.add(this.discord);
        options.add(back);

        add(options);
    }

    public JTextField getQuote() {
        return this.quote;
    }

    public String getDiscord() {
        return this.discord.getText();
    }
}