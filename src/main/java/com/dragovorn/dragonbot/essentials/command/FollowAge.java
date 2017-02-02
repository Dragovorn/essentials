package com.dragovorn.dragonbot.essentials.command;

import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.bot.DragonBot;
import com.dragovorn.dragonbot.bot.User;
import com.sun.istack.internal.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FollowAge extends Command {

    public FollowAge() {
        super("followage", 0, true);
    }

    @Override
    public void execute(@NotNull User user, @NotNull String[] strings) {

        Date followAge;

        try {
            followAge = getFollowAge(user.getName());
        } catch (IOException exception) {
            DragonBot.getInstance().sendMessage("Failed to connect to the Twitch API!");
            return;
        }

        if (followAge == null) {
            DragonBot.getInstance().sendMessage("%s -> You don't appear to be following %s...", user.getName(), DragonBot.getInstance().getChannel());
        }

        DragonBot.getInstance().sendMessage("%s -> Your Follow Age is %s!", user.getName(), 1);
    }

    private Date getFollowAge(String name) throws IOException {
        return poll(name, "");
    }

    private Date poll(String name, String cursor) throws IOException {
        JSONObject object;

        if (cursor.equalsIgnoreCase("")) {
            object = DragonBot.getInstance().getTwitchAPI().getFollowers(DragonBot.getInstance().getChannel());
        } else {
            object = DragonBot.getInstance().getTwitchAPI().getFollowers(DragonBot.getInstance().getChannel(), cursor);
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
                }
            }
        }

        if (!object.has("_cursor")) {
            return null;
        }

        return poll(name, object.getString("_cursor"));
    }
}