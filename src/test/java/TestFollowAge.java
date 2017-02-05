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
            Date date = poll("bob is great ", "");

            if (date == null) {
                System.out.println("Not following");
                return;
            }

            long difference = System.currentTimeMillis() - date.getTime();

            int years = (int) (difference / (long) (3.154 * Math.pow(10, 10)));
            long remainder = difference % (long) (3.154 * Math.pow(10, 10));
            int months = (int) (remainder / (long) (2.628 * Math.pow(10, 9)));
            remainder = difference % (long) (2.628 * Math.pow(10, 9));
            int days = (int) (remainder / (long) (8.64 * Math.pow(10, 7)));
            remainder = remainder % (long) (8.64 * Math.pow(10, 7));
            int hours = (int) (remainder / (long) (3.6 * Math.pow(10, 6)));
            remainder = remainder % (long) (3.6 * Math.pow(10, 6));
            int minutes = (int) (remainder / (long) (6 * Math.pow(10, 4)));
            remainder = remainder % (long) (6 * Math.pow(10, 4));
            int seconds = (int) (remainder / (long) (1 * Math.pow(10, 3)));


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