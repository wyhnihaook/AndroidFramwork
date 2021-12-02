package com.lib.net;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        MediaType contentType = null;
        String bodyString = null;
        String bodyLog = "";
        if (response.body() != null) {
            contentType = response.body().contentType();
            bodyString = response.body().string();
        }
        // 请求响应时间
        double time = (t2 - t1) / 1e6d;
        //打印log
        logJson(request, response, bodyString, time);

        if (response.body() != null) {
            // 打印body后原ResponseBody会被清空，需要重新设置body
            ResponseBody body = ResponseBody.create(contentType, bodyString);
            return response.newBuilder().body(body).build();
        } else {
            return response;
        }
    }

    /**
     * 打印Log
     *
     * @param request
     * @param response
     * @param bodyLog
     * @param time
     */
    private void logJson(Request request, Response response, String bodyLog, double time) {
        JSONObject json = null;
        try {
            if (request == null || response == null) {
                return;
            }

            json = new JSONObject();

            JSONObject requestJson = new JSONObject();
            JSONObject responseJson = new JSONObject();


            //request-----------------------------------------------------

            requestJson.put("url", request.url() + " in " + time + " ms");
            requestJson.put("method", request.method());


            if (request.headers() != null) {
                JSONObject requestHeaderJson = new JSONObject();
                Map<String, List<String>> requsetHeadersMap = request.headers().toMultimap();
                Set<String> requsetKeys = requsetHeadersMap.keySet();
                for (String key : requsetKeys) {
                    List<String> value = requsetHeadersMap.get(key);
                    requestHeaderJson.put(key, value.get(0));
                }
                requestJson.put("headers", requestHeaderJson);
            }


            String requetBodyStr = stringifyRequestBody(request);
            requestJson.put("body", requetBodyStr);

            //response-----------------------------------------------------

            responseJson.put("code", response.code());


            if (response.headers() != null) {
                JSONObject reponseHeaderJson = new JSONObject();
                Map<String, List<String>> responseHeadersMap = response.headers().toMultimap();
                Set<String> responseKeys = responseHeadersMap.keySet();
                for (String key : responseKeys) {
                    List<String> value = responseHeadersMap.get(key);
                    reponseHeaderJson.put(key, value.get(0));
                }
                responseJson.put("headers", reponseHeaderJson);
            }
            L.e("请求数据:\n" + "地址=" + request.url() + " in " + time + " ms" + "\n" +
                    ",method=" + request.method() + "\n,body=" + requetBodyStr);

            L.e("返回数据:\n" + bodyLog.toString() + "\n");
            responseJson.put("data", bodyLog);

            json.put("requst", requestJson);
            json.put("response", responseJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        L.e(JsonUtil.formatJson(json.toString()));
//        L.e(json.toString());
    }

    private static String stringifyRequestBody(Request request) {
        try {

            Request copy = request.newBuilder().build();
            if (copy != null) {
                RequestBody body = copy.body();
                if (body != null) {
                    Buffer buffer = new Buffer();
                    body.writeTo(buffer);
                    return buffer.readUtf8();
                } else {
                    return "null";
                }

            } else {
                return "null";
            }

        } catch (final IOException e) {
            return "did not work";
        }
    }

}
