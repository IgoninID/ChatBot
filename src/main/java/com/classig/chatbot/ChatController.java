package com.classig.chatbot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Контроллер чата
 */
public class ChatController
{
    /**
     * Поле имя
     */
    public String name;

    /**
     * Поле диалога
     */
    @FXML
    public TextArea ChatArea;

    /**
     * Поле ввода сообщения
     */
    @FXML
    public TextField MessageText;

    /**
     * Чат-бот
     */
    IBot bot = new Bot();

    /**
     * Нажатие на кнопку отправить
     * @param event - событие
     */
    @FXML
    void onSendClick(ActionEvent event)
    {
        String text = MessageText.getText(); // получаем сообщение пользвателя
        if (!text.isEmpty()) // строка не пустая
        {
            LocalDateTime now = LocalDateTime.now(); // текущее дата время

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //формат даты времени

            String formatnow = now.format(formatter); // форматируем дату время

            ChatArea.appendText(formatnow+". "+name+": "+text+"\n"); // выводим в окно диалога сообщение пользователя с его именем и датой время

            formatnow = now.format(formatter); // текущее дата время

            ChatArea.appendText(formatnow+". "+"Bot: "+bot.answer(text)+"\n"); // выводим ответ бота в окно диалога
        }
        MessageText.clear(); // очищаем поле ввода сообщения
    }

    /**
     * Сохранение диалога в файл
     */
    @FXML
    void onSaveClick()
    {
        final String H_FILE = name+".txt"; // название файла
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(H_FILE))) // инициализация записи в файл
        {
            writer.write(ChatArea.getText()); // записываем в файл текст из поля диалога
        }
        catch (IOException e) // если не сработал filewriter
        {
            e.printStackTrace(); // выводим где ошибка
        }
    }

    /**
     * Загрузка диалога из файла
     */
    @FXML
    void onLoadClick()
    {
        final String H_FILE = name+".txt"; // название файла
        if (Files.exists(Paths.get(H_FILE))) // если файл существует
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(H_FILE))) // инициализация чтения файла
            {
                String line; // строка текста в файле
                while ((line = reader.readLine()) != null) // до конца файла
                {
                    ChatArea.appendText(line+"\n"); // записываем строки в поле диалога
                }
            }
            catch (IOException e) // если не сработал readline
            {
                e.printStackTrace(); // выводим где ошибка
            }
        }
    }

    /**
     * Инициализация окна (то что произойдет при открытии окна чата)
     */
    @FXML
    void initialize()
    {
        onLoadClick(); // загрузка диалога из файла
    }

}