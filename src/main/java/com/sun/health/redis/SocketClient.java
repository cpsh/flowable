package com.sun.health.redis;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by 华硕 on 2018-06-20.
 */
public class SocketClient {

    public static void main(String[] args) {
        try {
            final Socket socket = new Socket("localhost", 9362);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Scanner scanner = new Scanner(System.in);
                        while (true) {
                            String line = scanner.nextLine();
                            OutputStream outputStream = socket.getOutputStream();
                            outputStream.write(line.getBytes());
                            outputStream.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            while(true) {
                TimeUnit.SECONDS.sleep(1L);
                InputStream inputStream = socket.getInputStream();
                int available = inputStream.available();
                if(available > 0) {
                    byte[] bytes = new byte[available];
                    inputStream.read(bytes);
                    System.out.println(new String(bytes));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
