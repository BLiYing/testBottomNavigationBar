package com.example.wangchang.testbottomnavigationbar.http;import com.google.gson.Gson;import java.io.IOException;import okhttp3.Interceptor;import okhttp3.MediaType;import okhttp3.Protocol;import okhttp3.Request;import okhttp3.Response;import okhttp3.ResponseBody;/** * Created by liying on 2017-2-24. */public class MockInterceptor implements Interceptor {    @Override    public Response intercept(Chain chain) throws IOException {        Gson gson = new Gson();        Response response = null;        Response.Builder responseBuilder = new Response.Builder()                .code(200)                .message("")                .request(chain.request())                .protocol(Protocol.HTTP_1_0)                .addHeader("content-type", "application/json");        Request request = chain.request();        if(request.url().equals("https://api.douban.com/v2/movie/top250?start=100,count=100")) { //拦截指定地址            String responseString = "{\n" +    //模拟数据返回body                    "\t\"code\": 200,\n" +                    "\t\"message\": \"success\",\n" +                    "\t\"data\": {}\n" +                    "}";            responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()));//将数据设置到body中            response = responseBuilder.build(); //builder模式构建response        }else{            response = chain.proceed(request);        }        return response;    }}