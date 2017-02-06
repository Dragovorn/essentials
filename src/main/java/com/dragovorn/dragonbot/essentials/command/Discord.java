package com.dragovorn.dragonbot.essentials.command;

import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.User;
import com.dragovorn.dragonbot.essentials.EssentialsCore;
import com.sun.istack.internal.NotNull;

/**
 * @author Dragovorn
 * @since 1.00a
 *
 * Sends the user configured discord link into the chat.
 */
public class Discord extends Command {

    public Discord() {
        super("discord", 0, true);
    }

    @Override
    public void execute(@NotNull User user, @NotNull String[] strings) {
        if (EssentialsCore.getInstance().getPanel().getDiscord().equals("") && EssentialsCore.getInstance().getConfiguration().getDiscord().equals("")) {
            Bot.getInstance().sendMessage("%s -> %s doesn\'t have a discord link configured!", user.getName(), Bot.getInstance().getChannel());
            return;
        }

        Bot.getInstance().sendMessage("%s -> You can join %s\'s discord at %s", user.getName(), Bot.getInstance().getChannel(), (EssentialsCore.getInstance().getPanel().getDiscord().equals("") ? EssentialsCore.getInstance().getConfiguration().getDiscord() : EssentialsCore.getInstance().getPanel().getDiscord()));
    }
}
