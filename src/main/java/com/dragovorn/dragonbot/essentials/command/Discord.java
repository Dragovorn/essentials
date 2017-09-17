package com.dragovorn.dragonbot.essentials.command;

import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.User;
import com.dragovorn.dragonbot.essentials.Main;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * Sends the user configured discord link into the chat.
 */
public class Discord extends Command {

    public Discord() {
        super("discord", 0, true);
    }

    @Override
    public void execute(User user, String[] strings) {
        if (Main.getInstance().getPanel().getDiscord().equals("") && Main.getInstance().getConfiguration().getDiscord().equals("")) {
            Bot.getInstance().sendMessage("%s -> %s doesn\'t have a discord link configured!", user.getName(), Bot.getInstance().getChannel());
            return;
        }

        Bot.getInstance().sendMessage("%s -> You can join %s\'s discord at %s", user.getName(), Bot.getInstance().getChannel(), (Main.getInstance().getPanel().getDiscord().equals("") ? Main.getInstance().getConfiguration().getDiscord() : Main.getInstance().getPanel().getDiscord()));
    }
}
