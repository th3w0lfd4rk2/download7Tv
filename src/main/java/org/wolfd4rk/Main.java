package org.wolfd4rk;

import okhttp3.Response;
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

        for(int i = 1; i <= 10; i++){

            String response = obtainResponse(CurlRequest.makeRequest("https://7tv.io/v3/gql", RequestUtil.createHeaders(), RequestUtil.createBody(i)));

            List<Emote> emotes = EmoteMapper.mapJson(response);

            DownloadImageUtil downloadImageUtil = new DownloadImageUtil("E:\\memes\\emotes7TV");

            downloadImageUtil.downloadImages(emotes, "png");
            downloadImageUtil.downloadImages(emotes, "gif");

            System.out.println("RUN: " + i);

        }
    }

    private static String obtainResponse(Response requestResponse) {

        String response = "";

        if (requestResponse != null && requestResponse.body() != null) {

            try (requestResponse) {
                response = requestResponse.body().string();
                requestResponse.body().close();
            } catch (IOException e) {
                System.out.println("Error getting response body");
            }

        }

        return response;
    }
}