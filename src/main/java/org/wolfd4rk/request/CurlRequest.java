package org.wolfd4rk.request;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CurlRequest {

    private CurlRequest(){

    }

    public static ResponseBody makeRequest(String url, Headers headers, RequestBody body){

        Request request = buildRequest(url, headers, body).build();

        try(Response response = new OkHttpClient().newCall(request).execute()) {

            if (response.isSuccessful()){

                System.out.println("Response is: " + response);

                return response.body();

            }


        } catch (IOException e) {

            System.out.println("Ha petao en la llamada con estos datos: ");

            System.out.println("Request: " + request);
            System.out.println("Body: " + body);
            System.out.println("Headers: " + headers);

        }

        return null;
    }

    @NotNull
    private static Request.Builder buildRequest(String url, Headers headers, RequestBody body) {
        Request.Builder builder = new Request.Builder()
                .url(url);

        if (headers != null){
            builder.headers(headers);
        }

        if (body != null){
            builder.post(body);
        }
        return builder;
    }

}
