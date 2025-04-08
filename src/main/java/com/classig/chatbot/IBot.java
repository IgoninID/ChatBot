package com.classig.chatbot;

/**
 * Абстрактный класс бота
 */
public interface IBot {
    /**
     * Ответ бота на сообщение
     * @param message сообщение пользователя
     * @return ответ бота
     */
    String answer(String message);

    void setMessages(String s);

    String getMessages(int i);
}
