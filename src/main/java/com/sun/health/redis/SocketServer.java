package com.sun.health.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by 华硕 on 2018-06-20.
 */
public class SocketServer {

    public static void main(String[] args) {
        final List<Socket> socketList = new CopyOnWriteArrayList<Socket>();
        try {
            final ServerSocket serverSocket = new ServerSocket(9362);
//            while(true) {
//                Socket socket = serverSocket.accept();
//                socketList.add(socket);
//                System.out.println("新连接连接");
//                byte[] bytes = ("Welcome, " + new Date().toString()).getBytes();
//                byte[] otherBytes = "Something else!".getBytes();
//                for (Socket s : socketList) {
//                    OutputStream outputStream = s.getOutputStream();
//                    outputStream.write(bytes);
//                    outputStream.flush();
//                    outputStream.write(otherBytes);
//                    outputStream.flush();
//                }
//            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(true) {
                            Socket socket = serverSocket.accept();
                            socketList.add(socket);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if(serverSocket != null) {
                                serverSocket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            InputStream inputStream = null;
            OutputStream outputStream = null;
            byte[] bytes = null;
            while(true) {
                for (Socket inSocket : socketList) {
                    inputStream = inSocket.getInputStream();
                    int available = inputStream.available();
                    if(available > 0) {
                        bytes = new byte[available];
                        inputStream.read(bytes);
                        for (Socket outSocket : socketList) {
                            outputStream = outSocket.getOutputStream();
                            outputStream.write(bytes);
                            outputStream.flush();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for (Socket socket : socketList) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
