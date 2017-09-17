package com.dragovorn.dragonbot.essentials.command;

import com.dragovorn.dragonbot.DragonBot;
import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.User;
import com.dragovorn.dragonbot.essentials.Main;
import com.dragovorn.dragonbot.essentials.utils.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Dragovorn
 * @since 1.0.1
 *
 * A command that queries the Twitch API and figures out when the user followed the
 * current channel, and then calculates the difference and applies a short
 * algorithm to split the difference calculated into year/months/days/hours/seconds
 */
public class FollowAge extends Command {

    public FollowAge() {
        super("followage", 0, true);
    }

    @Override
    public void execute(User user, String[] strings) {
        try {
            Date date = poll(user.getName(), "");

            if (date == null) {
                DragonBot.getInstance().sendMessage("%s -> You don\'t appear to be following %s...", user.getName(), DragonBot.getInstance().getChannel());
                return;
            }

            long difference = System.currentTimeMillis() - date.getTime();

            int years = TimeUnit.YEAR.convert(difference);
            long remainder = TimeUnit.YEAR.remainder(difference);
            int months = TimeUnit.MONTH.convert(remainder);
            remainder = TimeUnit.MONTH.remainder(remainder);
            int days = TimeUnit.DAY.convert(remainder);
            remainder = TimeUnit.DAY.remainder(remainder);
            int hours = TimeUnit.HOUR.convert(remainder);
            remainder = TimeUnit.HOUR.remainder(remainder);
            int minutes = TimeUnit.MINUTE.convert(remainder);
            remainder = TimeUnit.MINUTE.remainder(remainder);
            int seconds = TimeUnit.SECOND.convert(remainder);

            StringBuilder builder = new StringBuilder();

            if (years > 0) {
                builder.append(years).append(" year").append((years > 1 ? "s, " : ", "));
            }

            if (months > 0) {
                builder.append(months).append(" month").append((months > 1 ? "s, " : ", "));
            }

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

            DragonBot.getInstance().sendMessage("%s -> Your Follow Age is %s!", user.getName(), builder.toString().trim());
        } catch (IOException exception) {
            DragonBot.getInstance().sendMessage("Failed to connect to the Twitch API!");
        }
    }

    private Date poll(String name, String cursor) throws IOException {
        JSONObject object;

        if (cursor.equalsIgnoreCase("")) {
            object = Main.getInstance().getTwitchAPI().getFollowers(DragonBot.getInstance().getChannel());
        } else {
            object = Main.getInstance().getTwitchAPI().getFollowers(DragonBot.getInstance().getChannel(), cursor);
        }

        JSONArray array = object.getJSONArray("follows");

        for (int x = 0; x < array.length(); x++) {
            if (array.getJSONObject(x).getJSONObject("user").getString("display_name").equalsIgnoreCase(name)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));

                try {
                    return format.parse(array.getJSONObject(x).getString("created_at"));
                } catch (ParseException exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
        }

        if (!object.has("_cursor")) {
            return null;
        }

        return poll(name, object.getString("_cursor"));
    }
}