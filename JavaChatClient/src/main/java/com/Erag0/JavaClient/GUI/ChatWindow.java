package com.Erag0.JavaClient.GUI;

import com.Erag0.JavaClient.Network.IncomingReader;
import com.Erag0.JavaClient.Network.Network;
import com.Erag0.JavaClient.User;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChatWindow {
    private static Stage stage;
    private static TextArea textArea;
    private static BufferedReader reader;
    private static PrintWriter writer;

    public static void display() {
        stage = new Stage();
        stage.setTitle("Chat");
        //MainPane
        VBox mainPane = new VBox();
        mainPane.setAlignment(Pos.CENTER);

        //HeadPane
        HBox headPane = new HBox();
        headPane.setPadding(new Insets(15,15,15,15));
        headPane.setAlignment(Pos.TOP_CENTER);

        //BottomPane
        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(15,15,15,15));
        bottomPane.setAlignment(Pos.BOTTOM_CENTER);

        //ScrollPane
        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setMinSize(50,100);
        scrollPane.setPrefSize(350,450);
        scrollPane.setMaxSize(700,800);

        FlowPane labelContentPane = new FlowPane();
        labelContentPane.setOrientation(Orientation.VERTICAL);
        labelContentPane.setAlignment(Pos.TOP_LEFT);
        Label messagesLabel = new Label("");
        messagesLabel.setMinWidth(30);
        messagesLabel.setPrefWidth(330);
        messagesLabel.setMaxWidth(670);

        messagesLabel.setWrapText(true);

        labelContentPane.getChildren().add(messagesLabel);

        scrollPane.setContent(labelContentPane);
        VBox scrollBox = new VBox();
        scrollBox.getChildren().add(scrollPane);

        headPane.getChildren().add(scrollBox);

        //TextArea
        textArea = new TextArea();
        textArea.setPrefWidth(250);
        textArea.setPrefHeight(20);
        textArea.setPromptText("Введите сообщение");
        textArea.setWrapText(true);
        textArea.setOnKeyPressed(event ->  {
            if(event.getCode().toString().equals("ENTER")){
                textArea.clear();
            }
        });

        bottomPane.getChildren().add(textArea);
        bottomPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                sendButtonClickEvent();
            }
        });

        //SendButton
        Button sendButton = new Button("Send");
        sendButton.setOnAction(actionEvent -> {
            sendButtonClickEvent();
        });
        HBox buttonBox = new HBox(sendButton);
        buttonBox.setPadding(new Insets(0,0,0,3));
        bottomPane.getChildren().add(buttonBox);

        //AddComponents
        mainPane.getChildren().add(headPane);
        mainPane.getChildren().add(bottomPane);

        //SetUp TASK
        IncomingReader incomingReader = new IncomingReader(Network.getReader(), messagesLabel);
        messagesLabel.textProperty().bind(incomingReader.messageProperty());

        Thread thread = new Thread(incomingReader);
        thread.setDaemon(true);
        thread.start();

        //Show
        Scene root = new Scene(mainPane, 450,550);
        stage.setScene(root);
        stage.show();
    }

    private static void sendButtonClickEvent() {
        String text = textArea.getText();
        if (text.trim().length() > 0){
            String message =getCurrentTime() + " " + User.getUsername() + ": " + textArea.getText();
            Network.send(message);
            textArea.clear();
        }
    }


    private static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    private static void setReader(BufferedReader reader) {
        ChatWindow.reader = reader;
    }

    private static void setWriter(PrintWriter writer) {
        ChatWindow.writer = writer;
    }
}