package com.dragovorn.dragonbot.essentials;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.bot.configuration.Configuration;
import com.dragovorn.dragonbot.api.bot.plugin.BotPlugin;
import com.dragovorn.dragonbot.api.bot.plugin.Plugin;
import com.dragovorn.dragonbot.api.twitch.TwitchAPI;
import com.dragovorn.dragonbot.essentials.command.*;
import com.dragovorn.dragonbot.essentials.command.console.ConsoleQuote;
import com.dragovorn.dragonbot.essentials.gui.EssentialsPanel;
import com.dragovorn.dragonbot.essentials.listener.gui.EssentialsListener;
import com.dragovorn.dragonbot.gui.MainWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * Main Essentials plugin class handles the initialization of the plugin
 * look at this like the main method of your program.
 */
@Plugin(name = "Essentials", author = "Dragovorn", version= "1.0.2")
public class Main extends BotPlugin {

    private List<String> quotes;

    private Configuration quotesConfig;

    private EssentialsConfiguration configuration;

    private EssentialsPanel panel;

    private TwitchAPI twitch;

    private static Main instance;

    public static Main getInstance() {
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

    public TwitchAPI getTwitchAPI() {
        return this.twitch;
    }

    @Override
    public void onLoad() {
        instance = this;

        /* Make a twitchAPI object using the API key twitch made public */
        this.twitch = new TwitchAPI("uo6dggojyb8d6soh92zknwmi5ej1q2");

        /* Initialize our configuration right when our plugin loads */
        this.configuration = new EssentialsConfiguration(registerFile("config.yml"));
        this.quotesConfig = new Configuration(registerFile("quotes.yml")) {
            @Override
            protected void addDefaults() {
                add("quotes", new ArrayList<String>());
            }
        };

        /* Make sure to load the configurations */
        this.quotesConfig.load();
        this.configuration.load();

        /* Populate our quotes list */
        this.quotes = this.quotesConfig.getList("quotes");
    }

    @Override
    public void onEnable() {
        this.panel = new EssentialsPanel();

        /* Register commands */
        DragonBot.getInstance().getCommandManager().registerCommand(new Quote());
        DragonBot.getInstance().getCommandManager().registerCommand(new Discord());
        DragonBot.getInstance().getCommandManager().registerCommand(new Love());
        DragonBot.getInstance().getCommandManager().registerCommand(new Uptime());
        DragonBot.getInstance().getCommandManager().registerCommand(new FollowAge());

        /* Register console commands */
        DragonBot.getInstance().getCommandManager().registerConsoleCommand(new ConsoleQuote());

        /* Make and add button to the MainWindow of the Bot */
        JButton button = new JButton("Essentials");
        button.addActionListener(new EssentialsListener());

        MainWindow.getInstance().getPanel().add(button);
        MainWindow.getInstance().pack();
    }

    @Override
    public void onDisable() {
        /* Save the configurations */
        this.quotesConfig.set("quotes", this.quotes);
        this.quotesConfig.save();

        this.configuration.set("discord", this.panel.getDiscord());
        this.configuration.save();
    }
}