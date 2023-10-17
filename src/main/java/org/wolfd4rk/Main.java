package org.wolfd4rk;

import org.json.JSONException;
import org.wolfd4rk.model.Emote;
import org.wolfd4rk.request.CurlRequest;
import org.wolfd4rk.request.mapper.EmoteMapper;

import java.util.List;

public class Main {
    public static void main(String[] args) throws JSONException {

        CurlRequest request = new CurlRequest(1);

        List<Emote> emotes = EmoteMapper.mapJson(request.makeRequest());

        //TODO guarda emote
    }
}