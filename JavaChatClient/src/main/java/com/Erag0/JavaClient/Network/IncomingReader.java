package com.Erag0.JavaClient.Network;

import javax.swing.*;
import java.io.BufferedReader;

public class IncomingReader implements Runnable {
    private BufferedReader reader;
    private JTextArea incoming;

    public IncomingReader(BufferedReader reader, JTextArea incoming) {
        this.reader = reader;
        this.incoming = incoming;
    }

    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                System.out.println("read :" + message);
                incoming.append(message + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
