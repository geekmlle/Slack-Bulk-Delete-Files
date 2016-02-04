package slackapibulkdelete;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author geekmlle
 */

public class Main {

    static String USERID = "CHANGE THIS FOR YOUR USER ID ON THE SLACK API";
    static String USERTOKEN = "CHANGE THIS FOR YOUR SPECIFIC TOKEN";

    public static void main(String[] args) throws IOException {
        Main example = new Main();
        ArrayList files = readImageList();
        for (Object file : files) {
            String response = example.post("https://slack.com/api/files.delete?token=" + USERTOKEN + "&file=" + file.toString());
            System.out.println(file.toString() + " " + response);
        }

    }

    public static final MediaType TEXT
            = MediaType.parse("application/text; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static ArrayList readImageList() throws IOException {
        Main example = new Main();
        String response = example.post("https://slack.com/api/files.list?token=" + USERTOKEN + "&user=" + USERID);
        ArrayList temp = new ArrayList();
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response);

            JSONObject jsonObject = (JSONObject) json;

            JSONArray files = (JSONArray) jsonObject.get("files");
            int num = 0;
            for (Object o : files) {
                JSONObject object = (JSONObject) o;
                temp.add(object.get("id"));
                System.out.println(num + " " + object.get("id"));
                num++;
            }

        } catch (Exception e) {
        }
        return temp;
    }
}
