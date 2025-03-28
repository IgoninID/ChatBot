package com.classig.chatbot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {

    @Test
    void summ() {
        assertEquals("8.0", Bot.summ("4+4"));
        assertEquals("-8.0", Bot.summ("-4+-4"));
        assertEquals("0.0", Bot.summ("-4+4"));
        assertEquals("0.0", Bot.summ("4+-4"));
        assertEquals("Пустая строка", Bot.summ(""));
    }

    @Test
    void mult() {
        assertEquals("16.0", Bot.mult("4*4"));
        assertEquals("16.0", Bot.mult("-4*-4"));
        assertEquals("-16.0", Bot.mult("-4*4"));
        assertEquals("-16.0", Bot.mult("4*-4"));
        assertEquals("0.0", Bot.mult("4*0"));
        assertEquals("Пустая строка", Bot.mult(""));
    }

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

    @Test
    void minus() {
        assertEquals("0.0", Bot.minus("4-4"));
        //assertEquals("0.0", Bot.minus("-4--4"));
        //assertEquals("-8.0", Bot.minus("-4-4"));
        //assertEquals("8.0", Bot.minus("4--4"));
        assertEquals("Пустая строка", Bot.minus(""));
    }

    @Test
    void answer() {
        IBot bot = new Bot();
        assertEquals("Привет! Я чат бот, чем могу помочь? (Напишите что ты можешь? для показа возможностей)", bot.answer("Привет"));
        assertEquals("Привет! Я чат бот, чем могу помочь? (Напишите что ты можешь? для показа возможностей)", bot.answer("привет"));
        assertEquals("Сложить/вычесть/умножить/поделить два числа", bot.answer("Что ты можешь?"));
        assertEquals("Сложить/вычесть/умножить/поделить два числа", bot.answer("что ты можешь?"));
        assertEquals("Результат суммы = 4.0", bot.answer("2+2"));
        assertEquals("??", bot.answer(""));
        assertEquals("Результат вычитания = 0.0", bot.answer("2-2"));
        assertEquals("Результат умножения = 6.0", bot.answer("2*3"));
        assertEquals("Результат деления = 2.0", bot.answer("4:2"));
        assertEquals("Результат деления = Делить на 0 нельзя", bot.answer("4:0"));
        assertEquals("??", bot.answer("что?"));

    }
}