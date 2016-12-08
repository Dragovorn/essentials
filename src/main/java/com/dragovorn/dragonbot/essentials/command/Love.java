package com.dragovorn.dragonbot.essentials.command;

import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.User;
import com.sun.istack.internal.NotNull;

public class Love extends Command {

    public Love() {
        super("love", -1, false);
    }

    @Override
    public void execute(@NotNull User user, @NotNull String[] args) {
        if (args.length == 1) {
            Bot.getInstance().sendMessage("%s -> Please put the name of the person/thing you want to love!", user.getName());
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (String str : args) {
            builder.append(str).append(" ");
        }

        String name = builder.toString().trim();

        String entered = name;

        for (int x = 0; x < user.getName().length(); x++) {
            for (int y = 0; y < name.length(); y++) {
                if (user.getName().charAt(x) == name.charAt(y)) {
                    name = name.replaceAll(String.valueOf(name.charAt(y)), "");
                }
            }
        }

        String names = user.getName().toUpperCase() + name.toUpperCase();

        int sum = 0;

        for (int x = 0; x < names.length(); x++) {
            sum += names.charAt(x);
        }

        Bot.getInstance().sendMessage("%s\'s love to %s is %s%%!", user.getName(), entered, sum % 101);
    }
}