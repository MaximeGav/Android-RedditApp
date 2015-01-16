package school.reddittestvoorexamen1.utils.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import school.reddittestvoorexamen1.Post;

public class JSONParser {

    private String result;
    private JSONObject json;

    public JSONParser(String result) throws JSONException {
        this.result = result;
        this.json = new JSONObject(result);
    }

    public Post parsePost(int index) {
        Post post = null;

        String title, secondLine, thumbnail, fullImg;
        int score;

        try {
            JSONObject dataRecord = json.getJSONObject("data");
            JSONArray childrenRecord = dataRecord.getJSONArray("children");

            //Zoek op de index
            JSONObject record = childrenRecord.getJSONObject(index);
            JSONObject subRecord = record.getJSONObject("data");

            title = subRecord.getString("title");
            secondLine = subRecord.getString("author");
            thumbnail = subRecord.getString("thumbnail");
            fullImg = subRecord.getString("url");
            score = subRecord.getInt("score");


            post = new Post(title, secondLine, thumbnail, fullImg, score);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return post;
    }

    public ArrayList<Post> parsePosts() {
        Post post = null;

        String title, secondLine, thumbnail, fullImg;
        int score;

        ArrayList<Post> posts = new ArrayList();

        try {
            JSONObject dataRecord = json.getJSONObject("data");
            JSONArray childrenRecord = dataRecord.getJSONArray("children");

            for (int i = 0; i<childrenRecord.length(); i++) {
                JSONObject record = childrenRecord.getJSONObject(i);
                JSONObject subRecord = record.getJSONObject("data");

                title = subRecord.getString("title");
                secondLine = subRecord.getString("author");
                thumbnail = subRecord.getString("thumbnail");
                fullImg = subRecord.getString("url");
                score = subRecord.getInt("score");

                post = new Post(title, secondLine, thumbnail, fullImg, score);
                posts.add(post);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
