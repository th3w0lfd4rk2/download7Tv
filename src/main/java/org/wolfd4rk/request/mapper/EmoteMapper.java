package org.wolfd4rk.request.mapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wolfd4rk.model.Emote;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmoteMapper {

    private EmoteMapper() {

    }
    public static List<Emote> mapJson(String response) throws JSONException {

        JSONObject responseJSONObject = new JSONObject(response);

        JSONArray items = responseJSONObject.getJSONObject("data").getJSONObject("emotes").getJSONArray("items");

        List<Emote> emotes = IntStream.range(0, items.length()).mapToObj(i -> {

            try {
                JSONObject item = items.getJSONObject(i);

                return new Emote(item.getString("name"), "https:" + item.getJSONObject("host").getString("url") + "/4x.png");

            } catch (JSONException e) {
                System.out.println("Error retrieving emote info");
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        System.out.println("Emotes mapped: " + emotes);

        return emotes;
    }


}
