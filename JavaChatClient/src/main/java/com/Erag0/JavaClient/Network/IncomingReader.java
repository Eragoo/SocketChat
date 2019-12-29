package com.Erag0.JavaClient.Network;

import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;

public class IncomingReader extends Task {
    private BufferedReader reader;
    private Label messagesLabel;

    public IncomingReader(BufferedReader reader, Label messagesLabel) {
        this.reader = reader;
        this.messagesLabel = messagesLabel;
    }


    @Override
    protected Object call() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                String newMessage = messagesLabel.getText() + "\n" + message;
                System.out.println(message);
                updateMessage(newMessage);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
