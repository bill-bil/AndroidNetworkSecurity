package com.yinlei.server.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private boolean mRunning;
    private HttpCallback mCallback;

    public HttpServer(HttpCallback callback) {
        this.mCallback = callback;
    }

    /**
     * 编写接口函数，用于启动服务
     */
    public void startHttpServer() {
        if (mRunning){
            return;
        }
        mRunning = true;
        // 启动SocketServer
        try {
            ServerSocket serverSocket = new ServerSocket(80);
            while (mRunning) {
                Socket socket = serverSocket.accept();
                ExecutorService threadPool = Executors.newCachedThreadPool();
                threadPool.execute(new HttpThread(socket, mCallback));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
