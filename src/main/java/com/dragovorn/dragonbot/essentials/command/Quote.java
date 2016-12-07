package com.dragovorn.dragonbot.essentials.command;

import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.User;
import com.dragovorn.dragonbot.essentials.EssentialsCore;
import com.sun.istack.internal.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class Quote extends Command {

    public Quote() {
        super("quote", -1, false);
    }

    @Override
    public void execute(@NotNull User user, @NotNull String[] args) {
        if (args.length == 1) {
            if (EssentialsCore.getInstance().getQuotes().size() == 0) {
                Bot.getInstance().sendMessage("%s -> There are no quotes!", user.getName());
                return;
            }

            Bot.getInstance().sendMessage(EssentialsCore.getInstance().getQuotes().get(ThreadLocalRandom.current().nextInt(0, EssentialsCore.getInstance().getQuotes().size())));
        } else {
            if (args[1].equalsIgnoreCase("add")) {
                if (user.isMod()) {
                    StringBuilder builder = new StringBuilder();

                    for (int x = 2; x < args.length; x++) {
                        builder.append(args[x]).append(" ");
                    }

                    EssentialsCore.getInstance().getQuotes().add(builder.toString().trim());

                    Bot.getInstance().sendMessage("\'%s\' was added to the quote list and is number %s!", builder.toString().trim(), EssentialsCore.getInstance().getQuotes().size());
                } else {
                    Bot.getInstance().sendMessage("%s -> Only mods can add quotes!", user.getName());
                }
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (user.isMod()) {
                    try {
                        int num = Integer.valueOf(args[2]);

                        if (num <= 0 || EssentialsCore.getInstance().getQuotes().size() < num) {
                            Bot.getInstance().sendMessage("%s -> There is no quote number %s", user.getName(), num);
                            return;
                        }

                        Bot.getInstance().sendMessage("\'%s\' was removed from the quotes list!", EssentialsCore.getInstance().getQuotes().get(num - 1));
                        EssentialsCore.getInstance().getQuotes().remove(num - 1);
                    } catch (NumberFormatException exception) {
                        Bot.getInstance().sendMessage("%s -> \'%s\' isn\'t a number!", user.getName(), args[2]);
                    }
                }
            } else if (Integer.valueOf(args[1]) != null) {
                try {
                    int num = Integer.valueOf(args[1]);

                    if (num <= 0 || EssentialsCore.getInstance().getQuotes().size() < num) {
                        Bot.getInstance().sendMessage("%s -> There is no quote number %s", user.getName(), num);
                        return;
                    }

                    Bot.getInstance().sendMessage(EssentialsCore.getInstance().getQuotes().get(num - 1));
                } catch (NumberFormatException exception) {
                    Bot.getInstance().sendMessage("%s -> \'%s\' isn't a number!", user.getName(), args[1]);
                }
            } else {
                Bot.getInstance().sendMessage("%s -> That is invalid command usage!", user.getName());
            }
        }
    }
}
