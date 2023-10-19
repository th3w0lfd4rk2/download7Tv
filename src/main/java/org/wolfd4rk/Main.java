package org.wolfd4rk;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.wolfd4rk.model.Emote;
import org.wolfd4rk.request.CurlRequest;
import org.wolfd4rk.request.mapper.EmoteMapper;
import org.wolfd4rk.util.DownloadImageUtil;
import org.wolfd4rk.util.RequestUtil;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JSONException {

        String response = obtainResponse(CurlRequest.makeRequest("https://7tv.io/v3/gql", RequestUtil.createHeaders(), RequestUtil.createBody(1)));

        List<Emote> emotes = EmoteMapper.mapJson(response);

        DownloadImageUtil downloadImageUtil = new DownloadImageUtil("C:\\Users\\israel\\Documents\\emotes7TV");

        downloadImageUtil.downloadImages(emotes, "png");
        downloadImageUtil.downloadImages(emotes, "gif");
    }

    private static String obtainResponse(ResponseBody responseBody) {

        String response = "";

        if (responseBody != null) {

            try {
                response = responseBody.string();
            } catch (IOException e) {
                System.out.println("Error getting response body");
            }

        }

        return response;
    }
}