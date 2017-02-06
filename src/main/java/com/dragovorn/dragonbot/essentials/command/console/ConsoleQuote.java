package com.dragovorn.dragonbot.essentials.command.console;

import com.dragovorn.dragonbot.api.bot.command.ConsoleCommand;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.essentials.EssentialsCore;
import com.sun.istack.internal.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Dragovorn
 * @since 1.00a
 *
 * Sends a random quote from the list into the console.
 */
public class ConsoleQuote extends ConsoleCommand {

    public ConsoleQuote() {
        super("quote", 0, true);
    }

    @Override
    public void execute(@NotNull String[] args) {
        if (EssentialsCore.getInstance().getQuotes().size() == 0) {
            Bot.getInstance().getLogger().info("There are no quotes!");
            return;
        }

        Bot.getInstance().getLogger().info(EssentialsCore.getInstance().getQuotes().get(ThreadLocalRandom.current().nextInt(0, EssentialsCore.getInstance().getQuotes().size())));
    }
}