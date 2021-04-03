package com.example.trafficquery.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class KenUtils {

    /**
     * GET请求，不需要Token
     * @param url
     * @return
     * @throws IOException
     */
    public static String Get(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).method("GET",null).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }


}
