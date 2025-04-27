package com.classig.chatbot;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс бота
 */
public interface IBot {
    /**
     * Ответ бота на сообщение
     * @param message сообщение пользователя
     * @param name имя пользователя
     * @param formatnow текущее время в формате yyyy-MM-dd HH:mm:ss
     * @return ответ бота
     */
    String answer(String message, String name, String formatnow);

    int getMessagesSize();

    List<String> getMessages();

    String getMessage(int i);

    void Save(String name);

    void Load(String name);
}
