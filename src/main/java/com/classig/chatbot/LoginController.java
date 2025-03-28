package com.classig.chatbot;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {

    @FXML
    TextField LoginText;

    public String setName(String name) throws RuntimeException
    {
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я\\s]+");
        Matcher mather = pattern.matcher(name);
        if (mather.matches())
        {
            return name;
        }
        else
        {
            name = "Name Surname";
            throw new RuntimeException("Ошибка: неверно введено ФИО пользователя");
        }
    }
    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) throws IOException
    {
        try
        {
            String inputname = setName(LoginText.getText());
            ChatController chatController = new ChatController();
            chatController.name = inputname;
            FXMLLoader fxmlLoader = new FXMLLoader(ChatBotApplication.class.getResource("chat-view.fxml"));
            fxmlLoader.setController(chatController);
            Scene scene = new Scene(fxmlLoader.load(), 518.0, 365.0);
            Stage stage = new Stage();
            stage.setTitle("Chat");
            stage.setScene(scene);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    chatController.onSaveClick();
                    Platform.exit();
                }
            });
            stage.show();
            LoginText.getScene().getWindow().hide();
        }
        catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText(e.getMessage());
            error.setContentText("Имя должно состоять только из букв и пробела");
            error.showAndWait();
        }
    }

}