package com.Erag0.JavaClient.Network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Network {
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;

    public static void setUp() throws IOException {
        closeAll();
        Network.socket = new Socket("127.0.0.1", 50000);
        Network.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        Network.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

    }

    public static void send(String msg) {
        writer.println(msg);
        writer.flush();
    }

    private static void closeAll() {
        if (socket != null){
            try{
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (reader != null) {
            try{
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (writer != null) {
            writer.close();
        }
    }

    public static BufferedReader getReader() {
        return reader;
    }

}
