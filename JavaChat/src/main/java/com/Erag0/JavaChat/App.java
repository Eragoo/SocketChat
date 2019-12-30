package com.Erag0.JavaChat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class App
{
    private ArrayList clientOutputStreams;

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;

        public ClientHandler(Socket clientSocket) {
            try{
                socket = clientSocket;
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
                reader = new BufferedReader(inputStreamReader);

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try{
                while((message = reader.readLine()) != null) {
                    if (message.trim().length()>0){
                        System.out.println("read " + message);
                        tellEveryone(message);
                    }
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void main( String[] args ){
        new App().go();
    }

    public void go() {
        clientOutputStreams = new ArrayList();
        try{
            ServerSocket serverSocket = new ServerSocket(50000);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),StandardCharsets.UTF_8));
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tellEveryone(String message) {
        Iterator iterator = clientOutputStreams.iterator();
        while(iterator.hasNext()) {
            try{
                PrintWriter writer = (PrintWriter) iterator.next();
                writer.println(message);
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

