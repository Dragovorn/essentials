package com.dragovorn.dragonbot.essentials;

import com.dragovorn.dragonbot.api.bot.configuration.Configuration;
import com.dragovorn.dragonbot.api.bot.plugin.BotPlugin;
import com.dragovorn.dragonbot.api.bot.plugin.Plugin;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.essentials.command.Discord;
import com.dragovorn.dragonbot.essentials.command.Love;
import com.dragovorn.dragonbot.essentials.command.Quote;
import com.dragovorn.dragonbot.essentials.command.console.ConsoleQuote;
import com.dragovorn.dragonbot.essentials.gui.EssentialsPanel;
import com.dragovorn.dragonbot.essentials.listener.gui.EssentialsListener;
import com.dragovorn.dragonbot.gui.MainWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Plugin(name = "Essentials", author = "Dragovorn")
public class EssentialsCore extends BotPlugin {

    public static final String PREFIX = "[Essentials]: "; // Use this until plugin specific loggers are a thing

    private ArrayList<String> quotes;

    private Configuration quotesConfig;

    private EssentialsConfiguration configuration;

    private EssentialsPanel panel;

    private static EssentialsCore instance;

    public static EssentialsCore getInstance() {
        return instance;
    }

    public EssentialsConfiguration getConfiguration() {
        return this.configuration;
    }

    public EssentialsPanel getPanel() {
        return this.panel;
    }

    public List<String> getQuotes() {
        return this.quotes;
    }

    @Override
    public void onLoad() {
        instance = this;

        this.quotes = new ArrayList<>();
        this.configuration = new EssentialsConfiguration(registerFile("config.yml"));
        this.quotesConfig = new Configuration(registerFile("quotes.yml"));

        if (this.quotesConfig.getEntries().get("quotes") != null && this.quotesConfig.getList("quotes").size() == 0) {
            this.quotesConfig.set("quotes", this.quotes);
            this.quotesConfig.save();
        } else {
            this.quotesConfig.load();
            this.quotes = (ArrayList<String>) this.quotesConfig.getEntries().get("quotes");
        }

        if (this.configuration.getString("discord") == null) {
            this.configuration.generate();
        } else {
            this.configuration.load();
            this.configuration.update();
        }
    }

    @Override
    public void onEnable() {
        this.panel = new EssentialsPanel();

        Bot.getInstance().getCommandManager().registerCommand(new Quote());
        Bot.getInstance().getCommandManager().registerCommand(new Discord());
        Bot.getInstance().getCommandManager().registerCommand(new Love());
        Bot.getInstance().getCommandManager().registerConsoleCommand(new ConsoleQuote());

        JButton button = new JButton("Essentials");
        button.addActionListener(new EssentialsListener());

        MainWindow.getInstance().getPanel().add(button);
        MainWindow.getInstance().pack();
    }

    @Override
    public void onDisable() {
        this.quotesConfig.set("quotes", this.quotes);
        this.quotesConfig.save();

        this.configuration.set("discord", this.panel.getDiscord());
        this.configuration.save();
    }
}