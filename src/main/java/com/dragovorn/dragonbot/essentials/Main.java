package com.dragovorn.dragonbot.essentials;

import com.dragovorn.dragonbot.essentials.command.Love;
import com.dragovorn.ircbot.api.bot.IRCBot;
import com.dragovorn.ircbot.api.plugin.IPlugin;
import com.dragovorn.ircbot.api.plugin.Plugin;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * Main Essentials plugin class handles the initialization of the plugin
 * look at this like the main method of your program.
 */
@Plugin(name = "Essentials", author = "Dragovorn", version= "1.0.2")
public class Main implements IPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;

        /* Initialize our configuration right when our plugin loads */
//        this.configuration = new EssentialsConfiguration(registerFile("config.yml"));
//        this.quotesConfig = new Configuration(registerFile("quotes.yml")) {
//            @Override
//            protected void addDefaults() {
//                add("quotes", new ArrayList<String>());
//            }
//        };

        /* Make sure to load the configurations */
//        this.quotesConfig.load();
//        this.configuration.load();

        /* Populate our quotes list */
//        this.quotes = this.quotesConfig.getList("quotes");
    }

    @Override
    public void onEnable() {
        /* Register commands */
        IRCBot.getInstance().getCommandManager().register(new Love());
//        DragonBot.getInstance().getCommandManager().registerCommand(new Discord());
//        DragonBot.getInstance().getCommandManager().registerCommand(new Uptime());
//        DragonBot.getInstance().getCommandManager().registerCommand(new FollowAge());
//        DragonBot.getInstance().getCommandManager().registerCommand(new Points());

        /* Register console commands */
//        DragonBot.getInstance().getCommandManager().registerConsoleCommand(new ConsoleQuote());

        /* Make and add button to the MainWindow of the Bot */
//        JButton button = new JButton("Essentials");
//        button.addActionListener(new EssentialsListener());

//        MainWindow.getInstance().getPanel().add(button);
//        MainWindow.getInstance().pack();
    }

    @Override
    public void onDisable() {
        /* Save the configurations */
//        this.quotesConfig.set("quotes", this.quotes);
//        this.quotesConfig.save();

//        this.configuration.set("discord", this.panel.getDiscord());
//        this.configuration.save();
    }
}