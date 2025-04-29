package com.classig.chatbot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatBotApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatBotApplication.class.getResource("login-view.fxml"));
        stage.getIcons().add(new Image("file:src/main/resources/com/classig/chatbot/iconbot.png")); // добавляем иконку
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("ChatBot!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}