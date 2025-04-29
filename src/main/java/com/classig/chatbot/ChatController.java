package com.classig.chatbot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
     */
    @FXML
    void onSendClick()
    {
        String text = MessageText.getText(); // получаем сообщение пользвателя
        if (!text.isEmpty()) // строка не пустая
        {
            LocalDateTime now = LocalDateTime.now(); // текущее дата время

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //формат даты времени

            String formatnow = now.format(formatter); // форматируем дату время

            ChatArea.appendText(formatnow+". "+name+": "+text+"\n"); // выводим в окно диалога сообщение пользователя с его именем и датой время

            formatnow = now.format(formatter); // форматируем дату время

            ChatArea.appendText(bot.answer(text, name, formatnow)+"\n"); // выводим ответ бота в окно диалога
        }
        MessageText.clear(); // очищаем поле ввода сообщения
    }

    /**
     * Событие при нажатии на клавишу
     * @param key - клавиша
     */
    @FXML
    public void handle(KeyEvent key)
    {
        if (key.getCode() == KeyCode.ENTER)
        {
            onSendClick();
        }
    }

    /**
     * Сохранение диалога в файл
     */
    @FXML
    void onSave()
    {
        bot.Save(name);
    }

    /**
     * Загрузка диалога из файла
     */
    @FXML
    void onLoad()
    {
        bot.Load(name);
        for (int i = 0; i < bot.getMessagesSize(); i++)
        {
            ChatArea.appendText(bot.getMessage(i));
        }
        bot.getMessages().clear();
    }

    /**
     * Инициализация окна (то что произойдет при открытии окна чата)
     */
    @FXML
    void initialize()
    {
        onLoad(); // загрузка диалога из файла
    }

}