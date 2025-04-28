package com.classig.chatbot;

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

    /**
     * Получение размера истории сообщений
     * @return Количество сообщений в истории сообщений
     */
    int getMessagesSize();

    /**
     * Получение истории сообщений
     * @return Список с историей сообщений
     */
    List<String> getMessages();

    /**
     * Получение сообщения из истории сообщений
     * @param i индекс сообщения в списке
     * @return Сообщение из истории сообщений
     */
    String getMessage(int i);

    /**
     * Сохранение в файл истории сообщений
     * @param name имя файла без формата (имя пользователя)
     */
    void Save(String name);

    /**
     * Загрузка из файла истории сообщений
     * @param name имя файла без формата (имя пользователя)
     */
    void Load(String name);
}
