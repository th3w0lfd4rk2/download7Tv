package org.wolfd4rk;

import org.json.JSONException;
import org.wolfd4rk.model.Emote;
import org.wolfd4rk.request.DownloadEmotes7TV;
import org.wolfd4rk.request.mapper.EmoteMapper;
import org.wolfd4rk.util.DownloadImageUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) throws JSONException {

        DownloadEmotes7TV request = new DownloadEmotes7TV(1);

        List<Emote> emotes = EmoteMapper.mapJson(request.makeRequest());

        DownloadImageUtil downloadImageUtil = new DownloadImageUtil("C:\\Users\\israel\\Documents\\emotes7TV");

        downloadImageUtil.downloadImages(emotes, "png");
        downloadImageUtil.downloadImages(emotes, "gif");
    }
}