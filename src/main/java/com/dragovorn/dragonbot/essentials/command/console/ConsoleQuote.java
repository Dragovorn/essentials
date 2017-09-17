package com.dragovorn.dragonbot.essentials.command.console;

import com.dragovorn.dragonbot.api.bot.command.ConsoleCommand;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.essentials.Main;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * Sends a random quote from the list into the console.
 */
public class ConsoleQuote extends ConsoleCommand {

    public ConsoleQuote() {
        super("quote", 0, true);
    }

    @Override
    public void execute(String[] args) {
        if (Main.getInstance().getQuotes().size() == 0) {
            Bot.getInstance().getLogger().info("There are no quotes!");
            return;
        }

        Bot.getInstance().getLogger().info(Main.getInstance().getQuotes().get(ThreadLocalRandom.current().nextInt(0, Main.getInstance().getQuotes().size())));
    }
}