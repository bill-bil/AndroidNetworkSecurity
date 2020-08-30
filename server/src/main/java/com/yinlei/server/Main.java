package com.yinlei.server;

import com.yinlei.server.http.HttpCallback;
import com.yinlei.server.http.HttpServer;

public class Main {
    public static void main(String[] args) {
        HttpServer server = new HttpServer(new HttpCallback() {
            @Override
            public byte[] onResponse(String request) {
                return "yinlei".getBytes();
            }
        });
        server.startHttpServer();
    }
}
