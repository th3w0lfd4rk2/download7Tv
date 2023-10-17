package org.wolfd4rk.request;

import okhttp3.*;
import org.wolfd4rk.config.AppConfig;
import org.wolfd4rk.model.Emote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurlRequest {

    public CurlRequest(String page, int pageNumber, String limit, String query, String filter, String sort) {
        this.page = page;
        this.pageNumber = pageNumber;
        this.limit = limit;
        this.query = query;
        this.filter = filter;
        this.sort = sort;
    }

    private static final AppConfig appconfig = new AppConfig();

    private final String page;

    private final int pageNumber;

    private final String limit;

    private final String query;

    private final String filter;

    private final String sort;

    private static final String url = "https://7tv.io/v3/gql";


    public List<Emote> makeRequest(){

        List<Emote> emotes = new ArrayList<>();

        RequestBody body = createBody();

        Headers headers = createHeaders();

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();

        try(Response response = new OkHttpClient().newCall(request).execute()) {

            if (response.isSuccessful()){

                String responseBody = response.body() != null ? response.body().string() : "";

                //TODO tratar response a json y guardas info en emote

            }


        } catch (IOException e) {

            System.out.println("Ha petao en la llamada con estos datos: ");

            System.out.println("Request: " + request);
            System.out.println("Body: " + body);
            System.out.println("Headers: " + headers);

        }

        return emotes;
    }

    private Headers createHeaders() {

        return new Headers.Builder()
                .add("accept, */*")
                .add("accept-language", "es-ES,es;q=0.9")
                .add("Authorization, Bearer " + appconfig.getBearer())
                .add("content-type", "application/json")
                .add("origin", "https://7tv.app")
                .add("referer", "https://7tv.app/")
                .add("sec-fetch-dest", "empty")
                .add("sec-fetch-mode", "cors")
                .add("sec-fetch-site", "cross-site")
                .add(
                        "user-agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 OPR/95.0.0.0"
                )
                .add("accept, */*")
                .build();
    }

    private RequestBody createBody() {

        StringBuilder body = new StringBuilder("{\"operationName\":\"SearchEmotes\",\"variables\":{\"query\":\"\",\"limit\":45,\"page\":");

        body.append(pageNumber).append(",\"sort\":{\"value\":\"popularity\",\"order\":\"DESCENDING\"},\"filter\":{\"category\":\"TOP\",\"exact_match\":false,\"case_sensitive\":false,\"ignore_tags\":false,\"zero_width\":false,\"animated\":false,\"aspect_ratio\":\"\"}},\"query\":\"query SearchEmotes(");

        body.append(query).append(": String!,").append(page).append(": Int,").append(sort).append(": Sort,")
                .append(limit).append(": Int,").append(filter).append(": EmoteSearchFilter) {\\n  emotes(query: ")
                .append(query).append(", page: ").append(page).append(", sort: ").append(sort).append(", limit: ")
                .append(limit).append(", filter: ").append(filter)
                .append(") {\\n    count\\n    items {\\n      id\\n      name\\n      state\\n      trending\\n      owner {\\n        id\\n        username\\n        display_name\\n        style {\\n          color\\n          paint_id\\n          __typename\\n        }\\n        __typename\\n      }\\n      flags\\n      host {\\n        url\\n        files {\\n          name\\n          format\\n          width\\n          height\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\"}\"");

        System.out.println("body is: " + body);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        return RequestBody.create(body.toString(), JSON);
    }


}
