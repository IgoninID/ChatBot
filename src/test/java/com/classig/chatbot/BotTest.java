package com.classig.chatbot;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {

    /**
     * Проверка сложения двух чисел
     */
    @Test
    void summ() {
        assertEquals("8.0", Bot.summ("4+4"));
        assertEquals("-8.0", Bot.summ("-4+-4"));
        assertEquals("0.0", Bot.summ("-4+4"));
        assertEquals("0.0", Bot.summ("4+-4"));
        assertEquals("Пустая строка", Bot.summ(""));
    }

    /**
     * Проверка умножения двух чисел
     */
    @Test
    void mult() {
        assertEquals("16.0", Bot.mult("4*4"));
        assertEquals("16.0", Bot.mult("-4*-4"));
        assertEquals("-16.0", Bot.mult("-4*4"));
        assertEquals("-16.0", Bot.mult("4*-4"));
        assertEquals("0.0", Bot.mult("4*0"));
        assertEquals("Пустая строка", Bot.mult(""));
    }

    /**
     * Проверка деления двух чисел
     */
    @Test
    void div() {
        assertEquals("1.0", Bot.div("4:4"));
        assertEquals("1.0", Bot.div("-4:-4"));
        assertEquals("-1.0", Bot.div("-4:4"));
        assertEquals("-1.0", Bot.div("4:-4"));
        assertEquals("Делить на 0 нельзя", Bot.div("4:0"));
        assertEquals("0.0", Bot.div("0:4"));
        assertEquals("Пустая строка", Bot.div(""));
    }

    /**
     * Проверка вычитания двух чисел
     */
    @Test
    void minus() {
        assertEquals("0.0", Bot.minus("4 - 4"));
        assertEquals("0.0", Bot.minus("-4 - -4"));
        assertEquals("-8.0", Bot.minus("-4 - 4"));
        assertEquals("8.0", Bot.minus("4 - -4"));
        assertEquals("Пустая строка", Bot.minus(""));
    }

    /**
     * Проверка ответа на сообщение пользователя
     */
    @Test
    void answer() {
        IBot bot = new Bot();
        LocalDateTime now = LocalDateTime.now(); // текущее дата время

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //формат даты времени

        String formatnow = now.format(formatter); // форматируем дату время

        assertEquals(formatnow+". Bot: Привет! Я чат бот, чем могу помочь? (Напишите что ты можешь? для показа возможностей)", bot.answer("Привет", "a", formatnow));
        assertEquals(formatnow+". Bot: Привет! Я чат бот, чем могу помочь? (Напишите что ты можешь? для показа возможностей)", bot.answer("привет", "a", formatnow));
        assertEquals(formatnow+". Bot: Сложить/вычесть/умножить/поделить два числа, узнать погоду в городе(погода в город(именительный падеж))", bot.answer("Что ты можешь?", "a", formatnow));
        assertEquals(formatnow+". Bot: Сложить/вычесть/умножить/поделить два числа, узнать погоду в городе(погода в город(именительный падеж))", bot.answer("что ты можешь?", "a", formatnow));
        assertEquals(formatnow+". Bot: Результат суммы = 4.0", bot.answer("2+2", "a", formatnow));
        assertEquals(formatnow+". Bot: ??", bot.answer("", "a", formatnow));
        assertEquals(formatnow+". Bot: Результат вычитания = 0.0", bot.answer("2 - 2", "a", formatnow));
        assertEquals(formatnow+". Bot: Результат умножения = 6.0", bot.answer("2*3", "a", formatnow));
        assertEquals(formatnow+". Bot: Результат деления = 2.0", bot.answer("4:2", "a", formatnow));
        assertEquals(formatnow+". Bot: Результат деления = Делить на 0 нельзя", bot.answer("4:0", "a", formatnow));
        assertEquals(formatnow+". Bot: ??", bot.answer("что?", "a", formatnow));

    }
}