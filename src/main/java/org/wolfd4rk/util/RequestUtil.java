package org.wolfd4rk.util;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.wolfd4rk.config.AppConfig;

public class RequestUtil {

    private RequestUtil(){

    }
    private static final AppConfig appconfig = new AppConfig();

    public static Headers createHeaders() {

        return new Headers.Builder()
                .add("Accept", "*/*")
                .add("Accept-language", "es-ES,es;q=0.9")
                .add("Authorization", "Bearer " + appconfig.getBearer())
                .add("Content-type", "application/json")
                .add("Origin", "https://7tv.app")
                .add("Referer", "https://7tv.app/")
                .add("Sec-fetch-dest", "empty")
                .add("Sec-fetch-mode", "cors")
                .add("Sec-fetch-site", "cross-site")
                .add(
                        "User-agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 OPR/95.0.0.0"
                )
                .build();
    }

    public static RequestBody createBody(int pageNumber) {

        StringBuilder body = new StringBuilder("{\"operationName\":\"SearchEmotes\",\"variables\":{\"query\":\"\",\"limit\":45,\"page\":");

        body.append(pageNumber).append(",\"sort\":{\"value\":\"popularity\",\"order\":\"DESCENDING\"},\"filter\":{\"category\":\"TOP\",\"exact_match\":false,\"case_sensitive\":false,\"ignore_tags\":false,\"zero_width\":false,\"animated\":false,\"aspect_ratio\":\"\"}},\"query\":\"query SearchEmotes(");

        body.append("$query").append(": String!,").append("$page").append(": Int,").append("$sort").append(": Sort,")
                .append("$limit").append(": Int,").append("$filter").append(": EmoteSearchFilter) {\\n  emotes(query: ")
                .append("$query").append(", page: ").append("$page").append(", sort: ").append("$sort").append(", limit: ")
                .append("$limit").append(", filter: ").append("$filter")
                .append(") {\\n    count\\n    items {\\n      id\\n      name\\n      state\\n      trending\\n      owner {\\n        id\\n        username\\n        display_name\\n        style {\\n          color\\n          paint_id\\n          __typename\\n        }\\n        __typename\\n      }\\n      flags\\n      host {\\n        url\\n        files {\\n          name\\n          format\\n          width\\n          height\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\"}\"");

        System.out.println("body is: " + body);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        return RequestBody.create(body.toString(), JSON);
    }
}
