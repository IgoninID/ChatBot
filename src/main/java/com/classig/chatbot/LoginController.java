package com.classig.chatbot;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Контроллер окна входа
 */
public class LoginController {

    /**
     * Поле ввода имени пользователя
     */
    @FXML
    TextField LoginText;

    /**
     * Проверка имени пользователя
     * Правило ввода: только буквы и пробелы
     * @param name введенное имя
     * @return имя пользователя
     * @throws RuntimeException ошибка если имя неправильное
     */
    public String setName(String name) throws RuntimeException
    {
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я\\s]+"); // паттерн для имени
        Matcher mather = pattern.matcher(name);
        if (mather.matches()) // проверка введенного имени
        {
            return name; // возвращаем имя
        }
        else
        {
            name = "Name Surname";
            throw new RuntimeException("Ошибка: неверно введено ФИО пользователя");
        }
    }

    /**
     * Нажатие кнопки войти
     * @param actionEvent событие
     * @throws IOException ошибка
     */
    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) throws IOException
    {
        try
        {
            String inputname = setName(LoginText.getText()); //проверка введенного имени
            ChatController chatController = new ChatController(); // выделяем память под контроллер чата
            chatController.name = inputname; // передаем в контроллер имя пользователя
            FXMLLoader fxmlLoader = new FXMLLoader(ChatBotApplication.class.getResource("chat-view.fxml")); // создаем загрузчик для окна чата
            fxmlLoader.setController(chatController); // указываем контроллер чата для загрузчика чата
            Scene scene = new Scene(fxmlLoader.load(), 518.0, 365.0); // указываем размеры окна чата
            Stage stage = new Stage(); // выделяем память под окно чата
            stage.setTitle("Chat"); // заголовок окна чата
            stage.getIcons().add(new Image("file:src/main/resources/com/classig/chatbot/iconbot.png")); // добавляем иконку
            stage.setScene(scene);
            // создаем событие при закрытии окна чата
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    chatController.onSave(); // Сохраняем диалог с ботом
                    Platform.exit(); // закрываем приложение
                }
            });
            stage.show(); // показываем окно чата
            LoginText.getScene().getWindow().hide(); // скрываем окно входа
        }
        catch (RuntimeException e) // если ошибка
        {
            System.out.println(e.getMessage());
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText(e.getMessage());
            error.setContentText("Имя должно состоять только из букв и пробела");
            error.showAndWait();
        }
    }

}