package com.yinlei.androidnetworksecurity.http;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpRequest {

    private static final String HANDSHAKE = "handshake";
    //    private Call mCall;
    private Request.Builder mBuilder;

    public HttpRequest(String url) {
        mBuilder = new Request.Builder()
                .get()
                .url(url);

//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .get()
//                .url(url)
//                .build();
//        mCall = client.newCall(request);
    }


    // 发起握手请求，目的是与对方交换公钥
    public void handshake(Callback callback, String pubKey) {
            mBuilder.addHeader(HANDSHAKE, pubKey);
            request(callback);
            mBuilder.removeHeader(HANDSHAKE);
    }

    public void request(Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(mBuilder.build());
        call.enqueue(callback);
//        if (mCall != null) {
//            if (mCall.isExecuted()) {
//                mCall.clone().enqueue(callback);
//            } else {
//                mCall.enqueue(callback);
//            }
//        }
    }

}
