import com.dragovorn.dragonbot.Keys;
import com.dragovorn.dragonbot.api.twitch.TwitchAPI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.fail;

public class TestFollowAge {

    private TwitchAPI twitchAPI = new TwitchAPI(Keys.twitchClientID);

    @Test
    public void test() {
        try {
            System.out.println("Starting...");

            System.out.println("Polling...");

            Date date = poll("Dragovorn", "");

            System.out.println("Polled...");

            if (date == null) {
                System.out.println("Not following");
                return;
            }

            System.out.println("Decoding...");

            long difference = System.currentTimeMillis() - date.getTime();

            int days = (int) difference / 86400000;
            int remainder = (int) difference % 86400000;
            int hours = remainder / 3600000;
            remainder = remainder % 3600000;
            int minutes = remainder / 60000;
            remainder = remainder % 60000;
            int seconds = remainder / 1000;

            StringBuilder builder = new StringBuilder();

            System.out.println("Decoded...");

            System.out.println(days + " " + hours + " " + minutes + " " + seconds);

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
            }

            System.out.println(builder.toString().trim());
        } catch (IOException exception) {
            exception.printStackTrace();
            fail();
        }
    }

    private Date poll(String name, String cursor) throws IOException {
        JSONObject object;

        if (cursor.equalsIgnoreCase("")) {
            object = this.twitchAPI.getFollowers("swordmas_");
        } else {
            object = this.twitchAPI.getFollowers("swordmas_", cursor);
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