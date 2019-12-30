package com.Erag0.JavaClient.GUI;

import com.Erag0.JavaClient.Network.Network;
import com.Erag0.JavaClient.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginWindow {
    private static Stage stage;
    private static TextArea textArea;
    private static BufferedReader reader;
    private static PrintWriter writer;

    public static void display() {
        stage = new Stage();
        stage.setTitle("Login Chat");

        //VBox MAIN
        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER);

        //HBox label
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.TOP_CENTER);
        labelBox.setPadding(new Insets(5,5,5,5));

        //HBox Menu
        HBox menuBox = new HBox();
        menuBox.setAlignment(Pos.BOTTOM_CENTER);
        menuBox.setPadding(new Insets(5,5,5,5));

        //Label
        Label label = new Label("Введите имя пользователя");
        labelBox.getChildren().add(label);


        //TextArea
        textArea = new TextArea();
        textArea.setPrefWidth(250);
        textArea.setPrefHeight(15);
        textArea.setPromptText("Введите сообщение");
        textArea.setWrapText(true);
        textArea.setOnKeyPressed(event ->  {
                if(event.getCode().toString().equals("ENTER")){
                    textArea.clear();
                };
            });

        menuBox.getChildren().add(textArea);
        menuBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                sendButtonClickEvent();
            }
        });

        //SendButton
        Button sendButton = new Button("Send");
        HBox buttonBox = new HBox(sendButton);
        buttonBox.setPadding(new Insets(0,0,0,3));
        sendButton.setOnAction(actionEvent -> {
            sendButtonClickEvent();
        });
        menuBox.getChildren().add(buttonBox);


        //AddComponents
        mainBox.getChildren().add(labelBox);
        mainBox.getChildren().add(menuBox);

        Scene root = new Scene(mainBox,340,300);
        stage.setScene(root);
        stage.show();
    }

    private static void sendButtonClickEvent() {
        String text = textArea.getText();
        if (text.trim().length() > 0){
            User.setUsername(textArea.getText());
            Network.send( User.getUsername() + "  присоединился!");
            stage.close();
            ChatWindow.display();
        }
    }

    private static void setReader(BufferedReader reader) {
        LoginWindow.reader = reader;
    }

    private static void setWriter(PrintWriter writer) {
        LoginWindow.writer = writer;
    }
}
