package com.dragovorn.dragonbot.essentials.command;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.Bot;
import com.dragovorn.dragonbot.bot.User;
import com.dragovorn.dragonbot.essentials.Main;
import com.dragovorn.dragonbot.essentials.utils.TimeUnit;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Dragovorn
 * @since 1.00a
 *
 * This is the uptime command, it interfaces with the Twitch API to fetch the time the
 * stream went live, it then calculates the difference between the dates and applies a
 * short algorithm to split the difference into days hours minutes seconds.
 */
public class Uptime extends Command {

    public Uptime() {
        super("uptime", 0, true);
    }

    @Override
    public void execute(User user, String[] args) {
        try {
            JSONObject stream;

            try {
                stream = (JSONObject) Main.getInstance().getTwitchAPI().getStream(Bot.getInstance().getChannel()).get("stream");
            } catch (ClassCastException exception) {
                DragonBot.getInstance().sendMessage("%s isn\'t live!", Bot.getInstance().getChannel());
                return;
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parse = format.parse(stream.getString("created_at"));

            long difference = System.currentTimeMillis() - parse.getTime();

            int days = TimeUnit.DAY.convert(difference);
            long remainder = TimeUnit.DAY.remainder(difference);
            int hours = TimeUnit.HOUR.convert(remainder);
            remainder = TimeUnit.HOUR.remainder(remainder);
            int minutes = TimeUnit.MINUTE.convert(remainder);
            remainder = TimeUnit.MINUTE.remainder(remainder);
            int seconds = TimeUnit.SECOND.convert(remainder);

            StringBuilder builder = new StringBuilder();

            if (days > 0) {
                builder.append(days).append(" day").append((days > 1 ? "s, " : ", "));
            }

            if (hours > 0) {
                builder.append(hours).append(" hour").append((hours > 1 ? "s, " : ", "));
            }

            if (minutes > 0) {
                builder.append(minutes).append(" minute").append((minutes > 1 ? "s, " : ", "));
            }

            if (seconds > 0) {
                if (builder.length() > 0) {
                    builder.append("and ");
                }

                builder.append(seconds).append(" second").append((seconds > 1 ? "s" : ""));
            } else {
                builder.replace(builder.length() - 2, builder.length(), "");
            }


            DragonBot.getInstance().sendMessage("%s has been live for %s!", Bot.getInstance().getChannel(), builder.toString().trim());
        } catch (Exception exception) {
            DragonBot.getInstance().sendMessage("I couldn't connect to the twitch api!");
        }
    }
}