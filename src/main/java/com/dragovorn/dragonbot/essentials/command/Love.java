package com.dragovorn.dragonbot.essentials.command;

import com.dragovorn.ircbot.api.command.Command;
import com.dragovorn.ircbot.api.command.Executor;
import com.dragovorn.ircbot.api.command.Parameter;
import com.dragovorn.ircbot.api.command.ParameterType;
import com.dragovorn.ircbot.api.command.argument.StringArgument;
import com.dragovorn.ircbot.api.command.argument.annotation.Argument;
import com.dragovorn.ircbot.api.irc.IChannel;
import com.dragovorn.ircbot.api.irc.IConnection;
import com.dragovorn.ircbot.api.user.IUser;

/**
 * @author Dragovorn
 * @since 1.0.0
 *
 * This command takes in an argument which would represent the name of
 * the other user/thing, we then apply a short 'love' algorithm that
 * based on the characters of the name figures out the percent that the
 * two love each-other.
 */
@Command("love")
@Argument(key = "target", clazz = StringArgument.class, overflow = true)
public class Love {

    @Executor
    public void execute(@Parameter(ParameterType.USER) IUser user,
                        @Parameter(ParameterType.CHANNEL) IChannel channel,
                        @Parameter(value = ParameterType.ARGUMENT, name = "target") String target) {

        String entered = target;

        /* Remove duplicate letters */
        for (int x = 0; x < user.getUsername().length(); x++) {
            for (int y = 0; y < target.length(); y++) {
                if (user.getUsername().charAt(x) == target.charAt(y)) {
                    target = target.replaceAll(String.valueOf(target.charAt(y)), "");
                }
            }
        }

        /* Add the second name to the first make them both upper case so capitalization doesn't matter */
        String names = user.getUsername().toUpperCase() + target.toUpperCase();

        int sum = 0;

        /* Sum them together to get the final 'love score' */
        for (int x = 0; x < names.length(); x++) {
            sum += names.charAt(x);
        }

        channel.sendMessage(user.getUsername() + "'s love to " + entered + " is " + (sum % 101) + "%!");
    }
}