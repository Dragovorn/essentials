package com.dragovorn.dragonbot.essentials;

import com.dragovorn.dragonbot.api.bot.configuration.Configuration;
import com.dragovorn.dragonbot.api.bot.plugin.BotPlugin;
import com.dragovorn.dragonbot.api.bot.plugin.Plugin;
import com.dragovorn.dragonbot.bot.DragonBot;
import com.dragovorn.dragonbot.essentials.command.*;
import com.dragovorn.dragonbot.essentials.command.console.ConsoleQuote;
import com.dragovorn.dragonbot.essentials.gui.EssentialsPanel;
import com.dragovorn.dragonbot.essentials.listener.gui.EssentialsListener;
import com.dragovorn.dragonbot.gui.MainWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Plugin(name = "Essentials", author = "Dragovorn", version= "1.01a")
public class EssentialsCore extends BotPlugin {

    public static final String PREFIX = "[Essentials]: "; // Use this until plugin specific loggers are a thing

    private List<String> quotes;

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

        this.configuration = new EssentialsConfiguration(registerFile("config.yml"));
        this.quotesConfig = new Configuration(registerFile("quotes.yml")) {
            @Override
            protected void addDefaults() {
                add("quotes", new ArrayList<String>());
            }
        };

        this.quotesConfig.load();
        this.configuration.load();
        this.quotes = this.quotesConfig.getList("quotes");
    }

    @Override
    public void onEnable() {
        this.panel = new EssentialsPanel();

        DragonBot.getInstance().getCommandManager().registerCommand(new Quote());
        DragonBot.getInstance().getCommandManager().registerCommand(new Discord());
        DragonBot.getInstance().getCommandManager().registerCommand(new Love());
        DragonBot.getInstance().getCommandManager().registerCommand(new Uptime());
        DragonBot.getInstance().getCommandManager().registerCommand(new FollowAge());

        DragonBot.getInstance().getCommandManager().registerConsoleCommand(new ConsoleQuote());

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