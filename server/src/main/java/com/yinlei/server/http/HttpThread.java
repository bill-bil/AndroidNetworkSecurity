package com.yinlei.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HttpThread implements Runnable{

    private Socket mSocket;
    private HttpCallback mCallback;

    public HttpThread(Socket socket, HttpCallback httpCallback) {
        mSocket = socket;
        mCallback = httpCallback;
    }

    @Override
    public void run() {
        // 1. 读取客户端请求
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            String content;
            StringBuilder request = new StringBuilder();
            while ((content = reader.readLine()) != null && !content.trim().isEmpty()) {
                request.append(content).append("\n");
            }
            System.out.println("request:\n" + request.toString());
            // 2. 根据业务数据采取相关的操作
            // 3. 向客户端返回数据
            byte[] response = new byte[0];
            if (mCallback != null) {
                response = mCallback.onResponse(request.toString());
            }
            // 包装为http格式
            String responseFirstLine = "HTTP/1.1 200 \r\n";
            String responseHeader = "Content-type:" + "text/html" + "\r\n";
            OutputStream outputStream = mSocket.getOutputStream();
            outputStream.write(responseFirstLine.getBytes());
            outputStream.write(responseHeader.getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write(response); // 消息体body
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
