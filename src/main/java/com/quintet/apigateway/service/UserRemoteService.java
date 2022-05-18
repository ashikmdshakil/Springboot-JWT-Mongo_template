package com.quintet.apigateway.service;

import com.google.gson.Gson;
import com.quintet.apigateway.model.User;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Scope(scopeName = "prototype")
public class UserRemoteService {
    @Autowired
    private User user;
    @Autowired
    private Gson gson;

    public User getUserByMobileNumber(String mobileNumber) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://127.0.0.1:8081/userGetsProfile").newBuilder();
        urlBuilder.addEncodedQueryParameter("number","01720024944");
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                //.addHeader("Authorization","Bearer ")
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        user = gson.fromJson(response.body().string(), User.class);
        return user;
    }
}
