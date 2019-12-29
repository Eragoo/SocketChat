package com.Erag0.JavaClient;

import com.Erag0.JavaClient.GUI.ChatWindow;
import com.Erag0.JavaClient.GUI.LoginWindow;
import com.Erag0.JavaClient.Network.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Network.setUp();
        LoginWindow.display();
    }
}
