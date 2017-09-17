package com.dragovorn.dragonbot.essentials.command;

import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.User;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * This command takes in an argument which would represent the name of
 * the other user/thing, we then apply a short 'love' algorithm that
 * based on the characters of the name figures out the percent that the
 * two love each-other.
 */
public class Love extends Command {

    public Love() {
        super("love", -1, false);
    }

    @Override
    public void execute(User user, String[] args) {
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

        /* Remove duplicate letters */
        for (int x = 0; x < user.getName().length(); x++) {
            for (int y = 0; y < name.length(); y++) {
                if (user.getName().charAt(x) == name.charAt(y)) {
                    name = name.replaceAll(String.valueOf(name.charAt(y)), "");
                }
            }
        }

        /* Add the second name to the first make them both upper case so capitalization doesn't matter */
        String names = user.getName().toUpperCase() + name.toUpperCase();

        int sum = 0;

        /* Sum them together to get the final 'love score' */
        for (int x = 0; x < names.length(); x++) {
            sum += names.charAt(x);
        }

        Bot.getInstance().sendMessage("%s\'s love to %s is %s%%!", user.getName(), entered, sum % 101);
    }
}