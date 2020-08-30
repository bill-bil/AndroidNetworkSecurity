package com.yinlei.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
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

    public static Map<String, String> getHeader(String request) {
        try {
            Map<String, String> header = new HashMap<>();
            BufferedReader bufferedReader = new BufferedReader(new StringReader(request));
            String line = bufferedReader.readLine();
            while (line != null && !line.trim().isEmpty()) {
                int p = line.indexOf(':');
                if (p>=0){
                    header.put(line.substring(0, p).trim().toLowerCase(), line.substring(p+1).trim());
                }
                line = bufferedReader.readLine();
            }
            return header;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
