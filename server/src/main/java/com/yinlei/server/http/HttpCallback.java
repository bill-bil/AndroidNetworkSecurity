package com.yinlei.server.http;

public interface HttpCallback {

    // 收到消息的回调通知
    byte[] onResponse(String request);

}
